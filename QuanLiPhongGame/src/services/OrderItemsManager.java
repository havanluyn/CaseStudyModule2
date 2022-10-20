package services;

import model.Client;
import model.EnumProduct;
import model.OrderItems;
import model.Product;
import sun.security.jca.GetInstance;
import utils.DateUtils;
import utils.FileUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderItemsManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String userName;
    private static final Scanner sc = new Scanner(System.in);
    private List<OrderItems> orderItemsList;
    private Instant createAt;

    public OrderItemsManager() {
        this.orderItemsList = new ArrayList<>();
    }

    public OrderItemsManager(int id, String userName) {
        this.id = id;
        this.userName = userName;
        this.orderItemsList = new ArrayList<>();
    }

    public OrderItemsManager(int id, String userName, Instant createAt) {
        this.id = id;
        this.userName = userName;
        this.orderItemsList = new ArrayList<>();
        this.createAt = createAt;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderItems> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItems> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public static OrderItems createOrderItems() {
        String name = ProductManager.inputNameProduct(EnumProduct.CREATE);
        Product product = ProductManager.findProduct(name);
        while (product == null) {
            name = ProductManager.inputNameProduct(EnumProduct.CREATE);
            product = ProductManager.findProduct(name);
        }
        int quantity = inputQuantityProduct();
        while (ProductManager.findProduct(name).getAmount() < quantity) {
            System.out.println("Số lượng không đủ.Vui long nhập ít hơn");
            quantity = inputQuantityProduct();
        }
        ProductManager.findProduct(name).setAmount(ProductManager.findProduct(name).getAmount() - quantity);
        OrderItems orderItems = new OrderItems(product, quantity);
        return orderItems;
    }

    public static OrderItems createOrderItems(String nameProduct, int quantity) {
        Product product = ProductManager.findProduct(nameProduct);
        ProductManager.findProduct(nameProduct).setAmount(ProductManager.findProduct(nameProduct).getAmount() - quantity);
        OrderItems orderItems = new OrderItems(product, quantity);
        return orderItems;
    }

    public static void addOrderItems(OrderItems orderItems, OrderItemsManager orderItemsManager) {
        boolean check = false;
        for (OrderItems item : orderItemsManager.orderItemsList) {
            if ((item.getProduct().getName()).equals(orderItems.getProduct().getName())) {
                item.setQuantity(orderItems.getQuantity() + item.getQuantity());
                check = true;
            }
            break;
        }
        if (check == false) {
            orderItemsManager.orderItemsList.add(orderItems);
        }

    }

    public static void removeOrderItems(OrderItems orderItems, OrderItemsManager orderItemsManager) {
        for (OrderItems item : orderItemsManager.orderItemsList) {
            if ((item.getProduct().getName()).equals(orderItems.getProduct().getName())) {
                if (item.getQuantity() - orderItems.getQuantity() <= 0) {
                    orderItemsManager.orderItemsList.remove(item);
                    break;
                } else {
                    item.setQuantity(item.getQuantity() + orderItems.getQuantity());
                }
            }
        }
    }

    public static int inputQuantityProduct() {
        System.out.println("Nhập số lượng sản phẩm:");
        System.out.print("⟹");
        int quantity = -1;
        boolean flag = true;
        do {
            try {
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity < 0) {
                    System.out.println("Số lượng không được nhỏ hơn 0.");
                    System.out.print("⟹ ");
                    flag = false;
                } else {
                    flag = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Số lượng không hợp lệ");
                flag = false;
            }
        } while (flag == false);
        return quantity;
    }

    public void toPrint() {
        System.out.println("Thông tin Order: ");
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║               ►   Order ◄                ║");
        System.out.println("  ╟──────────────────────────────────────────╢");
        System.out.printf("  ║  ID: %-10d              %-10s  ║\n", id, DateUtils.convertInstantToStringFormat(createAt));
        System.out.printf("  ║  Tên tài khoản: %-15s          ║\n", userName);
        System.out.println("  ╟------------------------------------------╢");
        double total = 0;
        for (int j = 0; j < orderItemsList.size(); j++) {
            total = total + orderItemsList.get(j).getTotal();
            String name = orderItemsList.get(j).getProduct().getName();
            int quantity = orderItemsList.get(j).getQuantity();
            long price = orderItemsList.get(j).getProduct().getPrice();
            System.out.printf("  ║  %-10s    %-5s        %-10d   ║\n", name, quantity, price);
        }
        System.out.println("  ╟      ------------------------------      ╢");
        System.out.printf("  ║  Total                      %-10s   ║\n", total);
        System.out.println("  ╚══════════════════════════════════════════╝");
        System.out.println("           ------------------------------------------ ");

    }
}

