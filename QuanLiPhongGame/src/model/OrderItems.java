package model;

import java.io.Serializable;

public class OrderItems implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;
    private double total;

    public OrderItems() {
    }

    public OrderItems(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.total = (quantity) * (product.getPrice());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String toString() {
        return product.getName() + "    " + quantity + "    " + product.getPrice() + "     " + total;
    }
}
