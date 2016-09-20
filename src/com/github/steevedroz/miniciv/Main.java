package com.github.steevedroz.miniciv;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String VERSION = "v1.1";

    @Override
    public void start(Stage primaryStage) {
	try {
	    MiniCiv miniCiv = new MiniCiv(primaryStage);
	    miniCiv.getStylesheets().add(getClass().getResource("miniCiv.css").toExternalForm());
	    primaryStage.setScene(miniCiv);
	    primaryStage.setOnCloseRequest(event -> System.exit(0));

	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}
