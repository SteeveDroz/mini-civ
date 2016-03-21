package com.github.steevedroz.miniciv.component;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ProgressButton extends GridPane {
    private static final double DEFAULT_FILLING_SPEED = 0.001;

    private boolean running;
    private double fillingSpeed;

    private ProgressBar progress;
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
	this.running = false;
	this.fillingSpeed = fillingSpeed;

	progress = new ProgressBar();
	progress.setProgress(0.0);
	add(progress, 0, 0);

	button = new Button();
	if (!automatic) {
	    add(button, 0, 1);

	    button.addEventHandler(MouseEvent.MOUSE_PRESSED, new Activator(true));
	    button.addEventHandler(MouseEvent.MOUSE_RELEASED, new Activator(false));
	    button.addEventHandler(MouseEvent.MOUSE_EXITED, new Activator(false));
	    this.button.setText(text);
	}
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

    private class Activator implements EventHandler<MouseEvent> {
	private boolean running;

	public Activator(boolean running) {
	    this.running = running;
	}

	@Override
	public void handle(MouseEvent event) {
	    ProgressButton.this.running = running;
	}
    }
}
