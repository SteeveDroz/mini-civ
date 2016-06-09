package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.Worker;

import javafx.geometry.HPos;
import javafx.scene.control.Label;

public class WorkWidget extends MiniCivWidget {
    private Worker woodcutters;
    private Worker miners;
    private Worker ambassadors;
    private Worker warriors;
    private Worker inventors;

    private Worker free;

    public WorkWidget(MiniCiv parent) {
	super(parent);
	initializeComponents();
    }

    private void initializeComponents() {
	Label title = new Label("Travail");
	title.getStyleClass().add("title");
	setHalignment(title, HPos.CENTER);
	add(title, 0, 0, 4, 1);

	woodcutters = new Worker("Bûcherons");
	add(woodcutters, 0, 1);

	miners = new Worker("Mineurs");
	add(miners, 0, 2);

	ambassadors = new Worker("Ambassadeurs");
	add(ambassadors, 0, 3);

	warriors = new Worker("Guerriers");
	add(warriors, 0, 4);

	inventors = new Worker("Inventeurs");
	add(inventors, 0, 5);

	free = new Worker("Libres", true);
	add(free, 0, 6);
    }

    @Override
    public void update() {
	woodcutters.update(getFree());
	miners.update(getFree());
	ambassadors.update(getFree());
	warriors.update(getFree());
	inventors.update(getFree());

	free.setWorkers(getFree());
	free.update(0);
    }

    public int totalWorkers() {
	int totalWorkers = 0;
	totalWorkers += woodcutters.getWorkers();
	totalWorkers += miners.getWorkers();
	totalWorkers += ambassadors.getWorkers();
	totalWorkers += warriors.getWorkers();
	totalWorkers += inventors.getWorkers();
	return totalWorkers;
    }

    public void emptyAll() {
	woodcutters.empty();
	miners.empty();
	ambassadors.empty();
	warriors.empty();
	inventors.empty();
    }

    public int getWoodcutters() {
	return woodcutters.getWorkers();
    }

    public int getMiners() {
	return miners.getWorkers();
    }

    public int getAmbassadors() {
	return ambassadors.getWorkers();
    }

    public int getWarriors() {
	return warriors.getWorkers();
    }

    public int getInventors() {
	return inventors.getWorkers();
    }

    public int getFree() {
	return parent.getPopulation().getInhabitants() - totalWorkers();
    }

    public void setWoodcutters(int woodcutters) {
	this.woodcutters.setWorkers(woodcutters);
    }

    public void setMiners(int miners) {
	this.miners.setWorkers(miners);
    }

    public void setAmbassadors(int ambassadors) {
	this.ambassadors.setWorkers(ambassadors);
    }

    public void setWarriors(int warriors) {
	this.warriors.setWorkers(warriors);
    }

    public void setInventors(int inventors) {
	this.inventors.setWorkers(inventors);
    }
}
