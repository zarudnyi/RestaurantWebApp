package zarudnyi.trials.restaurant.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    ModelAndView indexPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("index");

        return model;
    }


}
