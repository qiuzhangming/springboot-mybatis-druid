package com.zzdz.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * @Classname TimeShardingTableAlgorithm
 * @Description TODO
 * @Date 2019/8/8 13:06
 * @Created by joe
 */
public class TimeShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        Long value = shardingValue.getValue();
        String yearAndMonth = getYearAndMonthAndDay(value);

        StringBuffer tableName = new StringBuffer();
        tableName.append(logicTableName)
                .append("_")
                .append(yearAndMonth);

        return tableName.toString();
    }


    /**
     * 根据时间计算表后缀
     * @param shardingKey
     * @return
     */
    public static String getYearAndMonthAndDay(long shardingKey){
        DateTimeFormatter yearAndMonthAndDay =  DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        Instant instant =  Instant.ofEpochSecond(shardingKey);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return  yearAndMonthAndDay.format(localDateTime);
    }

}
