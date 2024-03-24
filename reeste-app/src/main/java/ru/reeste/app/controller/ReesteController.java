package ru.reeste.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReesteController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}