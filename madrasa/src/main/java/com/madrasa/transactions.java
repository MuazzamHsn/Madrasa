package com.madrasa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class transactions {

    public static int insuffblnc, addamnt;

    // Method to add an amount to the balance
    public static void addAmount(double amount, String month) {
        insuffblnc = 0;
        addamnt = 0;

        try {
            DBcon con = new DBcon();

            // Get the current balance
            String query = "SELECT current_balance FROM funds ORDER BY trans_id DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            double currentBalance = 0.0;
            if (rs.next()) {
                currentBalance = rs.getDouble("current_balance");
            }

            double newBalance = currentBalance + amount;

            // Insert new balance into funds table
            query = "INSERT INTO funds (month, amount, current_balance, operation_type) VALUES (?, ?, ?, 'add')";
            ps = con.prepareStatement(query);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);

            // Update finance history
            String historyUpdate = "UPDATE finance_history " +
                                   "SET fund_left = ? " +
                                   "WHERE id = (SELECT * FROM (SELECT MAX(id) FROM finance_history) AS subquery)";
            PreparedStatement historyPs = con.prepareStatement(historyUpdate);
            historyPs.setDouble(1, currentBalance);

            int res = ps.executeUpdate();
            int historyRes = historyPs.executeUpdate();

            // Insert new record in finance history
            if (res > 0 && historyRes > 0) {
                query = "INSERT INTO finance_history (month, fund_added, fund_left) VALUES (?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, month);
                ps.setDouble(2, amount);
                ps.setDouble(3, newBalance);
                ps.executeUpdate();

                System.out.println("Amount added successfully. New Balance: " + newBalance);
                addamnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to deduct an amount from the balance, allowing for negative balance
    public static void deductAmount(double amount, String month) {
        try {
            DBcon con = new DBcon();

            // Get the current balance
            String query = "SELECT current_balance FROM funds ORDER BY trans_id DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            double currentBalance = 0.0;
            if (rs.next()) {
                currentBalance = rs.getDouble("current_balance");
            }

            if(currentBalance<amount){
                insuffblnc++;
            }

            // Calculate new balance after deduction (can go negative)
            double newBalance = currentBalance - amount;

            // Update finance history before deduction
            String historyUpdate = "Insert into finance_history (month,fund_spent, fund_left) values (?,?,?)";
            PreparedStatement historyPs = con.prepareStatement(historyUpdate);
            historyPs.setString(1, month);
            historyPs.setDouble(2, amount);
            historyPs.setDouble(3, newBalance);
            int historyRes = historyPs.executeUpdate();

            // Insert new balance into funds table
            query = "INSERT INTO funds (month, amount, current_balance, operation_type) VALUES (?, ?, ?, 'deduct')";
            ps = con.prepareStatement(query);
            ps.setString(1, month);
            ps.setDouble(2, amount);
            ps.setDouble(3, newBalance);

            int res = ps.executeUpdate();

            
            
                // query = "INSERT INTO finance_history (month, fund_left) VALUES (?, ?)";
                // ps = con.prepareStatement(query);
                // ps.setString(1, month);
                // ps.setDouble(2, newBalance);
                // ps.executeUpdate();

                System.out.println("Amount deducted successfully. New Balance: " + newBalance);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
