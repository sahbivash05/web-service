package com.example.springboots.webservice.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filtering")
    public SomeBean retrieveSomeBean () {
     return new SomeBean("a","b","c");
    }
    @GetMapping("/filtering-list")
    public List<SomeBean> retrieveSomeBeanList () {
        return Arrays.asList(new SomeBean("a","b","c"),new SomeBean("e","f","g"));
    }
}
