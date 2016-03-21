package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.ProgressButton;
import com.github.steevedroz.miniciv.component.Type;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

public class WarWidget extends MiniCivWidget {
    private Label invasionEnvyLabel;

    private ProgressButton warMenace;

    private Label protectedLabel;

    public WarWidget(MiniCiv parent) {
	super(parent);
	initializeComponents();
    }

    @Override
    public void update() {
	invasionEnvyLabel.setText("" + (Math.round(100 * invasionEnvy()) / 100.0));
	warMenace.addProgress(invasionEnvy() / parent.getLaboratory().getUpgrade(Type.INVASION));
	if (warMenace.getOverflow() >= 1.0) {
	    attack();
	}
	protectedLabel.setText("" + parent.getPopulation().getInhabitants() + "/" + getProtected());
	protectedLabel.setTextFill(getProtected() < parent.getPopulation().getInhabitants() ? Color.RED : Color.BLACK);
    }

    public double invasionEnvy() {
	double invasionEnvy = 0;
	invasionEnvy += parent.getPopulation().getInhabitants() / 10.0;
	invasionEnvy += parent.getResources().getWood() / 100.0;
	invasionEnvy += parent.getResources().getStone() / 20.0;
	invasionEnvy += parent.getLaboratory().getResearchPoints() / 1000.0;
	invasionEnvy /= parent.getWork().getAmbassadors() + 1;
	return invasionEnvy;
    }

    public int getProtected() {
	return parent.getWork().getWarriors() * parent.getBuilding().getTowers();
    }

    private void initializeComponents() {
	Label title = new Label("Guerre");
	title.getStyleClass().add("title");
	setHalignment(title, HPos.CENTER);
	add(title, 0, 0, 2, 1);

	add(new Label("Envie d'invasion :"), 0, 1);

	invasionEnvyLabel = new Label("0");
	invasionEnvyLabel.setTooltip(new Tooltip(
		"(population / 10 + bois / 100 + pierres / 20 + points de recherche / 1000) / (ambassadeurs + 1)"));
	add(invasionEnvyLabel, 1, 1);

	add(new Label("Menace de guerre : "), 0, 2);

	warMenace = new ProgressButton(0.0001, true);
	warMenace.setTooltip(new Tooltip(
		"Si toute la population n'est pas protégée :\n\t- Perte de tous les emplois\n\t- Mort de la moitié de la population\n\t- Destruction d'une maison\n\t- Destruction d'une tour\n\t- Perte de 10% du bois et des pierres\n\t- Perte de toutes les recherches\n\nSi toute la population est protégée :\n\t- Perte de 10% du bois et des pierres"));
	add(warMenace, 1, 2);

	add(new Label("Capacité de population :"), 0, 3);

	protectedLabel = new Label("0/0");
	protectedLabel.setTooltip(new Tooltip("(guerriers * tours)"));
	add(protectedLabel, 1, 3);
    }

    private void attack() {
	if (getProtected() < parent.getPopulation().getInhabitants()) {
	    parent.getWork().emptyAll();
	    parent.getPopulation().kill(0.5);
	    parent.getBuilding().destroyHouses(1);
	    parent.getBuilding().destroyTowers(1);
	    parent.getLaboratory().forgetAllResearches();
	}
	parent.getResources().destroyAllResources(0.1);
    }
}
