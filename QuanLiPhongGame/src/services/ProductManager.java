package services;
import model.EnumProduct;
import model.Product;
import utils.FileUtils;

import java.util.List;
import java.util.Scanner;

public class ProductManager {
    private static final String path = "C:\\Users\\LeThiThuyTrang\\Desktop\\CaseStudyModule2\\QuanLiPhongGame\\data\\FileProducts.txt";
    private static final Scanner sc = new Scanner(System.in);
    public static List<Product> products = FileUtils.read(path);

    public static Product createProduct() {
        long iD = System.currentTimeMillis() % 1000000;
        String productName = inputNameProduct(EnumProduct.CREATE);
        int amount = inputAmountProduct();
        int price = inputPriceProduct();
        Product product = new Product(iD, productName, amount, price);
        return product;
    }

    public static void insertProduct(Product product) {
        products = FileUtils.read(path);
        products.add(product);
        FileUtils.write(path, products);
    }

    public static void addProduct() {
        do {
            Product product = ProductManager.createProduct();
            ProductManager.insertProduct(product);
            System.out.println(product + "đã được thêm");
        } while (isReTry(EnumProduct.CREATE));
    }

    public static void editProduct(long idProduct, Product editProduct) {
        List<Product> products = FileUtils.read(path);
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == idProduct) {
                products.get(i).setName(editProduct.getName());
                products.get(i).setPrice(editProduct.getPrice());
                products.get(i).setAmount(editProduct.getAmount());
            }
        }
        FileUtils.write(path, products);
    }

    public static void removeProduct() {
        List<Product> products = FileUtils.read(path);
        long iD = inputIDProduct();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == iD) {
                products.remove(i);
            }
        }
        FileUtils.write(path, products);
    }

    public static void editProduct() {
        showAllProduct();
        boolean check = false;
        do {
            try {
                System.out.println("⟹⟹⟹⟹ Sửa sản phẩm ⟸⟸⟸⟸");
                long idProduct = inputIDProduct();
                String name = inputNameProduct(EnumProduct.EDIT);
                int price = inputPriceProduct();
                int amount = inputAmountProduct();
                Product productEdited = new Product();
                productEdited.setId(idProduct);
                productEdited.setName(name);
                productEdited.setPrice(price);
                productEdited.setAmount(amount);
                ProductManager.editProduct(idProduct, productEdited);
                System.out.println("Sửa thành công");
                showAllProduct();
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại.");
            }

        } while (isReTry(EnumProduct.EDIT));
    }

    public static int findProductByID(long ID) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (ID == (products.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static Product findProduct(String name) {
        for (int i = 0; i < products.size(); i++) {
            if (name.equals(products.get(i).getName())) {
                return products.get(i);
            }
        }
        return null;
    }

    public static Product findProduct(long iD) {
        for (int i = 0; i < products.size(); i++) {
            if (iD == (products.get(i).getId())) {
                return products.get(i);
            }
        }
        return null;
    }

    public static void showProduct() {
        products = FileUtils.read(path);
        System.out.println("Nhập tên sản phẩm muốn kiểm tra");
        String nameProduct = sc.nextLine();
        for (int i = 0; i < products.size(); i++) {
            if ((products.get(i).getName()).contains(nameProduct)) {
                System.out.println(products.get(i));
            }
        }
        System.out.println("           ------------------------------------------ ");
    }

    public static void showAllProduct() {
//        List<Product> products = FileUtils.read(path);
        System.out.println("  ═══════════════════════════════════ Danh sách tài khoản ══════════════════════════════════");
        System.out.printf("   %-25s %-20s %-20s %-20s \n", "ID", "Tên sản phẩm", "Số lượng", "Giá");
        System.out.println("  ──────────────────────────────────────────────────────────────────────────────────────────");
        for (Product product : products) {
            System.out.printf("   %-25s %-20s %-20s %-20s \n",
                    product.getId(),
                    product.getName(),
                    product.getAmount(),
                    product.getPrice()
            );
        }
        System.out.println("  ═════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void setAmountbyId() {
//        products = FileUtils.read(path);
        do {
            long iD = inputIDProduct();
            int index = findProductByID(iD);
            if (index != -1) {
                System.out.println("Nhập số lượng sản phẩm muốn thay đổi");
                int amount = Integer.parseInt(sc.nextLine());
                products.get(index).setAmount(amount + products.get(index).getAmount());
                FileUtils.write(path, products);
                break;
            } else {
                System.out.println("Không tìm thấy sản phẩm !!");
            }
        } while (true);
    }

    public static long inputIDProduct() {
        System.out.println("Nhập ID sản phẩm cần sửa:");
        System.out.print("⟹");
        long idProduct = -1;
        boolean flag = true;
        do {
            try {
                idProduct = Long.parseLong(sc.nextLine());
                Product product = findProduct(idProduct);
                if (product == null) {
                    System.out.println("ID sản phẩm không tồn tại. Vui lòng nhập lại");
                    flag = false;
                } else {
                    flag = true;
                }

            } catch (NumberFormatException ex) {
                System.out.println("ID sản phẩm không đúng định dạng");
                flag = false;
            }
        } while (flag == false);
        return idProduct;
    }

    public static String inputNameProduct(EnumProduct eProduct) {
        switch (eProduct) {
            case CREATE: {
                System.out.println("Nhập tên sản phẩm:");
                System.out.print("⟹");
                break;
            }
            case EDIT: {
                System.out.println("Nhập tên sản phẩm muốn sửa:");
                System.out.print("⟹");
                break;
            }
        }
        String name;

        do {
            name = sc.nextLine().trim();
            if (name.equals("") || name.equals(null)) {
                System.out.println("Vui lòng nhập lại tên sản phẩm");
                System.out.print("⟹");
            }
        } while (name.equals("") || name.equals(null));
        return name;
    }

    private static int inputAmountProduct() {
        System.out.println("Nhập số lượng sản phẩm:");
        System.out.print("⟹");
        int amount = -1;
        boolean flag = true;
        do {
            try {
                amount = Integer.parseInt(sc.nextLine());
                if (amount < 0) {
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
        return amount;
    }

    private static int inputPriceProduct() {
        System.out.println("Nhập giá sản phẩm:");
        System.out.print("⟹");
        int price = 0;
        boolean flag = true;
        do {
            try {
                price = Integer.parseInt(sc.nextLine());
                if (!(price > 1000)) {
                    System.out.println("Giá tiền không được nhỏ hơn 1000");
                    System.out.print("⟹ ");
                    flag = false;
                } else {
                    flag = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Giá không hợp lệ vui lòng nhập lại");
                flag = false;
            }

        } while (flag == false);

        return price;
    }

    private static boolean isReTry(EnumProduct eProduct) {
        switch (eProduct) {
            case CREATE:
                System.out.println("Bạn có tiếp tuc thêm sản phẩm hay không");
                break;
            case EDIT:
                System.out.println("Bạn có muốn sửa sản phẩm hay không");
                break;
        }
        do {
            System.out.println("Nhập 'c' để tiếp tục, nhập 'e' để thoát");
            String action = sc.nextLine();
            if (action.equals("c")) {
                return true;
            } else {
                if (action.equals("e")) {
                    return false;
                }
            }
        } while (true);
    }
}
