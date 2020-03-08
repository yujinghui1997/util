package com.yjh.util;

public enum MyLdtUnit {

    SECOND(0),
    MINUTE(1),
    HOUR(2),
    DAY(3),
    WEEK(4),
    MONTH(5),
    YEAR(6);

    private Integer code;
    MyLdtUnit(Integer code){
        this.code = code;
    }
}
