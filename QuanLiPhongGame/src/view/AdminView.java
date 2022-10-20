package view;

import model.User;
import services.AdminManager;

import java.util.Scanner;

public class AdminView {
    private static Scanner scanner = new Scanner(System.in);
    public static void menuAdmin() {
        System.out.println("  ╔═════════════════════════════════════════════╗");
        System.out.println("  ║               ► MENU ADMIN ◄                ║");
        System.out.println("  ╟─────────────────────────────────────────────╢");
        System.out.println("  ║                                             ║");
        System.out.println("  ║      1. Tạo tài khoản nhân viên             ║");
        System.out.println("  ║      2. Danh sách tài khoản nhân viên       ║");
        System.out.println("  ║      3. Danh sách tài khoản Admin           ║");
        System.out.println("  ║      4. Tạo thêm tài khoản Admin            ║");
        System.out.println("  ║      5. Kiểm tra doanh thu                  ║");
        System.out.println("  ║                                             ║");
        System.out.println("  ║            Nhấn '0' để thoát                ║");
        System.out.println("  ╚═════════════════════════════════════════════╝");
        selectUserMenuAdmin();
    }

    public static void selectUserMenuAdmin() {
        int choice = -1;
        do {
            try {
                System.out.println("Vui lòng chọn chức năng.");
                 choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        User user = AdminManager.createUser();
                        AdminManager.insertUser(user);
                        System.out.println("");
                        System.out.println(user);
                        AdminView.menuAdmin();
                        break;
                    case 2:
                        AdminManager.showAllEmployee();
                        AdminView.menuAdmin();
                        break;
                    case 3:
                        AdminManager.showAllAdmin();
                        AdminView.menuAdmin();
                        break;
                    case 4:
                        AdminManager.insertUser(AdminManager.createAdmin());
                        AdminView.menuAdmin();
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
        } while (true);
    }
}
