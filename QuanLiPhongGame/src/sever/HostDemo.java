package sever;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;


public class HostDemo {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        System.out.println("Server is waiting to accept user...");

        Thread handShakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            Socket socket = serverSocket.accept();
                            ServiceThread serviceThread = new ServiceThread(socket);
                            Thread thread = new Thread(serviceThread);
                            thread.start();
                        } catch (IOException e) {
                            System.out.println("Sever đã đóng");
                        }
                    }
                } finally {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("Sever đã đóng");
                    }
                }
            }
        }
        );
        handShakeThread.start();
        while (true) {
            sendAllMessage();
        }
    }

    public static void sendAllMessage() {
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();
        for (ClientDemo clientDemo : ServiceThread.clientDemoArrayList) {
            if (clientDemo.getSocket().isConnected()) {
                Thread sendThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writeOutputStream(clientDemo, message);
                    }
                });
                sendThread.start();
            }
        }

    }
    public static void writeOutputStream(Socket socket, String data) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(data);
            bw.newLine();
            bw.flush();
            if (data.equalsIgnoreCase("bye")) {
                socket.close();
            }
        } catch (IOException e) {
        }

    }
    public static void writeOutputStream(ClientDemo clientDemo, String data) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientDemo.getSocket().getOutputStream()));
            bw.write(data);
            bw.newLine();
            bw.flush();
//            if(data.equalsIgnoreCase(clientDemo.getNameClient()+"#/order")){
//                Product product = ProductManager.findProduct("CocaCola");
//                (clientDemo.getOrderItemsManager().getOrderItemsList()).add(new OrderItems(product,2));
//                String newline=clientDemo.getNameClient()+"#"+clientDemo.getOrderItemsManager().getOrderItemsList();
//                bw.write(newline);
//                bw.newLine();
//                bw.flush();
//            }
            if (data.equalsIgnoreCase("bye")) {
                clientDemo.getSocket().close();
            }
        } catch (IOException e) {
        }

    }
}

