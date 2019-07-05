package com.zzdz.controllers;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzdz.entity.Employee;
import com.zzdz.service.EmployeeService;
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
        Integer r= employeeService.add(employee);
        System.out.println(employee);
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

    @GetMapping("/getAll")
    public PageInfo<Employee> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //PageHelper.startPage(pageNum,pageSize).setOrderBy("age desc");
        List<Employee> employees = employeeService.getAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        return pageInfo;
    }
}
