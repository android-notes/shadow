package com.wanjian.shadowlib;

/**
 * Created by wanjian on 2017/9/14.
 */

class Check {
    static void ifNull(Object o) {
        if (o == null) {
            throw new RuntimeException("can not be null !");
        }
    }
}
