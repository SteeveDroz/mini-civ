package com.github.steevedroz.miniciv.component;

import java.io.IOException;

import com.github.steevedroz.miniciv.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ProgressButton extends VBox {
    private static final double DEFAULT_FILLING_SPEED = 0.001;

    private boolean running;
    private double fillingSpeed;
    private boolean automatic;
    private String text;

    @FXML
    private ProgressBar progress;
    @FXML
    private Button button;

    public ProgressButton(String text) {
	this(text, DEFAULT_FILLING_SPEED);
    }

    public ProgressButton(boolean automatic) {
	this("", DEFAULT_FILLING_SPEED, automatic);
    }

    public ProgressButton(String text, double fillingSpeed) {
	this(text, fillingSpeed, false);
    }

    public ProgressButton(double fillingSpeed, boolean automatic) {
	this("", fillingSpeed, automatic);
    }

    public ProgressButton(String text, double fillingSpeed, boolean automatic) {
	super();

	this.running = false;
	this.fillingSpeed = fillingSpeed;
	this.automatic = automatic;
	this.text = text;

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.PACKAGE + "/fxml/ProgressButton.fxml"));
	    loader.setRoot(this);
	    loader.setController(this);
	    loader.load();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    public void initialize() {
	button.setText(text);
	button.setVisible(!automatic);
    }

    @FXML
    private void activate(MouseEvent event) {
	this.running = true;
    }

    @FXML
    private void deactivate(MouseEvent event) {
	this.running = false;
    }

    public int getOverflow() {
	if (running) {
	    progress.setProgress(progress.getProgress() + fillingSpeed);
	}
	double value = progress.getProgress();
	progress.setProgress(value - (int) value);
	return (int) value;
    }

    public double getProgress() {
	return progress.getProgress();
    }

    public void addProgress(double amount) {
	progress.setProgress(progress.getProgress() + amount * fillingSpeed);
    }

    public void setText(String text) {
	button.setText(text);
    }

    public void setTooltip(Tooltip tooltip) {
	progress.setTooltip(tooltip);
	button.setTooltip(tooltip);
    }
}
