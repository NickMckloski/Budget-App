package com.app.main;

import com.app.fxml.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Nick Mckloski.
 */
public class Main extends Application {

    public static FXMLLoader fxmlLoader;
    public static Controller controller;

    ///Local Objects
    Stage window;
    Scene mainScene;

    /**
     * Main method of this project. Launches application.
     *
     * @param args - parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Builds the main application.
     *
     * @param stage - Stage to start
     */
    @Override
    public void start(Stage stage) {
        window = stage;

        AnchorPane root = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/com/app/fxml/gui.fxml"));
            root = (AnchorPane) fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //declare scene
        mainScene = new Scene(root);

        //manipulate window and display it
        window.setTitle("BudgetPal");
        window.setScene(mainScene);
        window.show();
    }

}