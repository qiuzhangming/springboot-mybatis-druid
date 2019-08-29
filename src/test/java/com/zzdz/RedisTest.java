package com.zzdz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public void contextLoads() {
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
                    redisTemplate.opsForValue().set(key,e);
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
