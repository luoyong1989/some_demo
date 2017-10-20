package com.ly;

import java.io.File;

/**
 * Created by ly on 2017/8/4.
 */
public class Nothing {

    public static void main(String[] args) {
        File file = new File("/Users/ly/logs/aa");
        if (!file.exists() || !file.isDirectory()){
            file.mkdir();
        }
    }
}
