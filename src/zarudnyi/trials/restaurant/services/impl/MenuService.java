package zarudnyi.trials.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.model.dao.MenuDAO;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;



    public List<String> getGallery (int size){

        List<MenuItem> allItems = menuDAO.findAllItems();
        List<String> res = new ArrayList<String>();

        for (MenuItem item:allItems){


            if (res.size()==size)
                break;
            if (item.getPicture()!=null && (!res.contains(item.getPicture()))){
                res.add(item.getPicture());
            }

        }

        return res;
    }

    public List<MenuItem> getIndexPageDishes(int size){

        List<MenuItem> allItems = menuDAO.findAllItems();
        List<MenuItem> res = new ArrayList<MenuItem>();

        for (MenuItem item:allItems){

            if (res.size()==size)
                break;
            if (item.getPicture()!=null && item.getDescription()!=null && !item.getDescription().equals("")){
                res.add(item);
            }

        }

        return res;

    }

    public List<MenuItem> getAllItems(){
        return menuDAO.findAllItems();
    }

    public MenuItem getItemById(Integer itemId) {
        return menuDAO.findItemById(itemId);
    }

    public void removeMenuItem(MenuItem item){
        menuDAO.removeMenuItem(item);
    }

    public void updateMenuItem(MenuItem item) {
        menuDAO.updateMenuItem(item);
    }
    public void updateCategory(MenuCategory category) {
        menuDAO.updateCategory(category);
    }
    public void removeCategory(MenuCategory category){
        menuDAO.removeCategory(category);
    }

    public MenuItem createItem (){
        return menuDAO.createMenuItem();
    }
    public MenuItem createItem (MenuItem menuItem){
        MenuItem created =  menuDAO.createMenuItem();
        menuItem.setId(created.getId());
        menuDAO.updateMenuItem(menuItem);
        return menuItem;
    }

    public MenuCategory createCategory(){
        return menuDAO.createCategory();
    }

    public MenuCategory createCategory(MenuCategory category){
        MenuCategory created =  menuDAO.createCategory();
        category.setId(created.getId());
        menuDAO.updateCategory(category);
        return category;
    }

    public MenuCategory getCategoryById(Integer itemId) {
        return menuDAO.findCategoryById(itemId);
    }

    public List<MenuCategory> getAllCategories() {
        return menuDAO.findAllCategories();
    }
}
