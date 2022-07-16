package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.model.Product;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.service.ProductService;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ViewController {

    private final ProductService productService;
    private final UserService userService;

    public ViewController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @RequestMapping({"/index.html","/","/index"})
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/category/*")
    public String categoryPage(Model model, Principal principal){

        if(principal != null && principal.getName() != null && userService.findByUsername(principal.getName()) != null)
            model.addAttribute("user",true);
        else
            model.addAttribute("user", false);
        return "category";
    }

    @RequestMapping("/product/{id}")
    public String productPage(@PathVariable Long id, Model model, Principal principal) {

        if(principal != null && principal.getName() != null && userService.findByUsername(principal.getName()) != null)
            model.addAttribute("user",true);
        else
            model.addAttribute("user", false);

        Product product = productService.findById(id);

        //TODO if no product return 404 PAGE!!!!
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping("/cart")
    public String cartPage (){
        return "cart";
    }
}
