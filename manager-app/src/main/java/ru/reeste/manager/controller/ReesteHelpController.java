package ru.reeste.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("help")
public class ReesteHelpController {

    @GetMapping("budget")
    public String budget() {
        return "help/budget";
    }

    @GetMapping("budget-lines")
    public String budgetLines() {
        return "help/budget-lines";
    }

    @GetMapping("development")
    public String development() {
        return "help/development";
    }
}
