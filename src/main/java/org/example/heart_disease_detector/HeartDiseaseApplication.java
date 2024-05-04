package org.example.heart_disease_detector;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HeartDiseaseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        // Create Node and Scene
        Group root = new Group();
        Scene scene = new Scene(root, Color.rgb(236,244,255));

        // Set Stage attributes
        stage.setTitle("Heart Disease Detector");
        stage.setWidth(1200);
        stage.setHeight(800);
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
/**
@Override
public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Heart Disease Detector");
    stage.setScene(scene);
    stage.show();
}
**/