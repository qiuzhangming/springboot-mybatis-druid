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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 搜查多表
 * 范围搜索时（跨表）应传递时间戳并左移22位
 */
public class TimeRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    private DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyyMM");
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();

        result.add("employee0");
//        result.add("employee1");
        return result;


//        Range<Long> shardingKey = shardingValue.getValueRange();
//        long startShardingKey = shardingKey.lowerEndpoint();
//        long endShardingKey = shardingKey.upperEndpoint();
//        //获取到开始时间戳
//        String startTimeString = ParaseShardingKeyTool.getYearAndMonth(startShardingKey);
//        //获取结束时间戳
//        String endTimeString = ParaseShardingKeyTool.getYearAndMonth(endShardingKey);
//        Calendar cal = Calendar.getInstance();
//        //获取开始的年月
//        //时间戳
//        LocalDateTime startLocalDate = GenericTool.getLocalDate(startTimeString);
//        //获取结束的年月
//        LocalDateTime endLocalDate = GenericTool.getLocalDate(endTimeString);
//        //进行判断 获取跨月份的表 如201901,201902,201903 三个月的表
//        int end = Integer.valueOf(dateformat.format(endLocalDate));
//        int start = Integer.valueOf(dateformat.format(startLocalDate));
//        while(start < end){
//            StringBuffer tableName = new StringBuffer();
//            tableName.append(shardingValue.getLogicTableName())
//                    .append("_").append(start);
//            result.add(tableName.toString());
//        }
//        return result;
    }
}