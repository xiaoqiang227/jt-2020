package com.jt;

import org.junit.Test;

public class test {
    @Test
    public static void main(String[] args) {
        String aa = "123123456789";
        String[] split = aa.split(",");
        for (String s : split) {
            System.out.println("s = " + s);
        }
    }
}
