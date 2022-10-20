package services;


import model.User;
import utils.FileUtils;

import java.time.LocalTime;

import java.util.List;
import java.util.Scanner;

public class AdminManager {
    private static String path = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileUser.txt";
    private static List<User> listUser = FileUtils.read(path);
    private static Scanner sc = new Scanner(System.in);

    public AdminManager() {
    }
    public static User createUser() {
//        List<User> listUser = FileUtils.read(path);
        long Id = System.currentTimeMillis() % 100000;
        boolean check;
        String userName;
        do {
            System.out.println(" Nhập tên tài khoản:");
            userName = inputName();
            check = false;
            for (User user : listUser) {
                if (user.getUserName().equals(userName)) {
                    check = true;
                    System.out.println(" Tên tài khoản đã tồn tại. Vui lòng nhập lại..");
                    break;
                }
            }
        } while (check);
        System.out.println(" => Nhập mật khẩu tài khoản:");
        String passWord = sc.nextLine();
        LocalTime dateCreate = LocalTime.now();
        User user = new User(Id, userName, passWord, "Nhanvien", dateCreate);
        return user;
    }

    public static User createAdmin() {
//        List<User> listUser = FileUtils.read(path);
        long Id = System.currentTimeMillis() / 100000;
        boolean check;
        String userName;
        do {
            System.out.println(" Nhập tên tài khoản:");
            userName = inputName();
            check = false;
            for (User user : listUser) {
                if (user.getUserName().equals(userName)) {
                    check = true;
                    System.out.println(" Tên tài khoản đã tồn tại. Vui lòng nhập lại..");
                    break;
                }
            }
        } while (check);
        System.out.println(" => Nhập mật khẩu tài khoản:");
        String passWord = inputPassWord();
        LocalTime dateCreate = LocalTime.now();
        User user = new User(Id, userName, passWord, "Admin", dateCreate);
        return user;
    }

    public static String inputName() {
        String name;
        do {
            name = sc.nextLine().trim();
            if (name.equals("") ) {
                System.out.println("Vui lòng nhập lại tên");
                System.out.print("⟹");
            }
        } while (name.equals(""));
        return name;
    }

    public static String inputPassWord() {
        String pass;
        do {
            pass = sc.nextLine().trim();
            if (pass.equals("") || pass.equals(null)) {
                System.out.println("Vui lòng nhập lại tên");
                System.out.print("⟹");
            }
        } while (pass.equals("") || pass.equals(null));
        return pass;
    }

    public static void insertUser(User user) {
        listUser.add(user);
        FileUtils.write(path, listUser);
    }

    public static int finUserByID(long iD) {
        int index = -1;
        for (int i = 0; i < listUser.size(); i++) {
            if (iD == (listUser.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void showAll() {
        System.out.println("********* Danh sách tài khoản ********");
        for (int i = 0; i < listUser.size(); i++) {
            System.out.println(listUser.get(i));
        }
    }

    public static void showAllEmployee() {
        listUser = FileUtils.read(path);
        System.out.println();
        System.out.println("  ══════════════════════════════════════ Danh Sách Nhân Viên ═════════════════════════════════════════");
        System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n", "ID", "UserName", "PassWord", "UserType", "DateCreate");
        System.out.println("  ───────────────────────────────────────────────────────────────────────────────────────────────────");
        for (User user : listUser) {
            if (user.getTypeUser().equals("Nhanvien")) {
                System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n",
                        user.getId(),
                        user.getUserName(),
                        user.getPassWord(),
                        user.getTypeUser(),
                        user.getDateCreate());
            }
        }
        System.out.println("  ═══════════════════════════════════════════════════════════════════════════════════════════════════");
        removeUser();
    }

    public static void showAllAdmin() {
//        listUser = FileUtils.read(path);
        System.out.println();
        System.out.println("  ══════════════════════════════════════ Danh Sách Nhân Viên ═════════════════════════════════════════");
        System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n", "ID", "UserName", "PassWord", "UserType", "DateCreate");
        System.out.println("  ───────────────────────────────────────────────────────────────────────────────────────────────────");
        for (User user : listUser) {
            if (user.getTypeUser().equals("Admin")) {
                System.out.printf("   %-25s %-20s %-20s %-20s %-20s\n",
                        user.getId(),
                        user.getUserName(),
                        user.getPassWord(),
                        user.getTypeUser(),
                        user.getDateCreate());
            }
        }
        System.out.println("  ═══════════════════════════════════════════════════════════════════════════════════════════════════");
        removeAdmin();
    }

    public static void removeAdmin() {
        while (true) {
            try {
                System.out.println("Nhập:   1. Xóa tài khoản                        0. Quay lại      ");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    long iD = inputIdUser();
                    int index = finUserByID(iD);
                    if ((listUser.get(index).getTypeUser()).equals("Admin")) {
                        listUser.remove(index);
                        FileUtils.write(path, listUser);
                        showAllEmployee();
                    } else {
                        System.out.println("Tài khoản có ID : " + iD + " không phải là tài khoản Admin ");
                        showAllEmployee();
                    }
                } else {
                    if (choice == 0) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        }
    }

    public static void removeUser() {
        while (true) {
            try {
                System.out.println("Nhập:   1. Xóa tài khoản                        0. Quay lại      ");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    long iD = inputIdUser();
                    int index = finUserByID(iD);
                    if ((listUser.get(index).getTypeUser()).equals("Nhanvien")) {
                        listUser.remove(index);
                        FileUtils.write(path, listUser);
                        showAllEmployee();
                    } else {
                        System.out.println("Tài khoản có ID : " + iD + " không phải là tài khoản Nhân viên ");
                        showAllEmployee();
                    }
                } else {
                    if (choice == 0) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Nhập sai! Vui lòng nhập lại");
            }
        }
    }

    public static long inputIdUser() {
        System.out.println("Nhập ID User :");
        System.out.print("⟹");
        long id = -1;
        boolean flag = true;
        do {
            try {
                id = Long.parseLong(sc.nextLine());
                int index = finUserByID(id);
                if (index == -1) {
                    System.out.println("ID không tồn tại. Vui lòng nhập lại");
                    flag = false;
                } else {
                    flag = true;
                }

            } catch (NumberFormatException ex) {
                System.out.println("ID không đúng định dạng");
                flag = false;
            }
        } while (flag == false);
        return id;
    }
}
