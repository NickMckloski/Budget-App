package com.app.main;

import com.app.fxml.Controller;
import com.app.sql.Sql;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Nick Mckloski.
 */
public class Action {

    /**
     * Adds an expense to the expense table
     */
    public static void addExpense() {

        //get fields
        String name = Main.controller.nameField.getText();
        String vendor = Main.controller.vendorField.getText();
        String date = Main.controller.dateField.getText();
        double cost;

        Main.controller.status.setText("Adding expense \""+name+"\".");

        //check if cost is a number
        try {
            cost = Double.parseDouble(Main.controller.costField.getText());
        } catch(NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cost not valid.");
            alert.setHeaderText(null);
            alert.setContentText("Cost field can only contain numbers.");
            alert.showAndWait();
            Main.controller.status.setText("Cost may only contain numbers.");
            return;
        }

        //check for empty fields
        if(name.length() == 0 || date.length() == 0 || vendor.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty field.");
            alert.setHeaderText(null);
            alert.setContentText("Fields cannot be empty.");
            alert.showAndWait();
            Main.controller.status.setText("Error adding expense \"" + name + "\".");
            return;
        }

        //insert new expense, throws existing expense error
        try {
            Connection connection = Sql.getConnection();
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO expenses VALUES ('" + name + "', '"+vendor+"', '" + date + "', '" + cost + "')");

            stmt.close();
            connection.close();
        } catch(SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty field.");
            alert.setHeaderText(null);
            alert.setContentText("Expense \"" + name + "\" already exists!");
            alert.showAndWait();
            //e.printStackTrace();
            Main.controller.status.setText("Error adding expense \"" + name + "\".");
            return;
        }

        //update table and chart lists
        Main.controller.tableList.add(new Expense(name, vendor, date, cost));
        Main.controller.pieChartList.add(new PieChart.Data(name, cost));

        //set new table and chart data
        Main.controller.table.setItems(Main.controller.tableList);
        Main.controller.pieChart.setData(Main.controller.pieChartList);

        //update labels
        Controller.expenseTotal += cost;
        Main.controller.total.setText("$"+Controller.expenseTotal);

        if(Main.controller.tableList.size() > 0)
            Main.controller.emptyLabel.setText("");

        Main.controller.status.setText("Expense \""+name+"\" added.");
    }

    /**
     * Deletes and expense from the expense table
     */
    public static void deleteExpense() {
        Expense expense = Main.controller.table.getSelectionModel().getSelectedItem();
        if(expense == null)
            return;

        Main.controller.status.setText("Deleting expense \""+expense.getName()+"\".");

        try {
            Connection connection = Sql.getConnection();
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM expenses WHERE name='" + expense.getName() + "'");

            stmt.close();
            connection.close();
        } catch(Exception e) {
            Main.controller.status.setText("Error adding expense \"" + expense.getName() + "\".");
            e.printStackTrace();
            return;
        }


        Main.controller.tableList.remove(expense);
        for(int i = 0; i < Controller.pieChartList.size(); i++) {
            if(Controller.pieChartList.get(i).getName() == expense.getName())
                Controller.pieChartList.remove(i);
        }

        Main.controller.table.setItems(Main.controller.tableList);
        Main.controller.pieChart.setData(Main.controller.pieChartList);

        Controller.expenseTotal -= expense.getCost();
        Main.controller.total.setText("$" + Controller.expenseTotal);

        if(Main.controller.tableList.size() == 0)
            Main.controller.emptyLabel.setText("No data to display.");

        Main.controller.status.setText("Expense \""+expense.getName()+"\" deleted.");
    }

}
