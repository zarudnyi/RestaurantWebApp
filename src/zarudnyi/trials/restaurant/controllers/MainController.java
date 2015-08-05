package zarudnyi.trials.restaurant.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.SecurityConfig;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;
import zarudnyi.trials.restaurant.services.impl.MenuService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.*;

@Controller
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MainController {

    @Autowired
    private MenuService menuService;



    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView indexPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("index");

        model.addObject("gallery", menuService.getGallery(4));

        List<MenuItem> dishes = menuService.getIndexPageDishes(6);

        model.addObject("firstDishes", dishes.subList(0,3));
        model.addObject("secondDishes", dishes.subList(3,6));


        return model;
    }



    @RequestMapping(value = {"/menu"}, method = {RequestMethod.GET})
    public ModelAndView menuPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("menu");

        List<MenuItem> allItems = menuService.getAllItems();

        Map<MenuCategory,List<MenuItem>> menu = new HashMap<MenuCategory, List<MenuItem>>();
        List<MenuCategory> allCategories = menuService.getAllCategories();

        for (MenuCategory category:allCategories){
            menu.put(category,new ArrayList<MenuItem>());
        }

        for (MenuItem item : allItems){
            menu.get(item.getCategory()).add(item);
        }

        model.addObject("menu",menu);

        return model;
    }

    @RequestMapping(value = {"/gallery"}, method = {RequestMethod.GET})
    public ModelAndView galleryPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("gallery");
        model.addObject("gallery", menuService.getGallery(40));

        return model;
    }

    @RequestMapping(value = {"/contact"}, method = {RequestMethod.GET})
    public ModelAndView contactPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("contact");
        return model;
    }


    @RequestMapping(value = {"/menu/createItem"}, method = {RequestMethod.GET})
    public ModelAndView createItemPage(){
        ModelAndView model = new ModelAndView();
        model.addObject("item", new CreateItemStub());
        Map<Integer,String> categoriesMap = new HashMap<Integer, String>();

        List<MenuCategory> allCategories = menuService.getAllCategories();
        for (MenuCategory category:allCategories){
            categoriesMap.put(category.getId(),category.getName());
        }

        model.addObject("categories", categoriesMap);
        model.setViewName("createItem");
        return model;
    }
    @RequestMapping(value = {"/menu/createItem"}, method = {RequestMethod.POST})
    public ModelAndView createItem(@ModelAttribute CreateItemStub itemStub,BindingResult result){

        itemStub.setCategory(menuService.getCategoryById(itemStub.getCategoryId()));
        if (itemStub.getFloatPrice()==null)
            itemStub.setFloatPrice((float) 0);
        itemStub.setPrice(Math.round(itemStub.getFloatPrice() * 100));

        menuService.createItem(itemStub);

        return new ModelAndView("redirect:/menu");
    }

    @RequestMapping(value = {"/menu/createCategory"}, method = {RequestMethod.GET})
    public ModelAndView createCategoryPage(){
        ModelAndView model = new ModelAndView();
        model.addObject("category", new MenuCategory());

        model.setViewName("createCategory");
        return model;
    }
    @RequestMapping(value = {"/menu/createCategory"}, method = {RequestMethod.POST})
    public ModelAndView createCategory(@ModelAttribute MenuCategory menuCategory,BindingResult result){
        menuService.createCategory(menuCategory);

        return new ModelAndView("redirect:/menu");
    }

    public static class CreateItemStub extends MenuItem{
        private Integer categoryId;
        private Float floatPrice;

        public CreateItemStub(){}

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Float getFloatPrice() {
            return floatPrice;
        }

        public void setFloatPrice(Float floatPrice) {
            this.floatPrice = floatPrice;
        }
    }



}
