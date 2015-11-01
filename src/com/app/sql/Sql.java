package com.app.sql;

import com.app.fxml.Controller;
import com.app.main.Expense;
import com.app.main.Main;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Nick Mckloski.
 */
public class Sql {

    private static final String dbPath = "./db.sqlite";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            return conn;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadSql() {
        try {
            Connection connection = Sql.getConnection();
            String query = "SELECT * FROM expenses";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String name = rs.getString("name");
                String vendor = rs.getString("vendor");
                String date = rs.getString("date");
                double cost = rs.getInt("cost");
                Main.controller.tableList.add(new Expense(name, vendor, date, cost));
                Main.controller.pieChartList.add(new PieChart.Data(name, cost));
                Controller.expenseTotal += cost;
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
