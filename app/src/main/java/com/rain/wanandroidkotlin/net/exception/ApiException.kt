package com.rain.wanandroidkotlin.net.exception


class ApiException(var errorCode: Int, msg: String) : RuntimeException(msg)
