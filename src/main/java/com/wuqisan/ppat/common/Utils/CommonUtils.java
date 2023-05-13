package com.wuqisan.ppat.common.Utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * @author plusthree
 */
public class CommonUtils {

    public static long generateKey11(){
        return Long.parseLong(RandomUtil.randomNumbers(11));

    }
    public static long generateKey15(){
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        String timestampStr=   DateUtil.format(new Date(timestamp),"yyyyMMddHHmmss");
        // 生成随机数
        SecureRandom random = new SecureRandom();
        int randomNum = random.nextInt(10) ; // 生成6位随机数
        // 组合成15位随机数
        String randomNumber = timestampStr + randomNum;
        return Long.parseLong(randomNumber);
    }

    public static String generate15String(){

     return    RandomUtil.randomNumbers(15);
    }

    public static String generateRanDomColor(){
        String[] colors = {"red", "orange", "yellow", "green", "cyan", "blue", "purple"};
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return  colors[index];
    }
}
