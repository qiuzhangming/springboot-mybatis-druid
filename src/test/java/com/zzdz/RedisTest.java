package com.zzdz;

import com.zzdz.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
}
