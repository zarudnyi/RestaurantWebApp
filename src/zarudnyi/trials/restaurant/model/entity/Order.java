package zarudnyi.trials.restaurant.model.entity;


import java.util.Date;

public class Order {
    private Integer id;
    private Integer userId;
    private Integer groupId;
    private String description;
    private Integer checkOutSum;
    private Integer statusId;
    private Date orderDate;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCheckOutSum() {
        return checkOutSum;
    }

    public void setCheckOutSum(Integer checkOutSum) {
        this.checkOutSum = checkOutSum;
    }


}
