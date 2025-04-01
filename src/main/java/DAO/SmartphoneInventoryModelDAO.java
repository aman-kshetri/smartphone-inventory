package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.databaseConnection;
import model.SmartphoneInventoryModel;

public class SmartphoneInventoryModelDAO {
	private Connection con;

	
	
	public SmartphoneInventoryModelDAO() throws ClassNotFoundException, SQLException {
		this.con = databaseConnection.getConnection();
	}

	public boolean addSmartPhoneModel(SmartphoneInventoryModel smartphone) {
		boolean rowInserted = false;

		if (con == null) {
			System.out.println("Database not connected.");
			return false;
		}

		String query = "INSERT INTO smartphone (model_code, name, display_size, price, memory, ram) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, smartphone.getModelCode());
			ps.setString(2, smartphone.getName()); // Ensure your model has a getName() method
			ps.setDouble(3, smartphone.getDisplaySize());
			ps.setDouble(4, smartphone.getPrice());
			ps.setInt(5, smartphone.getMemory());
			ps.setInt(6, smartphone.getRam());

			int rowsInserted = ps.executeUpdate();
			System.out.println("Rows inserted = " + rowsInserted);

			if (rowsInserted > 0) {
				rowInserted = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowInserted;
	}
	
	
	
	
	
	public boolean deleteSmartphoneModel(String modelCode) {
	    boolean rowDeleted = false;

	    if (con == null) {
	        System.out.println("Database not connected.");
	        return false;
	    }

	    String query = "DELETE FROM smartphone WHERE model_code = ?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, modelCode);
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            rowDeleted = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rowDeleted;
	}

	
	
	
	
	public boolean updateSmartphoneModel(SmartphoneInventoryModel smartphone) {
	    boolean rowUpdated = false;

	    if (con == null) {
	        System.out.println("Database Not Connected...");
	        return false;
	    }

	    String query = "UPDATE smartphone SET name=?, display_size=?, price=?, memory=?, ram=? WHERE model_code=?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, smartphone.getName());
	        ps.setDouble(2, smartphone.getDisplaySize());
	        ps.setDouble(3, smartphone.getPrice());
	        ps.setInt(4, smartphone.getMemory());
	        ps.setInt(5, smartphone.getRam());
	        ps.setString(6, smartphone.getModelCode());

	        int rowsAffected = ps.executeUpdate();
	        rowUpdated = rowsAffected > 0; // Returns true if at least one row is updated

	        if (rowUpdated) {
	            System.out.println("Smartphone with model code " + smartphone.getModelCode() + " updated successfully!");
	        } else {
	            System.out.println("Smartphone with model code " + smartphone.getModelCode() + " not found.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); // Log the error
	    }

	    return rowUpdated;
	}

	
	
	public SmartphoneInventoryModel getSmartphoneModelByCode(String modelCode) {
	    if (con == null) {
	        System.out.println("Database not connected.");
	        return null;
	    }

	    String query = "SELECT * FROM smartphone WHERE model_code = ?";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, modelCode);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return new SmartphoneInventoryModel(
	                rs.getString("model_code"),
	                rs.getString("name"),
	                rs.getDouble("display_size"),
	                rs.getDouble("price"),
	                rs.getInt("memory"),
	                rs.getInt("ram")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	
	

	public ArrayList<SmartphoneInventoryModel> getAllSmartPhoneModels() throws SQLException {
		ArrayList<SmartphoneInventoryModel> allSmartPhoneModelList = new ArrayList<SmartphoneInventoryModel>();
		if (con == null) {
			System.out.println("Database Not Connected...");
			return null;
		}
		String query = "Select * from smartphone";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			SmartphoneInventoryModel smartphone = new SmartphoneInventoryModel(rs.getString("model_code"),
					rs.getString("name"), rs.getDouble("display_size"), rs.getDouble("price"), rs.getInt("memory"),
					rs.getInt("ram"));
			allSmartPhoneModelList.add(smartphone);
		}
		return allSmartPhoneModelList;
	}
}
