//package com.ly.function;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Supplier;
//
///**
// * Created by ly on 2017/8/4.
// */
//public class FunctionTest {
//    public static void main(String[] args) {
//        Car car = Car.create(Car::new);
//        List<Car> list = Arrays.asList(car);
//        list.stream().forEach((Car c) -> c.repair() );
//        list.stream().forEach(Car::collide);
//    }
//
//
//}
//
//
//class Car {
//    public static Car create( final Supplier< Car > supplier ) {
//        return supplier.get();
//    }
//
//    public static void collide( final Car car ) {
//        System.out.println( "Collided " + car.toString() );
//    }
//
//    public void follow( final Car another ) {
//        System.out.println( "Following the " + another.toString() );
//    }
//
//    public void repair() {
//        System.out.println( "Repaired " + this.toString() );
//    }
//}
