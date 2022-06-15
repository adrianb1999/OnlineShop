package com.adrian99.onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({"/index.html","/","/index"})
    public String indexPage() {
        return "index";
    }
    @RequestMapping("/category/*")
    public String categoryPage(){
        return "category";
    }
}
