package com.zzdz.service.impl;

import com.zzdz.Dao.EmployeeDao;
import com.zzdz.entity.Employee;
import com.zzdz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname EmployeeServiceImpl
 * @Description TODO
 * @Date 2019/7/4 17:23
 * @Created by joe
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee get(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
