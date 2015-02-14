package com.appdirect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by NENE on 2015-02-12.
 */
@Controller
public class Welcome {

    @RequestMapping("/welcome")
    public String greeting(@RequestParam(value = "name",required = false,defaultValue = "No name")String name,Model model){
        model.addAttribute("name",name);
        return "welcome";
    }
}
