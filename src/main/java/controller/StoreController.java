package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.SmartphoneInventoryModelDAO;
import model.SmartphoneInventoryModel;

public class StoreController {

	// Method to add new product
	public static void addNewProduct(ArrayList<SmartphoneInventoryModel> smartphoneInventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter model code:");
		String modelCode = scanner.nextLine();

		// Check if the model code already exists in ArrayList
		for (SmartphoneInventoryModel smartphone : smartphoneInventory) {
			if (smartphone.getModelCode().equals(modelCode)) {
				System.out.println("Product with model code " + modelCode + " already exists.");
				return;
			}
		}

		System.out.println("Enter name:");
		String name = scanner.nextLine();

		System.out.println("Enter display size:");
		double displaySize = scanner.nextDouble();

		System.out.println("Enter price:");
		double price = scanner.nextDouble();

		System.out.println("Enter memory:");
		int memory = scanner.nextInt();

		System.out.println("Enter RAM:");
		int ram = scanner.nextInt();

		// Create SmartphoneInventoryModel object
		SmartphoneInventoryModel smartphone = new SmartphoneInventoryModel(modelCode, name, displaySize, price, memory,
				ram);

		// Add to ArrayList
		smartphoneInventory.add(smartphone);

		// Save to database using DAO
		try {
			SmartphoneInventoryModelDAO dao = new SmartphoneInventoryModelDAO();
			boolean success = dao.addSmartPhoneModel(smartphone);
			if (success) {
				System.out.println("Smartphone added to the database successfully!");
			} else {
				System.out.println("Failed to add smartphone to the database.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to delete existing data from the ArrayList based on model code
	public static void deleteProduct(ArrayList<SmartphoneInventoryModel> smartphoneInventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter model code to delete:");
		String modelCode = scanner.nextLine();

		// Find the index of the smartphone with the provided model code
		boolean found = false;
		for (int i = 0; i < smartphoneInventory.size(); i++) {
			if (smartphoneInventory.get(i).getModelCode().equals(modelCode)) {
				smartphoneInventory.remove(i);
				found = true;
				break;
			}
		}

		// Remove the smartphone if found
		if (!found) {
			System.out.println("Smartphone with model code " + modelCode + " not found.");
			return;
		}

		// Remove from database
		try {
			SmartphoneInventoryModelDAO dao = new SmartphoneInventoryModelDAO();
			boolean success = dao.deleteSmartphoneModel(modelCode);

			if (success) {
				System.out.println("Smartphone deleted successfully from database.");
			} else {
				System.out.println("Failed to delete smartphone from database.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to update existing data based on the model code
	public static void updateProduct(ArrayList<SmartphoneInventoryModel> smartphoneInventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter model code to update:");
		String modelCode = scanner.nextLine();

		// Find the smartphone with the provided model code
		SmartphoneInventoryModel smartphoneToUpdate = null;
		for (SmartphoneInventoryModel smartphone : smartphoneInventory) {
			if (smartphone.getModelCode().equals(modelCode)) {
				smartphoneToUpdate = smartphone;
				break;
			}
		}

		// If the smartphone is not found
		if (smartphoneToUpdate == null) {
			System.out.println("Smartphone with model code " + modelCode + " not found.");
			return;
		}

		// Get new details from the user
		System.out.println("Enter new name (or press Enter to keep unchanged):");
		String newName = scanner.nextLine();
		if (!newName.isEmpty())
			smartphoneToUpdate.setName(newName);

		System.out.println("Enter new display size (or -1 to keep unchanged):");
		double newDisplaySize = scanner.nextDouble();
		if (newDisplaySize != -1)
			smartphoneToUpdate.setDisplaySize(newDisplaySize);

		System.out.println("Enter new price (or -1 to keep unchanged):");
		double newPrice = scanner.nextDouble();
		if (newPrice != -1)
			smartphoneToUpdate.setPrice(newPrice);

		System.out.println("Enter new memory (or -1 to keep unchanged):");
		int newMemory = scanner.nextInt();
		if (newMemory != -1)
			smartphoneToUpdate.setMemory(newMemory);

		System.out.println("Enter new RAM (or -1 to keep unchanged):");
		int newRam = scanner.nextInt();
		if (newRam != -1)
			smartphoneToUpdate.setRam(newRam);

		// Call DAO to update the database
		SmartphoneInventoryModelDAO dao;
		try {
			dao = new SmartphoneInventoryModelDAO();
			boolean success = dao.updateSmartphoneModel(smartphoneToUpdate);

			if (success) {
				System.out
						.println("Smartphone with model code " + modelCode + " updated successfully in the database!");
			} else {
				System.out.println("Database update failed.");
			}
		} catch (Exception e) {
			System.out.println("Error updating database: " + ((Throwable) e).getMessage());
		}
	}

	// Method to get information about a specific smartphone
	public static void getSmartphoneInfo(ArrayList<SmartphoneInventoryModel> smartphoneInventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter model code to get smartphone information:");
		String modelCode = scanner.nextLine();

		// Search in the in-memory list
		for (SmartphoneInventoryModel smartphone : smartphoneInventory) {
			if (smartphone.getModelCode().equals(modelCode)) {
				displaySmartphoneDetails(smartphone);
				return;
			}
		}

		// If not found in memory, check in the database
		SmartphoneInventoryModelDAO dao;
		try {
			dao = new SmartphoneInventoryModelDAO();
			SmartphoneInventoryModel smartphoneFromDB = dao.getSmartphoneModelByCode(modelCode);

			if (smartphoneFromDB != null) {
				displaySmartphoneDetails(smartphoneFromDB);
			} else {
				System.out.println("Smartphone with model code " + modelCode + " not found.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error retrieving data from database: " + e.getMessage());
		}
	}

	// Utility method to display smartphone details
	private static void displaySmartphoneDetails(SmartphoneInventoryModel smartphone) {
		System.out.println("\nSmartphone Details:");
		System.out.println("Model Code: " + smartphone.getModelCode());
		System.out.println("Name: " + smartphone.getName());
		System.out.println("Display Size: " + smartphone.getDisplaySize() + " inches");
		System.out.println("Price: $" + smartphone.getPrice());
		System.out.println("Memory: " + smartphone.getMemory() + " GB");
		System.out.println("RAM: " + smartphone.getRam() + " GB");
	}
}