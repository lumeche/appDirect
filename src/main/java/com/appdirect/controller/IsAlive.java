package com.appdirect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * Created by NENE on 2015-02-12.
 */
@Controller
public class IsAlive {

    final static Logger logger = LoggerFactory.getLogger(IsAlive.class);

    @RequestMapping("/isAlive")
    public String greeting(@RequestParam(value = "name",required = false,defaultValue = "No name")String name,Model model){
        String date=getFormatedCurrentDate();
        logger.debug(String.format("isAlive at %s",date));
        model.addAttribute("name",name);
        model.addAttribute("date",date );
        return "isAlive";
    }

    public String getFormatedCurrentDate() {
        Calendar calendar= Calendar.getInstance();
        return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(calendar.getTime());
    }
}
