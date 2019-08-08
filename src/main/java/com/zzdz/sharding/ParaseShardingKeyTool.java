package com.zzdz.sharding;


import io.shardingsphere.core.keygen.DefaultKeyGenerator;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Classname ParaseShardingKeyTool
 * @Description TODO
 * @Date 2019/8/8 13:00
 * @Created by joe
 */
public class ParaseShardingKeyTool {
    private static DateTimeFormatter yearAndMonth =  DateTimeFormatter.ofPattern("yyyyMM");
    private static  DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");

    public static String getYearAndMonth(long shardingKey){
        Instant instant =  Instant.ofEpochMilli(DefaultKeyGenerator.EPOCH+(Long.valueOf(shardingKey+"")>>22));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return  yearAndMonth.format(localDateTime);
    }

    public static String getYear(long shardingKey){
        Instant instant =  Instant.ofEpochMilli(DefaultKeyGenerator.EPOCH+(Long.valueOf(shardingKey+"")>>22));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return  year.format(localDateTime);
    }

    public static void main(String[] args) {
        DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()));
        System.out.println(ParaseShardingKeyTool.getYearAndMonth(Long.valueOf(defaultKeyGenerator.generateKey()+"")));
        System.out.println(ParaseShardingKeyTool.getYearAndMonth(Long.valueOf(defaultKeyGenerator.generateKey()+"")));
    }
}
