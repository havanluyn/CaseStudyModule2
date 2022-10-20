package view;

import model.Client;
import services.EmployeeManager;
import services.ProductManager;

import java.util.Scanner;

public class EmployeeView {
    private static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║                 ► MENU ◄                 ║");
        System.out.println("  ╟──────────────────────────────────────────╢");
        System.out.println("  ║                                          ║");
        System.out.println("  ║         1. Quản lí người chơi            ║");
        System.out.println("  ║         2. Quản lí Order                 ║");
        System.out.println("  ║         3. Quản lí sản phẩm              ║");
        System.out.println("  ║         0. Thoát                         ║");
        System.out.println("  ║                                          ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    public static void menuEmployee() {
        System.out.println("  ╔═══════════════════════════════════════════════╗");
        System.out.println("  ║              ► MENU NHÂN VIÊN ◄               ║");
        System.out.println("  ╟───────────────────────────────────────────────╢");
        System.out.println("  ║                                               ║");
        System.out.println("  ║     1. Tạo tài khoản người chơi               ║");
        System.out.println("  ║     2. Kiểm tra tài khoản người chơi          ║");
        System.out.println("  ║     3. Danh sách tài khoản người chơi         ║");
        System.out.println("  ║     4. Nạp tiền vào tài khoản người chơi      ║");
        System.out.println("  ║     5. Reset passWord tài khoản               ║");
        System.out.println("  ║     0. Thoát                                  ║");
        System.out.println("  ║                                               ║");
        System.out.println("  ╚═══════════════════════════════════════════════╝");
    }

    public static void menuEmployeeOrder() {
        System.out.println("  ╔═════════════════════════════════════════════╗");
        System.out.println("  ║             ► MENU NHÂN VIÊN ◄              ║");
        System.out.println("  ╟─────────────────────────────────────────────╢");
        System.out.println("  ║                                             ║");
        System.out.println("  ║          1. Tạo Order                       ║");
        System.out.println("  ║          2. Hiển thị Order                  ║");
        System.out.println("  ║          3. Sửa Order                       ║");
        System.out.println("  ║          4. Xóa Order                       ║");
        System.out.println("  ║          0. Thoát                           ║");
        System.out.println("  ║                                             ║");
        System.out.println("  ╚═════════════════════════════════════════════╝");
    }

    public static void menuEmployeeProduct() {
        System.out.println("  ╔═════════════════════════════════════════════╗");
        System.out.println("  ║             ► MENU SẢN PHẨM ◄               ║");
        System.out.println("  ╟─────────────────────────────────────────────╢");
        System.out.println("  ║                                             ║");
        System.out.println("  ║        1. Thêm sản phẩm mới                 ║");
        System.out.println("  ║        2. Hiển thị danh sách sản phẩm       ║");
        System.out.println("  ║        3. Sửa sản phẩm                      ║");
        System.out.println("  ║        4. Xóa sản phẩm                      ║");
        System.out.println("  ║        0. Thoát                             ║");
        System.out.println("  ║                                             ║");
        System.out.println("  ╚═════════════════════════════════════════════╝");
    }

    public static void selectUserMenuEmployee() {
        int choice = -1;
        do {
            try {
                menuEmployee();
                System.out.println("Vui lòng chọn chức năng.");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        Client client = EmployeeManager.createClient();
                        EmployeeManager.insertClient(client);
                        System.out.println("");
                        System.out.println(client);
                        break;
                    case 2:
                        EmployeeManager.showClient();
                        break;
                    case 3:
                        EmployeeManager.showAllClient();
                        break;
                    case 4:
                        EmployeeManager.setAmountbyId();
                        break;
                    case 5:
                        EmployeeManager.resetPassWordById();
                        break;
                    case 0:
                        menu();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        } while (choice != 0);
    }

    public static void selectMenuEmployeeProduct() {
        int choice = -1;
        do {
            menuEmployeeProduct();
            try {
                System.out.println("Vui lòng chọn chức năng.");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        ProductManager.addProduct();
                        break;
                    case 2:
                        ProductManager.showAllProduct();
                        break;
                    case 3:
                        ProductManager.editProduct();
                    case 4:
                        ProductManager.removeProduct();
                        break;
                    case 0:
                        menu();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        } while (choice != 0);
    }

    public static void selectMenuEmployee() {
        int choice = -1;
        do {
            try {
                menu();
                System.out.println("Vui lòng chọn chức năng.");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        selectUserMenuEmployee();
                        break;
                    case 2:
                        selectMenuEmployeeOrder();
                        break;
                    case 3:
                        selectMenuEmployeeProduct();
                        break;
                    case 0:
                        MenuView.menuLogin();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        } while (choice != 0);
    }

    public static void selectMenuEmployeeOrder() {
        int choice = -1;
        do {
            menuEmployeeOrder();
            try {
                System.out.println("Vui lòng chọn chức năng.");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        EmployeeManager.insertOrder(EmployeeManager.createOrder());
                        break;
                    case 2:
                        EmployeeManager.showOrderbyId();
                        break;
                    case 3:
                        EmployeeManager.editOrder();
                        break;
                    case 4:
                        EmployeeManager.removeOrder();
                        break;
                    case 0:
                        menu();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
                e.printStackTrace();
            }
        } while (choice != 0);
    }
}
