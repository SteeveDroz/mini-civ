package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class ChangeVillageWidget extends MiniCivWidget {
    private int previousInhabitants;

    private Button changeVillage;
    private Label previousInhabitantsDescriptionLabel;
    private Label previousInhabitantsLabel;
    private Label levelLabel;
    private Label level;

    public ChangeVillageWidget(MiniCiv parent) {
	super(parent);
	previousInhabitants = 0;
	changeVillage = new Button();
	changeVillage.setText("Changer de village");
	changeVillage.setTooltip(
		new Tooltip("Recommencer à nouveau avec un nouveau village et comptabiliser les villageois."));
	changeVillage.setOnMouseClicked((event) -> {
	    previousInhabitants += parent.getPopulation().getInhabitants();
	    previousInhabitantsLabel.setText("" + (previousInhabitants));
	    level.setText("" + getLevel());
	    parent.reinitAll();
	});
	add(changeVillage, 0, 0, 2, 1);

	previousInhabitantsDescriptionLabel = new Label();
	previousInhabitantsDescriptionLabel.setText("Habitants passés :");
	add(previousInhabitantsDescriptionLabel, 0, 1);

	previousInhabitantsLabel = new Label();
	previousInhabitantsLabel.setText("0");
	add(previousInhabitantsLabel, 1, 1);

	levelLabel = new Label();
	levelLabel.setText("Niveau :");
	add(levelLabel, 0, 2);

	level = new Label();
	level.setText("1");
	level.setTooltip(new Tooltip("1 par puissance de 10 des habitants passés + 1."));
	add(level, 1, 2);
    }

    @Override
    public void update() {
	// Nothing
    }

    @Override
    public void reinit() {
	// Nothing
    }

    public int getPreviousInhabitants() {
	return previousInhabitants;
    }

    public void setPreviousInhabitants(int previousInhabitants) {
	this.previousInhabitants = previousInhabitants;
	previousInhabitantsLabel.setText("" + previousInhabitants);
	level.setText("" + getLevel());
    }

    public int getLevel() {
	if (previousInhabitants == 0) {
	    return 1;
	}
	return (int) Math.log10(previousInhabitants) + 1;
    }
}
