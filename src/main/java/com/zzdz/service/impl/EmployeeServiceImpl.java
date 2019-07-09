package com.zzdz.service.impl;

import com.zzdz.Dao.EmployeeDao;
import com.zzdz.entity.Employee;
import com.zzdz.service.EmployeeService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname EmployeeServiceImpl
 * @Description TODO
 * @Date 2019/7/4 17:23
 * @Created by joe
 */
@CacheConfig(cacheNames = "employee")
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    @CachePut(key = "#employee.id")
    @Override
    public Integer add(Employee employee) {
        return employeeDao.insert(employee);
    }

    @CacheEvict(key = "#employee.id")
    @Override
    public Integer delete(Employee employee) {
        return employeeDao.delete(employee);
    }

    @Cacheable(key = "#id")
    @Override
    public Employee get(Long id) {
        return employeeDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }


    @RabbitListener(queues = "test")
    public void receive1(Employee employee) {
        System.out.println(employee);
    }

    @RabbitListener(queues = "test")
    public void receive2(Message message) {
        System.out.println(message.getMessageProperties());
        System.out.println(message.getBody());
    }
}
