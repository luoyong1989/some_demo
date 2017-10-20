package com.ly.utils;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Controller 拦截器工具
 *
 * @author ly
 * @create 2017-10-18
 **/
public class ControllerAnnotationUtils {
    private static final String DELIMITER = "/";


    public static List<String> getRequestUris(Annotation[] annotations){
        for (Annotation annotation : annotations){
            List<String> list = getRequestUris(annotation);
            if (list.size() > 0){
                return list;
            }
        }
        return Collections.emptyList();
    }

    public static List<String> getRequestUris(Annotation annotation) {
        if (null == annotation) {
            return Collections.emptyList();
        }
        try {
            if (isRest(annotation)){
                Method method = annotation.getClass().getMethod("value");
                Object obj = method.invoke(annotation);
                String[] values = (String[]) obj;
                return Arrays.asList(values);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static boolean isRest(Annotation annotation){
        if (annotation instanceof RequestMapping ||
                annotation instanceof PostMapping ||
                annotation instanceof PutMapping ||
                annotation instanceof DeleteMapping ||
                annotation instanceof GetMapping){
            return true;
        }
        return false;
    }

    public static String getRequestUri(Annotation annotation) {
        String result = "";
        if (null == annotation){
            return result;
        }
        if (isRest(annotation)){
            try {
                Method method = annotation.getClass().getMethod("value");
                Object obj = method.invoke(annotation);
                String[] values = (String[]) obj;
                result = values[0];
            }catch (Exception e){

            }
        }
        return result;
    }

    public static String formatPath(String str) {
        if (null == str || "".equals(str)){
            return "";
        }
        if (DELIMITER.equals(str)){
            return str;
        }
        if (!str.startsWith(DELIMITER)) {
            str = DELIMITER + str;
        }
        if (str.endsWith(DELIMITER)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
