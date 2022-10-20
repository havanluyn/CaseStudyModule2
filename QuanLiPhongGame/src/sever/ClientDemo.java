package sever;

import services.OrderItemsManager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientDemo {

    private Socket socket;
    private String nameClient;
    private OrderItemsManager orderItemsManager;
    private BufferedReader is;
    private BufferedWriter os;

    public ClientDemo(Socket socket, String nameClient) {
        this.socket = socket;
        this.nameClient = nameClient;
        this.orderItemsManager = new OrderItemsManager();
        try {
            this.os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderItemsManager getOrderItemsManager() {
        return orderItemsManager;
    }

    public void setOrderItemsManager(OrderItemsManager orderItemsManager) {
        this.orderItemsManager = orderItemsManager;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public void listenMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String line = null;
                try {
                    while ((line = is.readLine()) != null) {
                        String line1 = null;
                        String line2 = null;
                        if (line.contains("#")) {
                            String[] w = line.split("#");
                            line1 = w[0];
                            line2 = w[1];
                            if (line1.trim().equals(nameClient)) {
                                String[] lines = line2.split(",");
                                for (String str : lines) {
                                    System.out.println(str);
                                }
                                if (line2.equalsIgnoreCase("bye")) {
                                    System.out.println("Máy chủ đã tắt");
                                    socket.close();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                }
            }
        }).start();
    }

    public void sendMessage() {
        Scanner sc = new Scanner(System.in);
        try {
            os.write(nameClient);
            os.newLine();
            os.flush();

            while (true) {
                String line = sc.nextLine();
                os.write(line);
                os.newLine();
                os.flush();
                if (line.equalsIgnoreCase("bye")) {
                    socket.close();
                    break;
                }
            }
            os.close();
            is.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);

        }
    }

    public static void start() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Nhap clientname: ");
                String username = scanner.nextLine();
                final String serverHost = "localhost";
                Socket socket = null;
                try {
                    socket = new Socket(serverHost, 7777);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientDemo clientDemo = new ClientDemo(socket, username);
                clientDemo.listenMessage();
                clientDemo.sendMessage();
            } catch (Exception e) {
                System.out.println("Mất kết nối máy chủ");
            }
    }

//    public static void main(String[] args) {
//
//        while (true)
//            try {
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("Nhap clientname: ");
//                String username = scanner.nextLine();
//                final String serverHost = "localhost";
//                Socket socket = null;
//                try {
//                    socket = new Socket(serverHost, 7777);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                ClientDemo clientDemo = new ClientDemo(socket, username);
//                clientDemo.listenMessage();
//                clientDemo.sendMessage();
//            } catch (Exception e) {
//                System.out.println("Mất kết nối máy chủ");
//            }
//    }
}
