package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.component.ProgressButton;
import com.github.steevedroz.miniciv.component.Type;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class ResourcesWidget extends MiniCivWidget {
    private int wood;
    private int stone;

    private Label woodLabel;
    private Label stoneLabel;

    private ProgressButton chopWood;
    private ProgressButton pickStone;

    public ResourcesWidget(MiniCiv parent) {
	super(parent);
	initializeComponents();

	wood = 0;
	stone = 0;
    }

    @Override
    public void update() {
	woodLabel.setText("" + wood);
	stoneLabel.setText("" + stone);

	wood += chopWood.getOverflow();
	stone += pickStone.getOverflow();

	autoWorkWood(0.1 * parent.getWork().getWoodcutters() * parent.getLaboratory().getUpgrade(Type.WOOD));
	autoWorkStone(0.1 * parent.getWork().getMiners() * parent.getLaboratory().getUpgrade(Type.STONE));
    }

    public boolean consumeWood(int amount) {
	if (!hasWood(amount)) {
	    return false;
	}
	wood -= amount;
	return true;
    }

    public boolean consumeStone(int amount) {
	if (!hasStone(amount)) {
	    return false;
	}
	stone -= amount;
	return true;
    }

    public void autoWorkWood(double amount) {
	chopWood.addProgress(amount);
    }

    public void autoWorkStone(double amount) {
	pickStone.addProgress(amount);
    }

    public int getWood() {
	return wood;
    }

    public int getStone() {
	return stone;
    }

    public boolean hasWood(int amount) {
	return wood >= amount;
    }

    public boolean hasStone(int amount) {
	return stone >= amount;
    }

    private void initializeComponents() {
	Label title = new Label("Ressources");
	title.getStyleClass().add("title");
	setHalignment(title, HPos.CENTER);
	add(title, 0, 0, 2, 1);

	add(new Label("Bois"), 0, 1);

	woodLabel = new Label("0");
	add(woodLabel, 0, 2);

	chopWood = new ProgressButton("Couper du bois");
	chopWood.setTooltip(new Tooltip("bûcherons / 10 + clic"));
	add(chopWood, 0, 3);

	add(new Label("Pierres"), 1, 1);

	stoneLabel = new Label("0");
	add(stoneLabel, 1, 2);

	pickStone = new ProgressButton("Récolter des pierres");
	pickStone.setTooltip(new Tooltip("mineurs / 10  + clic"));
	add(pickStone, 1, 3);
    }

    public void destroyAllResources(double percentage) {
	wood *= 1 - percentage;
	stone *= 1 - percentage;
    }

    public int destroyWood(double percentage) {
	if (wood == 0) {
	    return 0;
	}
	int loss = (int) (wood * percentage + 1);
	wood -= loss;
	return loss;
    }

    public int destroyStone(double percentage) {
	if (stone == 0) {
	    return 0;
	}
	int loss = (int) (stone * percentage + 1);
	stone -= loss;
	return loss;
    }

    public void setWood(int wood) {
	this.wood = wood;
    }

    public void setStone(int stone) {
	this.stone = stone;
    }
}
