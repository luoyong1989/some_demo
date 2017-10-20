package com.ly;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * test
 *
 * @author ly
 * @create 2017-10-18
 **/
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("33333");
        list.add("33331");
        list.add("333233");
        list.add("3335333");
        list.add("33323333");
        String s = Arrays.toString(list.toArray());
        String join = "".join(",", list);
        System.out.println("join = " + join);
        System.out.println("s = " + s);
    }
}
