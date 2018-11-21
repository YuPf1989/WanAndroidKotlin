package com.rain.wanandroidkotlin;

import com.rain.wanandroidkotlin.test.Son;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:rain
 * Date:2018/11/19 16:40
 * Description:
 */
public class TestJava {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(0, new Son());
        Son son = ((Son) map.get(0));
        son.doSomething();
    }
}
