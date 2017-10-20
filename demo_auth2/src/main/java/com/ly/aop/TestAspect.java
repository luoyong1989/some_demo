package com.ly.aop;

import com.ly.pojo.ValueRoute;
import com.ly.service.MonitorConfigManager;
import com.ly.utils.ControllerAnnotationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拦截器
 *
 * @author ly
 * @create 2017-10-18
 **/
@Aspect
@Component
public class TestAspect {

    @Autowired
    MonitorConfigManager configManager;

    @Pointcut("execution(* com.ly.controller.*.*(..))")
    private void pointCut(){

    }

    //声明前置通知
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("前置通知");
        List<String> requestUriList = getRequestUri(joinPoint);
        String[] path = null;
        String appIdPath;
        for (String uri : requestUriList){
            ValueRoute route = configManager.macthPath(uri);
            if (null != route){
                Object[] args = joinPoint.getArgs();
                appIdPath = route.getAppIdPath();
                if (null != appIdPath && !"".equals(appIdPath)){
                    path = appIdPath.split("[.]");
                }
                String value = "";
                try {
                    value = String.valueOf(getValueByValuePath(args[route.getArgsIndex()],path));
                } catch (Exception e) {
                }
                System.out.println("value = " + value + "----uri = "+uri);
                break;
            }
        }
    }

    /**
     * 获取请求路径
     * @param joinPoint
     * @return
     */
    private List<String> getRequestUri(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        RequestMapping annotation = declaringClass.getAnnotation(RequestMapping.class);
        String controllerUri = ControllerAnnotationUtils.formatPath(ControllerAnnotationUtils.getRequestUri(annotation));

        Annotation[] annotations = method.getAnnotations();
        List<String> methodUris = ControllerAnnotationUtils.getRequestUris(annotations);
        return methodUris.stream().map(uri -> {
            uri = ControllerAnnotationUtils.formatPath(uri);
            uri = controllerUri + uri;
            return uri;
        }).collect(Collectors.toList());

    }

    /**
     *
     * @param object
     * @param path
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object getValueByValuePath(Object object, String[] path) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (null == path){
            return String.valueOf(object);
        }
        String methodName = CreatGetMethodName(path[0]);
        Method m = object.getClass().getMethod(methodName);
        Object o = m.invoke(object);
        if (path.length > 1){
            String[] array = delArrayFirstElement(path);
            o = getValueByValuePath(o,array);
        }
        return o;
    }


    private String[] delArrayFirstElement(String[] path){
        String[] array = new String[path.length-1];
        for (int i=1;i<path.length;i++){
            array[i-1] = path[i];
        }
        return array;
    }

    /**
     * 创建get方法
     * @param fildeName
     * @return
     */
    private static String CreatGetMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        String name = new String(items);
        return "get"+name;
    }

}
