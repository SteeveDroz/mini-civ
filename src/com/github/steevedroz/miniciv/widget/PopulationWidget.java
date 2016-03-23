package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.ProgressButton;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class PopulationWidget extends MiniCivWidget {
    private int inhabitants;

    private Label populationLabel;
    private ProgressButton invite;

    public PopulationWidget(MiniCiv parent) {
	super(parent);
	initializeComponents();

	inhabitants = 0;
    }

    @Override
    public void update() {
	populationLabel.setText(inhabitants + "/" + maxInhabitants());
	invite.setDisable(inhabitants >= maxInhabitants());
	increasePopulation(invite.getOverflow());
    }

    private void initializeComponents() {
	Label title = new Label("Population");
	title.setAlignment(Pos.CENTER);
	title.getStyleClass().add("title");
	add(title, 0, 0);
	setHalignment(title, HPos.CENTER);

	populationLabel = new Label("0/0");
	populationLabel.setTooltip(new Tooltip("3 * maison"));
	add(populationLabel, 0, 1);

	invite = new ProgressButton("Inviter");
	add(invite, 0, 2);
    }

    private void increasePopulation(int amount) {
	if (inhabitants + amount <= maxInhabitants()) {
	    inhabitants += amount;
	}
    }

    public int maxInhabitants() {
	int maxInhabitants = 0;
	maxInhabitants += parent.getBuilding().getPlace(); // Places in houses.
	return maxInhabitants;
    }

    public int getInhabitants() {
	return inhabitants;
    }

    public int kill(double percentage) {
	if (inhabitants == 0) {
	    return 0;
	}
	int loss = (int) (inhabitants * percentage + 1);
	inhabitants -= loss;
	return loss;
    }

    public void kill(int amount) {
	inhabitants = Math.min(0, inhabitants - amount);
    }
}
