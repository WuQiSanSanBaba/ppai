package com.wuqisan.ppai.common.Utils;

import java.util.UUID;

public class CommonUtils {
    public static long generateKey(){
        int random = (int) (Math.random()*9+1);
        String valueOf = String.valueOf(random);
        int hashCode = UUID.randomUUID().toString().hashCode();
        if(hashCode<0){
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        return Long.parseLong(value);
    }
}
