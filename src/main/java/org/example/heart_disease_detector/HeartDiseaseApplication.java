package org.example.heart_disease_detector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HeartDiseaseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        // Create Node and Scene
        FXMLLoader fxmlLoader = new FXMLLoader(HeartDiseaseApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set Stage attributes
        stage.setTitle("Heart Disease Detector");
        stage.setResizable(false);
        stage.centerOnScreen();

        // Set & Show Stage
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
