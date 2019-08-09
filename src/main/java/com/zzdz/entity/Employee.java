package com.zzdz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Classname Employee
 * @Description TODO
 * @Date 2019/7/4 17:13
 * @Created by joe
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private Integer age;
    private Double salary;
    private Long addTime;

    private Status status;

    public Employee(String nickName) {
        this.nickName = nickName;
    }

    public Employee(String nickName, Integer age) {
        this.nickName = nickName;
        this.age = age;
    }

    public Employee(String nickName, Integer age, Double salary, Status status) {
        this.nickName = nickName;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }

    public enum Status {
        FREE,
        BUSY,
        VOCATION
    }
}