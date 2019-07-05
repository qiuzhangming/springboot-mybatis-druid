package com.zzdz;

import com.zzdz.Dao.EmployeeDao;
import com.zzdz.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisDruidApplicationTests {

    @Resource
    private EmployeeDao employeeDao;

    @Test
    public void contextLoads() {
    }

    /**
     * 查询
     * SELECT id,nick_name,age FROM employee WHERE ( ( age > ? and age < ? ) or ( age < ? ) ) FOR UPDATE
     *
     */
    @Test
    public void exampleTest1() {
        Example example = new Example(Employee.class);
        example.setForUpdate(true);
        example.createCriteria().andGreaterThan("age",30).andLessThan("age",100);
        example.or().andLessThan("age",10);

        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }


    /**
     * 动态 SQL
     * SELECT id,nick_name,age FROM employee WHERE ( ( id = ? and nick_name like ? and age > ? ) )
     *
     */
    @Test
    public void exampleTest2() {

        Employee employee = new Employee();
        employee.setId(null);
        employee.setNickName("joe");
        employee.setAge(20);

        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();

        if (employee.getId() != null) {
            criteria.andEqualTo("id", employee.getId());
        }
        if (employee.getNickName() != null) {
            criteria.andLike("nickName", employee.getNickName() + "%");
        }
        if (employee.getAge() != null) {
            criteria.andGreaterThan("age", employee.getAge());
        }

        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }

    /**
     * 排序
     * Preparing: SELECT id,nick_name,age FROM employee order by id DESC,nick_name,age ASC
     */
    @Test
    public void exampleTest3() {
        Example example = new Example(Employee.class);
        example.orderBy("id").desc().orderBy("nickName").orderBy("age").asc();

        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }

    /**
     * 去重
     * SELECT distinct id,nick_name,age FROM employee WHERE ( ( nick_name like ? ) or ( id > ? ) )
     *
     */
    @Test
    public void exampleTest4() {
        Example example = new Example(Employee.class);
        //设置 distinct
        example.setDistinct(true);

        example.createCriteria().andLike("nickName", "o" + "%");
        example.or().andGreaterThan("id", 2);

        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }


    /**
     * 设置查询列
     * SELECT id , nick_name FROM employee
     */
    @Test
    public void exampleTest5() {
        Example example = new Example(Employee.class);
        example.selectProperties("id", "nickName");

        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }


    /**
     * Example.builder 方式
     *
     * SELECT nick_name FROM employee WHERE ( ( id > ? ) ) order by age Asc FOR UPDATE
     */
    @Test
    public void exampleTest6() {
        Example example = Example.builder(Employee.class)
                .select("nickName")
                .where(Sqls.custom().andGreaterThan("id", 1))
                .orderByAsc("age")
                .forUpdate()
                .build();
        List<Employee> employees = employeeDao.selectByExample(example);
        log.info(employees.toString());
    }


    /**
     * Weekend
     * SELECT id,nick_name,age FROM employee WHERE ( ( nick_name like ? and age > ? ) )
     */
    @Test
    public void exampleTest7() {
        List<Employee> employees = employeeDao.selectByExample(new Example.Builder(Employee.class)
                .where(WeekendSqls.<Employee>custom().andLike(Employee::getNickName, "%o%")
                        .andGreaterThan(Employee::getAge, 30))
                .build());

        log.info(employees.toString());
    }

}
