package com.madrasa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class transactions {

    public static int insuffblnc, addamnt;

    // Method to add an amount to the balance
    public static void addAmount(double amount, String month) {
        insuffblnc = 0;
        addamnt = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DBcon conn = new DBcon(); // Assuming DBcon has a method to get a connection

            // Get the current balance
            String query = "SELECT current_balance FROM funds ORDER BY trans_id DESC LIMIT 1";
            ps = conn .prepareStatement(query);
            rs = ps.executeQuery();

            double currentBalance = 0.0;
            if (rs.next()) {
                currentBalance = rs.getDouble("current_balance");
            }

            double newBalance = currentBalance + amount;

            // Insert new balance into funds table
            query = "INSERT INTO funds (month, amount, current_balance, operation_type) VALUES (?, ?, ?, 'add')";
            ps = conn.prepareStatement(query);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);
            ps.executeUpdate();

            // Insert new record in finance history
            query = "INSERT INTO finance_history (month, fund_added, fund_left) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);
            ps.executeUpdate();

            System.out.println("Amount added successfully. New Balance: " + newBalance);
            addamnt++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to deduct an amount from the balance, allowing for negative balance
    public static void deductAmount(double amount, String month) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DBcon conn = new DBcon(); // Assuming DBcon has a method to get a connection

            // Get the current balance
            String query = "SELECT current_balance FROM funds ORDER BY trans_id DESC LIMIT 1";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            double currentBalance = 0.0;
            if (rs.next()) {
                currentBalance = rs.getDouble("current_balance");
            }

            if (currentBalance < amount) {
                insuffblnc++;
            }

            // Calculate new balance after deduction (can go negative)
            double newBalance = currentBalance - amount;

            // Update finance history before deduction
            String historyUpdate = "INSERT INTO finance_history (month, fund_spent, fund_left) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(historyUpdate);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);
            ps.executeUpdate();

            // Insert new balance into funds table
            query = "INSERT INTO funds (month, amount, current_balance, operation_type) VALUES (?, ?, ?, 'deduct')";
            ps = conn.prepareStatement(query);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);
            ps.executeUpdate();

            System.out.println("Amount deducted successfully. New Balance: " + newBalance);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
