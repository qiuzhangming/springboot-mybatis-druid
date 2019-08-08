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

    Integer add(Employee employee);

    Integer delete(Employee employee);

    Employee get(Long id);

    List<Employee> findByAge(Integer age);

    List<Employee> findByAgeRange(Integer minAge, Integer maxAge);

    List<Employee> findByAgeAndId(Integer age, Long id);

    List<Employee> getAll();
}
