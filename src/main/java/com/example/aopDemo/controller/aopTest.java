package com.example.aopDemo.controller;


import com.example.aopDemo.aspect.annotation.McPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mc
 */
@RestController
@RequestMapping("/test")
public class aopTest {

    @GetMapping(value = "/list")
    @McPermission("kei")
    public String list() {
        System.out.println("成功");
        return "success11";
    }


    @GetMapping(value = "/list1")
    @McPermission("NiuNiu")
    public String list1() {
        System.out.println("成功");
        return "success";
    }
}
