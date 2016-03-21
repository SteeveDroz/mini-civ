package com.github.steevedroz.miniciv;

import java.util.Timer;
import java.util.TimerTask;

import com.github.steevedroz.miniciv.widget.BuildingWidget;
import com.github.steevedroz.miniciv.widget.LaboratoryWidget;
import com.github.steevedroz.miniciv.widget.PopulationWidget;
import com.github.steevedroz.miniciv.widget.ResourcesWidget;
import com.github.steevedroz.miniciv.widget.WarWidget;
import com.github.steevedroz.miniciv.widget.WorkWidget;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MiniCiv extends Scene {
    private PopulationWidget population;
    private BuildingWidget building;
    private ResourcesWidget resources;
    private WorkWidget work;
    private WarWidget war;
    private LaboratoryWidget laboratory;

    private Timer timer;

    public MiniCiv(Stage stage) {
	super(new FlowPane(), 800, 600);
	stage.setTitle("MiniCiv");
	InitializeComponents();

	this.timer = new Timer();
	this.timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		updateAll();
	    }
	}, 10, 1);
    }

    public void updateAll() {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		population.update();
		building.update();
		resources.update();
		work.update();
		war.update();
		laboratory.update();
	    }
	});
    }

    public PopulationWidget getPopulation() {
	return population;
    }

    public BuildingWidget getBuilding() {
	return building;
    }

    public ResourcesWidget getResources() {
	return resources;
    }

    public WorkWidget getWork() {
	return work;
    }

    public WarWidget getWar() {
	return war;
    }

    public LaboratoryWidget getLaboratory() {
	return laboratory;
    }

    private void InitializeComponents() {
	FlowPane flow = (FlowPane) getRoot();

	population = new PopulationWidget(this);
	population.getStyleClass().add("widget");
	flow.getChildren().add(population);

	building = new BuildingWidget(this);
	building.getStyleClass().add("widget");
	flow.getChildren().add(building);

	resources = new ResourcesWidget(this);
	resources.getStyleClass().add("widget");
	flow.getChildren().add(resources);

	work = new WorkWidget(this);
	work.getStyleClass().add("widget");
	flow.getChildren().add(work);

	war = new WarWidget(this);
	war.getStyleClass().add("widget");
	flow.getChildren().add(war);

	laboratory = new LaboratoryWidget(this);
	laboratory.getStyleClass().add("widget");
	flow.getChildren().add(laboratory);
    }
}
