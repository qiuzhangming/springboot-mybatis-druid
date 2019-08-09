package com.zzdz.controllers;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzdz.entity.Employee;
import com.zzdz.service.EmployeeService;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname EmployeeController
 * @Description TODO
 * @Date 2019/7/4 17:20
 * @Created by joe
 */

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/add")
    public Integer add(Employee employee) {
        SnowflakeShardingKeyGenerator defaultKeyGenerator = new SnowflakeShardingKeyGenerator();
        employee.setId((Long) defaultKeyGenerator.generateKey());
        employee.setAddTime(System.currentTimeMillis()/1000);
        System.out.println("=========================="+employee);

        Integer r= employeeService.add(employee);
        return r;
    }

    @GetMapping("/delete")
    public Integer delete(Employee employee) {
        return employeeService.delete(employee);
    }

    @GetMapping("/get/{id}")
    public Employee get(@PathVariable("id") Long id) {
        return employeeService.get(id);
    }

    @GetMapping("/find1")
    public List<Employee> findByAge(Long addtime) {
        return employeeService.findByAddTime(addtime);
    }

    @GetMapping("/find2")
    public List<Employee> find2(Long start, Long end) {
        return employeeService.findByAddTimeRange(start,end);
    }

    @GetMapping("/find3")
    public List<Employee> find3(Long addtime, String name) {
        return employeeService.findByAddTimeAndName(addtime, name);
    }

    @GetMapping("/find4")
    public List<Employee> find4(Long addtime, String name, Integer age) {
        return employeeService.findByAddTimeAndNameAndAge(addtime, name, age);
    }

    //http://localhost:8080/getAll?pageNum=1&pageSize=7
    @GetMapping("/getAll")
    public PageInfo<Employee> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //PageHelper.startPage(pageNum,pageSize).setOrderBy("age desc");
        List<Employee> employees = employeeService.getAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        return pageInfo;
    }
}
