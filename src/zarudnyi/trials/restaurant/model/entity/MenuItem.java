package zarudnyi.trials.restaurant.model.entity;

public class MenuItem {
    private MenuCategory category;
    private Integer id;
    private String name;
    private Integer price;
    private String description;

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;

        MenuItem menuItem = (MenuItem) o;

        if (getCategory() != null ? !getCategory().equals(menuItem.getCategory()) : menuItem.getCategory() != null)
            return false;
        if (getId() != null ? !getId().equals(menuItem.getId()) : menuItem.getId() != null) return false;
        if (getName() != null ? !getName().equals(menuItem.getName()) : menuItem.getName() != null) return false;
        if (getPrice() != null ? !getPrice().equals(menuItem.getPrice()) : menuItem.getPrice() != null) return false;
        return !(getDescription() != null ? !getDescription().equals(menuItem.getDescription()) : menuItem.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
