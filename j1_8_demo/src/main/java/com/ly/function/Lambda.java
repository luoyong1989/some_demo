//package com.ly.function;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * Created by ly on 2017/8/4.
// */
//public class Lambda {
//    public static void main(String[] args) {
//        tetst3();
//    }
//    public static void test1(){
//        List<Integer> list = Arrays.asList(22,3,5,5,5,1,33,56);
//        list.sort((a,b) -> a<b ?0:-1);
//        list.stream().forEach(Test::add);
//        list.stream().forEach(i -> System.out.println("i = " + i));
//    }
//    public static void tetst2(){
//        Function<Integer,Value> f = Value::new;
//        List<Value> collect = Arrays.asList(1, 2, 34, 55, 66, 46, 32, 75, 33).stream().map(integer -> f.apply(integer)).collect(Collectors.toList());
//        collect.stream().forEach(Test::add);
//        collect.forEach(value -> System.out.println(value.getV()));
//    }
//    public static void tetst3() {
//        Function<Integer, Value> f = Value::new;
//        Arrays.asList(1, 2, 34, 55, 66, 46, 32, 75, 33).stream().filter(i -> i<10).forEach(System.out::println);
//    }
//
//}
//class Test{
//    public static int add(int a){
//        return a*10;
//    }
//    public static void add(Value v){
//       v.setV(v.getV()*10);
//    }
//}
//class Value{
//    public Value(int i){
//        this.v = i;
//    }
//    private int v;
//
//    public int getV() {
//        return v;
//    }
//
//    public void setV(int v) {
//        this.v = v;
//    }
//}