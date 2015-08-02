package zarudnyi.trials.restaurant.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.AppConfig;

@Controller
public class TestController {


    @Secured("ROLE_TEST")
    @RequestMapping(value = {"/reset"}, method = {RequestMethod.GET})
    ModelAndView profilePage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("index");
        AppConfig.resetSchema();

        return model;
    }



}
