package com.zzdz.service;

import com.zzdz.entity.Employee;

import java.util.List;

/**
 * @Classname EmployeeService
 * @Description TODO
 * @Date 2019/7/4 17:22
 * @Created by joe
 */
public interface EmployeeService {

    Employee get(Long id);

    List<Employee> getAll();
}
