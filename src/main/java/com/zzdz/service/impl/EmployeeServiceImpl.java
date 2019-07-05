package com.zzdz.service.impl;

import com.zzdz.Dao.EmployeeDao;
import com.zzdz.entity.Employee;
import com.zzdz.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname EmployeeServiceImpl
 * @Description TODO
 * @Date 2019/7/4 17:23
 * @Created by joe
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    @Override
    public Integer add(Employee employee) {
        return employeeDao.insert(employee);
    }

    @Override
    public Integer delete(Employee employee) {
        return employeeDao.delete(employee);
    }

    @Override
    public Employee get(Long id) {
        return employeeDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
