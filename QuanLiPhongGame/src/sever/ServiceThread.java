package sever;

import model.OrderItems;
import model.Product;
import services.EmployeeManager;
import services.OrderItemsManager;
import services.ProductManager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceThread extends Thread {
    public static ArrayList<ClientDemo> clientDemoArrayList = new ArrayList<>();
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private Socket socketOfServer;
    private String clientName;

    public ServiceThread(Socket socket) {
        this.socketOfServer = socket;
        try {
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            clientName = is.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientDemo clientDemo = new ClientDemo(socketOfServer, clientName);
        ServiceThread.clientDemoArrayList.add(clientDemo);
        System.out.println("New connection with client " + ++this.clientNumber + " at " + socketOfServer);
    }

    @Override
    public void run() {
        try {
            while (socketOfServer.isConnected()) {
                String line = is.readLine();
                switch (line.toLowerCase()) {
                    case "/order":
                        getOrderClient(clientName);
                        break;
                    case "/menu":
                        getMenuClient(clientName);
                        break;
                    case "bye":
                        System.out.println(clientName + " da thoat");
                        break;
                    default:
                        getOrderItem(clientName,line);
                        System.out.println(clientName + ": " + line);
                        break;
                }
            }
            is.close();
        } catch (IOException e) {
            System.out.println(clientName + " da thoat");
            for (ClientDemo client:clientDemoArrayList) {
                if(client.getNameClient().equals(clientName)){
                    clientDemoArrayList.remove(client);
                    break;
                }
            }
            this.clientNumber--;
        }

    }

    //    public static void sendMessage() {
//        for (ClientDemo clientDemo : clientDemoArrayList) {
//            System.out.println(clientDemo.getSocket() + "" + clientDemo.getSocket().isConnected());
//            if (clientDemo.getSocket().isConnected()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Scanner sc = new Scanner(System.in);
//                        try {
//                            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(clientDemo.getSocket().getOutputStream()));
//                            String line = sc.nextLine();
//                            os.write(line);
//                            os.newLine();
//                            os.flush();
//                            if(line.equalsIgnoreCase(clientDemo.getNameClient()+"#/order")){
//                                String newline=clientDemo.getNameClient()+"#"+clientDemo.getOrderItemsManager().getOrderItemsList();
//                                os.write(line);
//                                os.newLine();
//                                os.flush();
//                            }
//                            if (line.equalsIgnoreCase("bye")) {
//                                clientDemo.getSocket().close();
//                            }
//                            os.close();
//                        } catch (UnknownHostException e) {
//                            System.err.println("Trying to connect to unknown host: " + e);
//                        } catch (IOException e) {
//                            System.err.println("IOException:  " + e);
//                        }
//                    }
//                }).start();
//            }
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Scanner sc = new Scanner(System.in);
//                try {
//                    while (true) {
//                        String line = sc.nextLine();
//                        os.write(line);
//                        os.newLine();
//                        os.flush();
//                        if (line.equalsIgnoreCase("bye")) {
//                            break;
//                        }
//                    }
//                    os.close();
//                    is.close();
//                } catch (UnknownHostException e) {
//                    System.err.println("Trying to connect to unknown host: " + e);
//                } catch (IOException e) {
//                    System.err.println("IOException:  " + e);
//                }
//            }
//        }).start();
//    }
    public static void getOrderClient(String clientName) {
        for (ClientDemo client : clientDemoArrayList) {
            if (client.getNameClient().equals(clientName)) {
                String newline = client.getNameClient() + "#" + client.getOrderItemsManager().getOrderItemsList() ;
                BufferedWriter os = null;
                try {
                    os = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
                    os.write(newline);
                    os.newLine();
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static void getMenuClient(String clientName) {
        for (ClientDemo client : clientDemoArrayList) {
            if (client.getNameClient().equals(clientName)) {
                String newline = client.getNameClient() + "#" + Menu();
                BufferedWriter os = null;
                try {
                    os = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
                    os.write(newline);
                    os.newLine();
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static void getOrderItem(String clientName,String nameProduct) {
        Product product=ProductManager.findProduct(nameProduct);
        OrderItems orderItems = null;
        if(product!=null){
           orderItems= OrderItemsManager.createOrderItems(nameProduct,1);
            OrderItemsManager orderItemsManager =new OrderItemsManager((int) (System.currentTimeMillis() % 100000),clientName, Instant.now());
            orderItemsManager.getOrderItemsList().add(orderItems);
            EmployeeManager.orderItemsManagerList.add(orderItemsManager);
            for (ClientDemo client : clientDemoArrayList) {
                if (client.getNameClient().equals(clientName)) {
                    client.getOrderItemsManager().getOrderItemsList().add(orderItems);
                    String newline = client.getNameClient() + "#" + "Bạn đã order thành công 1"+nameProduct;
                    BufferedWriter os = null;
                    try {
                        os = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
                        os.write(newline);
                        os.newLine();
                        os.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }

    }

    public static String Menu() {
        String str = "Menu,";
        for (Product product : ProductManager.products) {
            str = str + product.getName() + "   " + product.getPrice() + ",";
        }
        return str;
    }
}