package zarudnyi.trials.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.ApplicationContextProvider;

import javax.sql.DataSource;

@Controller
public class TestController {

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Hello World!");
        model.setViewName("helloworld");

        ApplicationContextProvider appContext = new ApplicationContextProvider();





        return model;
    }
}
