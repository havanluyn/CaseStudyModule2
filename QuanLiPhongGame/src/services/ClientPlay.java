package services;

import model.Client;
import sever.ClientDemo;
import utils.FileUtils;
import java.util.List;
import java.util.Scanner;

public class ClientPlay {
    private static final String path = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileClient.txt";
    private static final Scanner sc = new Scanner(System.in);
    public static void menuLoginClient() {
        System.out.println(" ╔════════════════════════════╗");
        System.out.println(" ║       ► Đăng nhập ◄        ║");
        System.out.println(" ╟────────────────────────────╢");
        System.out.println(" ║                            ║");
        System.out.println(" ║    1.Đăng nhập             ║");
        System.out.println(" ║    0.Thoát chương trình    ║");
        System.out.println(" ║                            ║");
        System.out.println(" ╚════════════════════════════╝");
    }
    public static void loginClient() {
        System.out.println("*  Tên tài khoản:           ");
        String nameLogin = sc.nextLine();
        System.out.println("*  Nhập Mật khẩu:           ");
        String passLogin = sc.nextLine();
        List<Client> clients = FileUtils.read(path);
        boolean check = false;
        for (Client client : clients) {
            if (client.getNameAcount().equals(nameLogin) && client.getPassWord().equals(passLogin)&&client.getAmount()>0) {
                System.out.println("Đăng nhập thành công");
                downTime(client);
                selectMenuClient(client);
                check = true;
            }
            if (check == false) {
                System.out.println("Tài khoản hoặc mật khẩu sai. Vui lòng đăng nhập lại hoặc bấm 0 để thoát !!");
            }
        }
    }
    public static void menuClient() {
        System.out.println("  ╔═════════════════════════════════════════════╗");
        System.out.println("  ║               ► MENU CLIENT ◄               ║");
        System.out.println("  ╟─────────────────────────────────────────────╢");
        System.out.println("  ║                                             ║");
        System.out.println("  ║      1. Chơi trò chơi                       ║");
        System.out.println("  ║      2. Kiểm tra số dư tài khoản            ║");
        System.out.println("  ║      3. Nhắn tin cho nhân viên              ║");
        System.out.println("  ║                                             ║");
        System.out.println("  ║            Nhấn '0' để thoát                ║");
        System.out.println("  ╚═════════════════════════════════════════════╝");
    }
    public static void selectMenuClient(Client client) {
        do {
            try {
                menuClient();
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        GameTaiXiu.playGame();
                        break;
                    case 2:
                        System.out.println("Tài khoản ban còn "+client.getAmount());
                        break;
                    case 3:
                        ClientDemo.start();
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
        } while (client.getAmount()>0);
    }
    public static void downTime(Client client){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (client.getAmount()>0){
                    try {
                        Thread.sleep(360000);
                        client.setAmount(client.getAmount()-1000);
                        System.out.println(client.getAmount());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
}
