package com.github.steevedroz.miniciv.component;

import java.io.IOException;

import com.github.steevedroz.miniciv.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Worker extends HBox {
    private String name;
    private int workers;
    private boolean automatic;

    @FXML
    Label nameLabel;
    @FXML
    Label workersLabel;
    @FXML
    Button hireButton;
    @FXML
    Button fireButton;

    public Worker(String name) {
	this(name, false);
    }

    public Worker(String name, boolean automatic) {
	this.name = name;
	this.workers = 0;
	this.automatic = automatic;

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.PACKAGE + "/fxml/Worker.fxml"));
	    loader.setRoot(this);
	    loader.setController(this);
	    loader.load();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    public void initialize() {
	nameLabel.setText(name);
	workersLabel.setText("0");
	hireButton.setVisible(!automatic);
	fireButton.setVisible(!automatic);
    }

    @FXML
    private void hire(ActionEvent event) {
	workers++;
    }

    @FXML
    private void fire(ActionEvent event) {
	workers = Math.max(workers - 1, 0);
    }

    public void update(int free) {
	workersLabel.setText("" + workers);
	hireButton.setDisable(free <= 0);
	fireButton.setDisable(workers <= 0);
    }

    public void empty() {
	workers = 0;
    }

    public int getWorkers() {
	return workers;
    }

    public void setWorkers(int workers) {
	this.workers = workers;
    }
}
