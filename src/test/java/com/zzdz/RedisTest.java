package com.zzdz;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceC3P0Adapter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zaxxer.hikari.HikariDataSource;
import com.zzdz.entity.Employee;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Classname RedisTest
 * @Description TODO
 * @Date 2019/7/9 10:03
 * @Created by joe
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Qualifier(value = "empRedisTemplate")
    @Autowired
    private RedisTemplate<Object, Employee> empRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void test() {
        System.out.println(System.getProperties());
        System.out.println("");
    }

    @Test
    public void contextLoads() {

        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee("zs" + i, 11 + i);
            list.add(employee);
        }

        redisTemplate.opsForList().leftPushAll("list:test", list);

        DruidDataSource dataSource = new DruidDataSource();
        //dataSource.configFromPropety();

    }

    @Test
    public void testHashWrite() {
//        Map<String, String> map = new HashMap<>();
//        for (int i = 0; i < 1000; i++) {
//            map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
//        }

        Instant sTime = Instant.now();

        List<Object> objects = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                String key = "user:";
                for (int i = 0; i < 10000; i++) {
                    Employee employee = new Employee("zs" + i, 11 + i);
                    redisTemplate.opsForHash().put(key + i, "name", "joe" + i);
                    redisTemplate.opsForHash().put(key + i, "age", "32");
                    redisTemplate.opsForHash().put(key + i, "emp", JSON.toJSONString(employee));
//                    redisTemplate.opsForHash().put(key + i, "map", JSON.toJSONString(map));
//                    redisTemplate.opsForHash().putAll(key + i, map);
                    redisTemplate.expire(key + i, 5, TimeUnit.MINUTES);
                }
                return null;
            }
        });

//        String key = "user:";
//        for (int i = 0; i < 10000; i++) {
//            Employee employee = new Employee("zs"+i, 11+i);
//            redisTemplate.opsForHash().put(key+i, "name", "joe"+i);
//            redisTemplate.opsForHash().put(key+i, "age", "32");
//            redisTemplate.opsForHash().put(key+i, "emp", JSON.toJSONString(employee));
//            redisTemplate.opsForHash().put(key+i, "map", JSON.toJSONString(map));
//            redisTemplate.opsForHash().putAll(key+i, map);
//            redisTemplate.expire(key+i,10, TimeUnit.MINUTES);
//        }

        System.out.println("耗时：" +ChronoUnit.MILLIS.between(sTime,Instant.now()) );
        System.out.println(objects);
    }

    @Test
    public void testhashRead() {
        List<String> keyList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            String key = "user:" + i;
            keyList.add(key);
        }

        Instant sTime = Instant.now();
        List<Object> objectList = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (String key : keyList) {
//                    stringRedisTemplate.opsForValue().get(key);
//                    redisTemplate.opsForValue().get(key);

//                    redisTemplate.opsForHash().get(key,"emp");

//                    redisTemplate.opsForHash().keys(key);

//                    redisTemplate.opsForHash().values(key);

                    redisTemplate.opsForHash().entries(key);
                }
                return null;
            }
        });
        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
        System.out.println(objectList);
        System.out.println(objectList.get(0));
        System.out.println(objectList.get(0).getClass());
    }

    @Test
    public void test01() {
        Employee employee = new Employee("aa", 11);

        empRedisTemplate.opsForValue().set("11",employee);

        Employee employee1 = empRedisTemplate.opsForValue().get("11");
        System.out.println(employee1);
    }

    @Test
    public void testPipelined() {

        Instant sTime = Instant.now();

        for (int i = 0; i < 10000; i++) {
            stringRedisTemplate.opsForValue().increment("pipline", 1);
        }

        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
    }

    @Test
    public void testPipelined2() {
        Instant sTime = Instant.now();

        List<Object> objectList = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    stringRedisTemplate.opsForValue().increment("pipline2", 1);
                }
                return null;
            }
        });
        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
        //System.out.println(objectList);
    }

    /**
     * 管道批量写入
     */
    @Test
    public void testPipelined3() {
        List<Employee> employeeList = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            Employee employee = new Employee(UUID.randomUUID().toString().substring(0,6));
            employee.setId((long) i);
            employeeList.add(employee);
        }

        Instant sTime = Instant.now();
        List<Object> objectList = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (Employee e: employeeList) {
                    String key = "employees:" + e.getId();
//                    stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(e));
                    redisTemplate.opsForValue().set(key,e,3, TimeUnit.MINUTES);
                }
                return null;
            }
        });
        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
        System.out.println(objectList);
    }


    /**
     * 管道读取
     */
    @Test
    public void testPipelined4() {
        List<String> keyList = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            String key = "employees:" + i;
            keyList.add(key);
        }

        Instant sTime = Instant.now();
        List<Object> objectList = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (String key : keyList) {
//                    stringRedisTemplate.opsForValue().get(key);
                    redisTemplate.opsForValue().get(key);
                }
                return null;
            }
        });
        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
        System.out.println(objectList);
        System.out.println(objectList.get(0));
        System.out.println(objectList.get(0).getClass());

        Employee employee = JSONObject.parseObject(objectList.get(0).toString(), Employee.class);
        System.out.println(employee);
    }

    @Test
    public void testPipelined5() {
        List<String> keyList = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            String key = "employees:" + i;
            keyList.add(key);
        }

        Instant sTime = Instant.now();

        //List<String> list = stringRedisTemplate.opsForValue().multiGet(keyList);

        List<Object> list = redisTemplate.opsForValue().multiGet(keyList);

        Instant eTime = Instant.now();
        System.out.println("耗时：" + ChronoUnit.MILLIS.between(sTime,eTime) );
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(0).getClass());
    }

}
