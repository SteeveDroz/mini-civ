package com.github.steevedroz.miniciv.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.Research;
import com.github.steevedroz.miniciv.component.Type;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class LaboratoryWidget extends MiniCivWidget {
    private double researchPoints;

    private Label researchLabel;

    private List<Research> researches;
    private Map<Type, Double> upgrades;

    public LaboratoryWidget(MiniCiv parent) {
	super(parent);
	initializeComponents();
	upgrades = new HashMap<Type, Double>();
    }

    @Override
    public void update() {
	researchPoints += parent.getWork().getInventors() * getUpgrade(Type.RESEARCH) * 0.0001;
	researchLabel.setText("" + Math.round(researchPoints * 100) / 100.0);
	for (Research research : researches) {
	    research.update(researchPoints);
	}
    }

    public void forgetAllResearches() {
	for (Research research : researches) {
	    research.forget();
	}
	if (parent.getPopulation().getInhabitants() == 0) {
	    researchPoints = 0;
	}
    }

    public double getResearchPoints() {
	return researchPoints;
    }

    private void consumeResearchPoints(double amount) {
	researchPoints -= amount;
    }

    private void initializeComponents() {
	Label title = new Label("Laboratoire");
	title.getStyleClass().add("title");
	add(title, 0, 0, 2, 1);

	add(new Label("Recherche :"), 0, 1);

	researchLabel = new Label("0");
	add(researchLabel, 1, 1);

	researches = new ArrayList<Research>();

	for (int i = 1; i < 100; i++) {
	    researches.add(new Research("Meilleures haches", i * i * 10, Type.WOOD, 1.1, this));
	}
	for (int i = 1; i < 100; i++) {
	    researches.add(new Research("Meilleures pioches", i * i * 15, Type.STONE, 1.1, this));
	}
	for (int i = 1; i < 100; i++) {
	    researches.add(new Research("Défenses", i * i * 6, Type.INVASION, 1.1, this));
	}
	for (int i = 1; i < 100; i++) {
	    researches.add(new Research("Meilleure technologie", i * i * 7, Type.RESEARCH, 1.1, this));
	}

	List<Research> researchesList = new ArrayList<Research>();
	researchesList.addAll(researches);

	Collections.sort(researchesList, new Comparator<Research>() {
	    @Override
	    public int compare(Research research1, Research research2) {
		return (int) (research1.getPrice() - research2.getPrice());
	    }
	});

	FlowPane flow = new FlowPane(researchesList.toArray(new Research[0]));

	ScrollPane scroll = new ScrollPane();
	scroll.setMaxHeight(300);
	scroll.setPrefHeight(300);
	scroll.setContent(flow);
	add(scroll, 0, 2, 2, 1);
    }

    public void discover(Research research) {
	consumeResearchPoints(research.getPrice());
	upgrades.put(research.getType(), getUpgrade(research.getType()) * research.getUpgrade());
    }

    public Double getUpgrade(Type type) {
	if (upgrades.containsKey(type)) {
	    return upgrades.get(type);
	}
	return 1.0;
    }
}
