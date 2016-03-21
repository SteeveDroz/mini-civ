package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.ProgressButton;

import javafx.geometry.HPos;
import javafx.scene.control.Label;

public class BuildingWidget extends MiniCivWidget {
    private int houses;
    private int towers;

    private Label housesLabel;
    private ProgressButton buildHouse;

    private Label towersLabel;
    private ProgressButton buildTower;

    public BuildingWidget(MiniCiv parent) {
	super(parent);

	houses = 0;
	towers = 0;

	initializeComponents();
    }

    @Override
    public void update() {
	housesLabel.setText("" + houses);
	buildHouse.setText("Construire (" + nextHousePrice() + " bois)");
	buildHouse.setDisable(!parent.getResources().hasWood(nextHousePrice()));
	if (buildHouse.getOverflow() > 0) {
	    buildHouse();
	}

	towersLabel.setText("" + towers);
	buildTower.setText("Construire (" + nextTowerPrice() + " pierres)");
	buildTower.setDisable(!parent.getResources().hasStone(nextTowerPrice()));
	if (buildTower.getOverflow() > 0) {
	    buildTower();
	}
    }

    public int nextHousePrice() {
	return (int) (Math.PI * houses * houses / 5 + 1);
    }

    public int nextTowerPrice() {
	return (int) (Math.E * towers * towers / 2 + 1);
    }

    public int getPlace() {
	return 3 * houses;
    }

    public int getHouses() {
	return houses;
    }

    public int getTowers() {
	return towers;
    }

    private void initializeComponents() {
	Label title = new Label("Construction");
	title.getStyleClass().add("title");
	setHalignment(title, HPos.CENTER);
	add(title, 0, 0, 2, 1);

	add(new Label("Maisons"), 0, 1);
	housesLabel = new Label("0");
	add(housesLabel, 0, 2);
	buildHouse = new ProgressButton("Construire");
	add(buildHouse, 0, 3);

	add(new Label("Tours"), 1, 1);
	towersLabel = new Label("0");
	add(towersLabel, 1, 2);
	buildTower = new ProgressButton("Construire");
	add(buildTower, 1, 3);
    }

    private void buildHouse() {
	if (consumeMaterialForHouse()) {
	    houses++;
	}
    }

    private void buildTower() {
	if (consumeMaterialForTower()) {
	    towers++;
	}
    }

    private boolean consumeMaterialForHouse() {
	return parent.getResources().consumeWood(nextHousePrice());
    }

    private boolean consumeMaterialForTower() {
	return parent.getResources().consumeStone(nextTowerPrice());
    }

    public void destroyHouses(int amount) {
	houses = Math.max(0, houses - amount);
    }

    public void destroyTowers(int amount) {
	towers = Math.max(0, towers - amount);
    }
}
