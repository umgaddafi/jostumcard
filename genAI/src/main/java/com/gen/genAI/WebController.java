package com.gen.genAI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {


    @GetMapping("/")
    public String homePage(){
     

        return "index";

    }
    
}
