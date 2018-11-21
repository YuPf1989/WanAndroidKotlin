package com.rain.wanandroidkotlin.test;

import android.util.Log;

/**
 * Author:rain
 * Date:2018/11/21 11:26
 * Description:
 */
public class Son implements Father {

    @Override
    public void doSomething() {
        System.out.println("son:doSomething");
    }
}
