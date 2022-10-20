package view;

import model.User;
import services.AdminManager;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private static List<User> listUser=new ArrayList<>();
    private static String path = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileUser.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void menuLogin() {
        System.out.println(" ╔════════════════════════════╗");
        System.out.println(" ║       ► Đăng nhập ◄        ║");
        System.out.println(" ╟────────────────────────────╢");
        System.out.println(" ║                            ║");
        System.out.println(" ║    1.Đăng nhập             ║");
        System.out.println(" ║    0.Thoát chương trình    ║");
        System.out.println(" ║                            ║");
        System.out.println(" ╚════════════════════════════╝");
        createAdmin0();
        selectMenu();
    }

    public static void createAdmin0(){
        listUser = FileUtils.read(path);
        if (listUser.isEmpty()){
            listUser.add( new User("Admin","Admin","Admin"));
            FileUtils.write(path,listUser);
        }
    }
    public static void selectMenu() {
        do {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        login();
                        menuLogin();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Chọn chức năng không đúng! Vui lòng chọn lại");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        } while (true);
    }

    public static void login() {
        System.out.println("*  Tên tài khoản:           ");
        String nameLogin = scanner.nextLine();
        System.out.println("*  Nhập Mật khẩu:           ");
        String passLogin = scanner.nextLine();
        listUser = FileUtils.read(path);
        boolean check=false;
        for (User user : listUser) {
            if (user.getUserName().equals(nameLogin) && user.getPassWord().equals(passLogin)) {
                if (user.getTypeUser().equals("Nhanvien")) {
                    EmployeeView.selectMenuEmployee();
                    check=true;
                    break;
                } else {
                    AdminView.menuAdmin();
                    check=true;
                    break;
                }
            }
        }
        if(check==false){
            System.out.println("Tài khoản hoặc mật khẩu sai. Vui lòng đăng nhập lại hoặc bấm 0 để thoát !!");
        }
    }
}
