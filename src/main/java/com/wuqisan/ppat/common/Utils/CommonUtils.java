package com.wuqisan.ppat.common.Utils;

import cn.hutool.core.util.RandomUtil;

import java.util.Random;

/**
 * @author plusthree
 */
public class CommonUtils {

    public static long generateKey11(){
        return Long.parseLong(RandomUtil.randomNumbers(11));

    }
    public static long generateKey15(){
        long timestamp = System.currentTimeMillis();
        Random random = new Random(timestamp);
        String randomStr = String.format("%d%05d", timestamp, random.nextInt(100000));
        return Long.parseLong(randomStr);

    }

    public static String generate10String(){

     return    RandomUtil.randomNumbers(15);
    }

    public static String generateRanDomColor(){
        String[] colors = {"red", "orange", "yellow", "green", "cyan", "blue", "purple"};
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return  colors[index];
    }
}
