package com.frozen.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class PageController {
    @GetMapping("editor")
    public String editor(HttpServletRequest request) {
        return "redirect:/modeler.html?" + request.getQueryString();
    }

    @GetMapping
    public String index() {
        return "/index";
    }
}
