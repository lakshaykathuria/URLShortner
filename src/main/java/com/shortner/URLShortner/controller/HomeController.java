package com.shortner.URLShortner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = { "/", "/{path:^(?!shorten$).*$}" })
    public String index() {
        // Serve the frontend UI
        return "index.html";
    }
}
