package services;

import model.Client;
import model.OrderItems;
import model.Product;
import model.User;
import utils.DateUtils;
import utils.FileUtils;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {
    private static final String path = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileClient.txt";
    private static final String pathItem = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileOrder.txt";
    private static List<Client> clientList=FileUtils.read(path);;
    public static List<OrderItemsManager> orderItemsManagerList = FileUtils.read(pathItem);
    private static final Scanner sc = new Scanner(System.in);

    public EmployeeManager() {
    }

    public static Client createClient() {
//        clientList = FileUtils.read(path);
        long Id = System.currentTimeMillis() % 100000;
        boolean check;
        String clientName;
        do {
            System.out.println(" Nhập tên tài khoản:");
            clientName = AdminManager.inputName();
            check = false;
            for (Client client : clientList) {
                if (client.getNameAcount().equals(clientName)) {
                    check = true;
                    System.out.println(" Tên tài khoản đã tồn tại. Vui lòng nhập lại..");
                }
            }
        } while (check);
        System.out.println("Nhập mật khẩu muốn tạo");
        String passWord = AdminManager.inputPassWord();
        double amount = inputAmount();
        Instant dateCreate = Instant.now();
        Client client = new Client(Id, clientName, passWord, amount, dateCreate);
        return client;
    }

    public static void insertClient(Client client) {
//        clientList = FileUtils.read(path);
        clientList.add(client);
        FileUtils.write(path, clientList);
    }

    private static int findClientByID(long ID) {
        int index = -1;
        for (int i = 0; i < clientList.size(); i++) {
            if (ID == (clientList.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static int findClientByName(String name) {
        int index = -1;
        for (int i = 0; i < clientList.size(); i++) {
            if (name.equals(clientList.get(i).getNameAcount())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void showClient() {
//        clientList = FileUtils.read(path);
        System.out.println("Nhập tên tài khoản muốn kiểm tra");
        String userName = AdminManager.inputName();
        System.out.println("  ═══════════════════════════════════ Danh sách tài khoản ═════════════════════════════════════════");
        System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n", "ID", "ClientName", "PassWord", "Amount", "DateCreate");
        System.out.println("  ────────────────────────────────────────────────────────────────────────────────────────────────");
        for (int i = 0; i < clientList.size(); i++) {
            if ((clientList.get(i).getNameAcount()).contains(userName)) {
                System.out.printf("   %-25s %-20s %-20s %-20s\n",
                        clientList.get(i).getId(),
                        clientList.get(i).getNameAcount(),
                        clientList.get(i).getAmount(),
                        clientList.get(i).getPassWord(),
                        DateUtils.convertInstantToStringFormat(clientList.get(i).getDateCreate()));
            }
        }
        System.out.println("  ═════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Nhập 4 để nạp tiền           |        Nhập 5 để đổi mật khẩu            ");
        System.out.println("           ------------------------------------------ ");
    }

    public static void showAllClient() {
//        clientList = FileUtils.read(path);
        System.out.println();
        System.out.println("  ═══════════════════════════════════ Danh sách tài khoản ═════════════════════════════════════════");
        System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n", "ID", "ClientName", "PassWord", "Amount", "DateCreate");
        System.out.println("  ─────────────────────────────────────────────────────────────────────────────────────────────────");
        for (Client client : clientList) {
            System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n",
                    client.getId(),
                    client.getNameAcount(),
                    client.getPassWord(),
                    client.getAmount(),
                    DateUtils.convertInstantToStringFormat(client.getDateCreate()));
        }
        System.out.println("  ═════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void setAmountbyId() {
//        clientList = FileUtils.read(path);
        do {
            String name = inputNameClient();
            int index = findClientByName(name);
            if (index != -1) {
                System.out.println("Nhập số tiền muốn nạp");
                double amount = sc.nextDouble();
                clientList.get(index).setAmount(amount + clientList.get(index).getAmount());
                FileUtils.write(path, clientList);
                break;
            } else {
                System.out.println("Không tìm thấy tài khoản !!");
            }
        } while (true);

    }

    public static void resetPassWordById() {
//        clientList = FileUtils.read(path);
        String name = inputNameClient();
        int index = findClientByName(name);
        while (index == -1) {
            System.out.println("Nhập lại tên tài khoản muốn reset");
            index = findClientByName(name);
        }
        clientList.get(index).setPassWord("00000000");
        System.out.println("Tài khoản đã được reset mật khẩu về mặc định");
        FileUtils.write(path, clientList);
    }

    public static void insertOrder(OrderItemsManager orderItemsManager) {
        orderItemsManagerList = FileUtils.read(pathItem);
        orderItemsManagerList.add(orderItemsManager);
        FileUtils.write(pathItem, orderItemsManagerList);
    }

    public static OrderItemsManager createOrder() {
        OrderItemsManager orderItemsManager = new OrderItemsManager();
        int Id = (int) (System.currentTimeMillis() % 100000);
        String userName = inputNameClient();

        orderItemsManager.setId(Id);
        orderItemsManager.setUserName(userName);
        Instant instant = Instant.now();
        orderItemsManager.setCreateAt(instant);
        OrderItems orderItems;
        orderItems = OrderItemsManager.createOrderItems();
        OrderItemsManager.addOrderItems(orderItems, orderItemsManager);
        boolean flag;
        do {
            System.out.println("Bạn muốn order thêm không?");
            System.out.println("Nhấn 'y' để tiếp tục order || Nhấn 'no' để quay lại!");
            String choice = sc.nextLine();
            switch (choice) {
                case "y":
                    orderItems = OrderItemsManager.createOrderItems();
                    OrderItemsManager.addOrderItems(orderItems, orderItemsManager);
                    flag = true;
                    break;
                case "no":
                    flag = false;
                    break;
                default:
                    System.out.println("Bạn nhập sai, vui lòng nhập lại");
                    flag = true;
                    break;
            }
        } while (flag);
        return orderItemsManager;
    }

    private static int findOrderByID(int ID) {
        orderItemsManagerList = FileUtils.read(pathItem);
        int index = -1;
        for (int i = 0; i < orderItemsManagerList.size(); i++) {
            if (ID == (orderItemsManagerList.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void showAllOrder() {
//        orderItemsManagerList = FileUtils.read(pathItem);
        for (int i = 0; i < orderItemsManagerList.size(); i++) {
            orderItemsManagerList.get(i).toPrint();
        }
    }

    public static void showOrderbyId() {
//        orderItemsManagerList = FileUtils.read(pathItem);
        showAllOrder();
        System.out.println("Nhập Id Order để in hoá đơn");
        int iD = Integer.parseInt(sc.nextLine());
        System.out.println("Thông tin Order: ");
        for (int i = 0; i < orderItemsManagerList.size(); i++) {
            if ((orderItemsManagerList.get(i).getId()) == iD) {
                System.out.println("  ╔══════════════════════════════════════════╗");
                System.out.println("  ║               ► Hóa Đơn ◄                ║");
                System.out.println("  ╟──────────────────────────────────────────╢");
                System.out.printf("  ║  ID: %-10d              %-10s  ║\n", iD, DateUtils.convertInstantToStringFormat(orderItemsManagerList.get(i).getCreateAt()));
                System.out.printf("  ║  Tên tài khoản: %-15s          ║\n", orderItemsManagerList.get(i).getUserName());
                System.out.println("  ╟------------------------------------------╢");
                double total = 0;
                for (int j = 0; j < orderItemsManagerList.get(i).getOrderItemsList().size(); j++) {
                    total = total + orderItemsManagerList.get(i).getOrderItemsList().get(j).getTotal();
                    String name = orderItemsManagerList.get(i).getOrderItemsList().get(j).getProduct().getName();
                    int quantity = orderItemsManagerList.get(i).getOrderItemsList().get(j).getQuantity();
                    long price = orderItemsManagerList.get(i).getOrderItemsList().get(j).getProduct().getPrice();
                    System.out.printf("  ║  %-10s    %-5s        %-10d   ║\n", name, quantity, price);
                }
                System.out.println("  ╟      ------------------------------      ╢");
                System.out.printf("  ║  Total                      %-10s   ║\n", total);
                System.out.println("  ╚══════════════════════════════════════════╝");
            }
        }
        System.out.println("           ------------------------------------------ ");
    }

    public static void editOrder() {
//        orderItemsManagerList = FileUtils.read(pathItem);
        showAllOrder();
        System.out.println("Nhập Id Order cần thêm");
        int iD = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < orderItemsManagerList.size(); i++) {
            if ((orderItemsManagerList.get(i).getId()) == iD) {
                System.out.println(orderItemsManagerList.get(i));
                System.out.println(" 1. Order thêm sản phẩm ");
                System.out.println(" 2. Sửa Order sản phẩm ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        OrderItems orderItems = OrderItemsManager.createOrderItems();
                        orderItemsManagerList.get(i).getOrderItemsList().add(orderItems);
                        System.out.println("Bạn vừa order thêm " + orderItems);
                        break;
                    case 2:
                        System.out.println(" Nhập tên sản phẩm cần sửa");
                        String name = sc.nextLine();
                        for (int j = 0; j < orderItemsManagerList.get(i).getOrderItemsList().size(); j++) {
                            if (orderItemsManagerList.get(i).getOrderItemsList().get(i).getProduct().getName().equals(name)) {
                                int quantity = OrderItemsManager.inputQuantityProduct();
                                orderItemsManagerList.get(i).getOrderItemsList().get(i).setQuantity(quantity);
                                System.out.println("Đã sửa thành công");
                               orderItemsManagerList.get(i).toPrint();
                                break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Nhập sai, vui lòng nhập lại.");
                        break;
                }
            }
        }
        FileUtils.write(pathItem, orderItemsManagerList);
    }

    public static void removeOrder() {
//        orderItemsManagerList = FileUtils.read(pathItem);
        showAllOrder();
        System.out.println("Nhập Id Order muốn xóa");
        int iD = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < orderItemsManagerList.size(); i++) {
            if ((orderItemsManagerList.get(i).getId()) == iD) {
                orderItemsManagerList.remove(i);
            }
        }
        FileUtils.write(pathItem, orderItemsManagerList);
    }

    private static double inputAmount() {
        System.out.println("Nhập số tiền muốn nạp:");
        System.out.print("⟹");
        double amount = -1;
        boolean flag = true;
        do {
            try {
                amount = Double.parseDouble(sc.nextLine());
                if (amount < 1000) {
                    System.out.println("Số tiền không được nhỏ hơn 0.");
                    System.out.print("⟹ ");
                    flag = false;
                } else {
                    flag = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Số tiền không hợp lệ");
                flag = false;
            }
        } while (flag == false);
        return amount;
    }

    public static String inputNameClient() {
        System.out.println("Nhập tên tài khoản :");
        System.out.print("⟹");
        String name;
        do {
            name = sc.nextLine().trim();
            if (name.equals("") || name.equals(null)) {
                System.out.println("Vui lòng nhập lại tên tài khoản");
                System.out.print("⟹");
            }
        } while (name.equals("") || name.equals(null));
        return name;
    }
}
