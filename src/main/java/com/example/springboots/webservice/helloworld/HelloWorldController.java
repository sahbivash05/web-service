package com.example.springboots.webservice.helloworld;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World Bean");
    }

    @GetMapping(path = "/hello-world-bean/pathvariable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Your name is %s",name));
    }
    @GetMapping(path = "/hello-world-bean-i18n")
    public String helloWorldBeanI18N(){
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }

}
