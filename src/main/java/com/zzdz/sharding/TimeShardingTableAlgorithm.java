package com.zzdz.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * @Classname TimeShardingTableAlgorithm
 * @Description TODO
 * @Date 2019/8/8 13:06
 * @Created by joe
 */
public class TimeShardingTableAlgorithm implements PreciseShardingAlgorithm<Integer> {
    private DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        Integer value = shardingValue.getValue();
        String logicTableName = shardingValue.getLogicTableName();
//        String yearAndMonth = ParaseShardingKeyTool.getYearAndMonth(value);
        String yearAndMonth = (value.hashCode() & Integer.MAX_VALUE % 2)+"";

        StringBuffer tableName = new StringBuffer();
        tableName.append(logicTableName)
                //.append("_")
                .append(yearAndMonth);

        System.out.println("---------------------------------- "+ tableName);
        return tableName.toString();
    }
}
