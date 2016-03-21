package com.github.steevedroz.miniciv.component;

import com.github.steevedroz.miniciv.widget.LaboratoryWidget;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Research extends Button {
    private double price;
    private Research.State state;

    private Type type;
    private double upgrade;

    public Research(String name, double price, Type type, double upgrade, LaboratoryWidget parent) {
	this.price = price;
	this.state = State.UNAVAILABLE;
	this.type = type;
	this.upgrade = upgrade;

	setText(name + "\n" + "+" + (Math.round(10000 * (upgrade - 1)) / 100.0) + "% " + type + "\n" + (int) price
		+ " points");
	addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
	    @Override
	    public void handle(Event event) {
		state = State.RESEARCHED;
		parent.discover(Research.this);
	    }
	});
    }

    public void update(double points) {
	state = state.nextState(points, price);
	setVisible(state.isVisible());
	setManaged(isVisible());
	setDisable(state.isDisabled());
    }

    public void forget() {
	this.state = State.UNAVAILABLE;
    }

    public double getPrice() {
	return price;
    }

    public Type getType() {
	return type;
    }

    public double getUpgrade() {
	return upgrade;
    }

    public enum State {
	UNAVAILABLE {
	    @Override
	    public State nextState(double points, double price) {
		return points > price / 2 ? UNAFFORDABLE : UNAVAILABLE;
	    }

	    @Override
	    public boolean isVisible() {
		return false;
	    }

	    @Override
	    public boolean isDisabled() {
		return true;
	    }
	},
	UNAFFORDABLE {
	    @Override
	    public State nextState(double points, double price) {
		return points > price ? AFFORDABLE : UNAFFORDABLE;
	    }

	    @Override
	    public boolean isVisible() {
		return true;
	    }

	    @Override
	    public boolean isDisabled() {
		return true;
	    }
	},
	AFFORDABLE {
	    @Override
	    public State nextState(double points, double price) {
		return points > price ? AFFORDABLE : UNAFFORDABLE;
	    }

	    @Override
	    public boolean isVisible() {
		return true;
	    }

	    @Override
	    public boolean isDisabled() {
		return false;
	    }
	},
	RESEARCHED {
	    @Override
	    public State nextState(double points, double price) {
		return RESEARCHED;
	    }

	    @Override
	    public boolean isVisible() {
		return false;
	    }

	    @Override
	    public boolean isDisabled() {
		return false;
	    }
	};

	public abstract State nextState(double points, double price);

	public abstract boolean isVisible();

	public abstract boolean isDisabled();
    }
}
