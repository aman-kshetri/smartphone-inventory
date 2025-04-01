package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.StoreController;
import model.SmartphoneInventoryModel;

class StoreApp {
    private static ArrayList<SmartphoneInventoryModel> smartphoneInventory = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\nStore Operations:");
            System.out.println("1. Add New Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Update Product");
            System.out.println("4. Get Smartphone Information");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    StoreController.addNewProduct(smartphoneInventory);
                    break;
                case 2:
        StoreController.deleteProduct(smartphoneInventory);
                    break;
                case 3:
                    StoreController.updateProduct(smartphoneInventory);
                    break;
                case 4:
                    StoreController.getSmartphoneInfo(smartphoneInventory);
                    break;
                case 5:
                    System.out.println("Exiting the program. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }

        } while (choice != 5);
    }
}
