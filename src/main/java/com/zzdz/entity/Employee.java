package com.zzdz.entity;

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
@Accessors(chain = true)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private Integer age;

    public Employee(String nickName, Integer age) {
        this.nickName = nickName;
        this.age = age;
    }
}
