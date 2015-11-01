package com.app.fxml;

import com.app.main.Action;
import com.app.main.Expense;
import com.app.sql.Sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Nick Mckloski.
 */
public class Controller implements Initializable {

    @FXML public PieChart pieChart;
    @FXML public Label emptyLabel;

    @FXML public TextField nameField;;
    @FXML public TextField vendorField;
    @FXML public TextField dateField;
    @FXML public TextField costField;

    public static double expenseTotal;
    @FXML public Label total;
    @FXML Button add;
    @FXML Button delete;

    @FXML public TableView<Expense> table;
    @FXML TableColumn name;
    @FXML TableColumn vendor;
    @FXML TableColumn date;
    @FXML TableColumn cost;

    @FXML public Label status;

    public static ObservableList<Expense> tableList = FXCollections.observableArrayList();
    public static ObservableList<PieChart.Data> pieChartList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Sql.loadSql();

        total.setText("$" + expenseTotal);

        name.setCellValueFactory(new PropertyValueFactory<Expense, String>("name"));
        name.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        vendor.setCellValueFactory(new PropertyValueFactory<Expense, String>("vendor"));
        vendor.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        date.setCellValueFactory(new PropertyValueFactory<Expense, String>("date"));
        date.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        cost.setCellValueFactory(new PropertyValueFactory<Expense, Double>("cost"));
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {

            @Override
            public String toString(Double object) {
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }

        }));

        add.setOnAction(e -> Action.addExpense());
        delete.setOnAction(e -> Action.deleteExpense());

        if(tableList.size() > 0)
            emptyLabel.setText("");

        table.setItems(tableList);
        pieChart.setData(pieChartList);

    }

}
