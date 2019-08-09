package com.zzdz.sharding;

/**
 * @Classname TimeRangeShardingAlgorithm
 * @Description TODO
 * @Date 2019/8/8 12:59
 * @Created by joe
 */

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 搜查多表
 * 范围搜索时（跨表）应传递时间戳并左移22位
 */
public class TimeRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();

        String logicTableName = shardingValue.getLogicTableName();
        Range<Long> valueRange = shardingValue.getValueRange();
        Long start = valueRange.lowerEndpoint();
        Long stop = valueRange.upperEndpoint();

        Collection<String> suffixs = tableSuffix(start, stop);
        for (String suffix : suffixs) {
            result.add(logicTableName + "_" + suffix);
        }
        return result;
    }

    /**
     * 计算时间范围的表的后缀
     * @param startTime
     * @param stopTime
     * @return
     */
    public static Collection<String> tableSuffix(Long startTime, Long stopTime) {

        DateTimeFormatter yearAndMonthAndDay =  DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        Collection<String> result = new LinkedHashSet<>();

        // 如果结束时间小于开始时间
        if (stopTime < startTime) {
            return result;
        }

        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochSecond(startTime), ZoneId.systemDefault());
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(Instant.ofEpochSecond(stopTime), ZoneId.systemDefault());

        //先加入开始时间和结束时间对应的表后缀
        result.add(yearAndMonthAndDay.format(localDateTime1));
        result.add(yearAndMonthAndDay.format(localDateTime2));

        //两个时间的时间差
        long tempSeconds = Duration.between(localDateTime1, localDateTime2).getSeconds();
        int timeDiff = (int) tempSeconds / 60;
        //不能整除就加1
        if (tempSeconds % 60 != 0) {
            timeDiff++;
        }
        if (timeDiff > 1 ) {
            for (int i = 1; i < timeDiff; i++) {
                result.add(localDateTime1.plusMinutes(i).format(yearAndMonthAndDay));
            }
        }
        return result;
    }
}