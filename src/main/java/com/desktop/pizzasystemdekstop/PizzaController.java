package com.desktop.pizzasystemdekstop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaController implements Initializable {

    @FXML
    private TableView<PizzaModel> productTableView;

    @FXML
    private TableColumn<PizzaModel, Integer> productTableIdColumn;

    @FXML
    private TableColumn<PizzaModel, String> productTableNameColumn;

    @FXML
    private TableColumn<PizzaModel, String> productTableDoughColumn;


    @FXML
    private TableColumn<PizzaModel, String> productTableSauceColumn;

    @FXML
    private TableColumn<PizzaModel, String> productTablePriceColumn;
    @FXML
    private TextField keywordTextField;

    ObservableList<PizzaModel> pizzaModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String getOrdersViewQuery = "select id, name, dough, sauce, size, price from db.pizza;";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(getOrdersViewQuery);
            while (queryOutput.next()) {
                Integer queryID = queryOutput.getInt("id");
                String queryName = queryOutput.getString("name");
                String queryDough = queryOutput.getString("dough");
                String querySauce = queryOutput.getString("sauce");
                String querySize = queryOutput.getString("size");
                Integer queryPrice = queryOutput.getInt("price");


                pizzaModelObservableList.add(new PizzaModel(queryID, queryName, queryDough, querySauce, querySize, queryPrice));
            }
            productTableIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
            productTableNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
            productTableDoughColumn.setCellValueFactory(new PropertyValueFactory("dough"));
            productTableSauceColumn.setCellValueFactory(new PropertyValueFactory("sauce"));
            productTablePriceColumn.setCellValueFactory(new PropertyValueFactory("price"));

            productTableView.setItems(pizzaModelObservableList);

            FilteredList<PizzaModel> filteredList = new FilteredList<>(pizzaModelObservableList, b -> true);
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(pizzaModel -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(pizzaModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true; //found match
                    }else if(pizzaModel.getDough().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else{
                        return false;
                    }
                });
            });
            SortedList<PizzaModel> sortedList = new SortedList<>(filteredList);
            //bind sorted result with table view
            sortedList.comparatorProperty().bind(productTableView.comparatorProperty());

            productTableView.setItems(sortedList);

        } catch (SQLException e) {
            Logger.getLogger(PizzaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public void saveDataToFile(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        List data = new ArrayList();
        String getOrdersViewQuery = "select id, name, dough, sauce, size, price from db.pizza;";
        try{
            Statement sqlStatement = connectDB.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(getOrdersViewQuery);
            while(resultSet.next()){
                Integer queryID = resultSet.getInt("id");
                String queryName = resultSet.getString("name");
                String queryDough = resultSet.getString("dough");
                String querySauce = resultSet.getString("sauce");
                String querySize = resultSet.getString("size");
                Integer queryPrice = resultSet.getInt("price");
                data.add("id: " + queryID + " " + "name: " + queryName + " " + "dough: "
                        + queryDough + " " + "sauce: " + querySauce + " " + "size: " + querySize + " " + "price: " + queryPrice);
            }
            writeToFile(data, "C:\\Users\\dawid\\Desktop\\Orders.txt");

        }catch(SQLException e){
            System.out.println(e);
        }

    }

    private void writeToFile(List data, String path){
        BufferedWriter out = null;

        try {
            File file = new File(path);
            out = new BufferedWriter(new FileWriter(file, true));
            for (Object line : data) {
                out.write(line.toString());
                out.newLine();
            }
            out.close();
        }catch(IOException e){

        }
    }
}
