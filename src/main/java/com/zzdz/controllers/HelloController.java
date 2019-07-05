package com.zzdz.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Classname HellowController
 * @Description TODO
 * @Date 2019/7/3 10:40
 * @Created by joe
 */

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello " + new Date();
    }
}
