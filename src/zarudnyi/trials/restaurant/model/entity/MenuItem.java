package zarudnyi.trials.restaurant.model.entity;

public class MenuItem {
    private MenuCategory category;
    private Integer id;
    private String name;
    private Integer price;
    private String description;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


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

    public String getDollarPrice() {
        String s = "" + price;
        if (s.length() == 0) {
            return "Free!";
        }
        if (s.length() > 2) {
            return "" + s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2, s.length());
        }
        if (s.length() > 1)
            return "0." + s;
        if (s.length() == 1) {
            return "0.0"+s;
        }
        return "";
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
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
        if (getDescription() != null ? !getDescription().equals(menuItem.getDescription()) : menuItem.getDescription() != null)
            return false;
        return !(getPicture() != null ? !getPicture().equals(menuItem.getPicture()) : menuItem.getPicture() != null);

    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        return result;
    }

}
