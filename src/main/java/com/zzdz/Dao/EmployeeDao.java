package com.zzdz.Dao;

import com.zzdz.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname EmployeeDao
 * @Description TODO
 * @Date 2019/7/4 17:15
 * @Created by joe
 */

@Mapper
public interface EmployeeDao extends tk.mybatis.mapper.common.Mapper<Employee> {

    Employee findById(Long id);
}
