package com.github.steevedroz.miniciv;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    MiniCiv miniCiv = new MiniCiv(primaryStage);
	    miniCiv.getStylesheets().add(getClass().getResource("miniCiv.css").toExternalForm());
	    primaryStage.setScene(miniCiv);
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		@Override
		public void handle(WindowEvent event) {
		    System.exit(0);
		}
	    });
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}
