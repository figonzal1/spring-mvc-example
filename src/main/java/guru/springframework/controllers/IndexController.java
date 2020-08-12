package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    //Mapea la URL root ("/") al controlador con nombre "index"
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
