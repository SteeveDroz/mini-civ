package com.github.steevedroz.miniciv.component;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Worker extends GridPane {
    private int workers;
    private Label workersLabel;
    private Button add;
    private Button remove;

    public Worker(String name) {
	this(name, false);
    }

    public Worker(String name, boolean automatic) {
	this.workers = 0;

	add(new Label(name + " :"), 0, 0);

	this.workersLabel = new Label("0");
	add(workersLabel, 1, 0);

	add = new Button("Engager");
	remove = new Button("Licencier");

	if (!automatic) {
	    add.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
		@Override
		public void handle(Event event) {
		    workers++;
		}
	    });

	    add(add, 2, 0);

	    remove.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
		@Override
		public void handle(Event event) {
		    workers--;
		}
	    });
	    add(remove, 3, 0);
	}
    }

    public void update(int free) {
	workersLabel.setText("" + workers);
	add.setDisable(free <= 0);
	remove.setDisable(workers <= 0);
    }

    public void empty() {
	workers = 0;
    }

    public int getWorkers() {
	return workers;
    }

    public void setWorkers(int workers) {
	this.workers = workers;
	update(0);
    }
}
