package br.com.alura.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class HelloWorldController {

    @RequestMapping("/")
    //@ResponseBody
    public String helloWorld() {
        return "Hello World";
    }
}
