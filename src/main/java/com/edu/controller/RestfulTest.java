package com.edu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("student")
public class RestfulTest {


    @RequestMapping("/index/{id}")
    public @ResponseBody String test(@PathVariable("id") int id) {
        return "rest" + id;
    }
}
