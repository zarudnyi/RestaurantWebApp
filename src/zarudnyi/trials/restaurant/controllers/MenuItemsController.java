package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;
import zarudnyi.trials.restaurant.services.impl.MenuService;
@RestController
@RequestMapping(value = "/menu/**")
public class MenuItemsController {
    @Autowired
    MenuService menuService;

    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST})
    public String delete(@RequestParam(value = "id") Integer itemId){
        menuService.removeMenuItem(menuService.getItemById(itemId));

        return "success";
    }

    @RequestMapping(value = {"/update"}, method = {RequestMethod.POST})
    public String update(@ModelAttribute MenuItem menuItem, BindingResult result){
        MenuItem dbItem = menuService.getItemById(menuItem.getId());

        dbItem.setName(menuItem.getName());
        dbItem.setPrice(menuItem.getPrice());
        dbItem.setDescription(menuItem.getDescription());

        menuService.updateMenuItem (dbItem);

        return "success";
    }

    @RequestMapping(value = {"/delete/category"}, method = {RequestMethod.POST})
    public String deleteCategory(@RequestParam(value = "id") Integer categoryId){
        menuService.removeCategory(menuService.getCategoryById(categoryId));

        return "success";
    }

    @RequestMapping(value = {"/update/category"}, method = {RequestMethod.POST})
    public String updateCategory(@RequestParam(value = "id") Integer categoryId,
                                 @RequestParam(value = "name") String  name){
        MenuCategory dbCategory = menuService.getCategoryById(categoryId);

        dbCategory.setName(name);


        menuService.updateCategory(dbCategory);

        return "success";
    }






}
