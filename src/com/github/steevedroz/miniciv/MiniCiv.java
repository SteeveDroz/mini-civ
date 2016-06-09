package com.github.steevedroz.miniciv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.github.steevedroz.miniciv.loader.Loader;
import com.github.steevedroz.miniciv.widget.BuildingWidget;
import com.github.steevedroz.miniciv.widget.LaboratoryWidget;
import com.github.steevedroz.miniciv.widget.PopulationWidget;
import com.github.steevedroz.miniciv.widget.ResourcesWidget;
import com.github.steevedroz.miniciv.widget.WarWidget;
import com.github.steevedroz.miniciv.widget.WorkWidget;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MiniCiv extends Scene {
    private PopulationWidget population;
    private BuildingWidget building;
    private ResourcesWidget resources;
    private WorkWidget work;
    private WarWidget war;
    private LaboratoryWidget laboratory;

    private FileChooser fileChooser;
    private Timer timer;

    public MiniCiv(Stage stage) {
	super(new BorderPane(), 800, 700);
	stage.setTitle("MiniCiv");
	initializeComponents();
	initializeMenu();

	this.timer = new Timer();
	this.timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		updateAll();
	    }
	}, 10, 1);
    }

    public void updateAll() {
	Platform.runLater(() -> {
	    population.update();
	    building.update();
	    resources.update();
	    work.update();
	    war.update();
	    laboratory.update();
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

    private void initializeComponents() {
	FlowPane flow = new FlowPane();

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

	((BorderPane) getRoot()).setCenter(flow);
    }

    private void initializeMenu() {
	MenuBar menu = new MenuBar();

	Menu fileMenu = new Menu("Fichier");
	menu.getMenus().add(fileMenu);

	MenuItem save = new MenuItem("Sauvegarder");
	save.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		save();
	    }
	});

	MenuItem load = new MenuItem("Ouvrir");
	load.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		load();
	    }
	});

	fileMenu.getItems().addAll(save, load);

	((BorderPane) getRoot()).setTop(menu);

	fileChooser = new FileChooser();
	fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers MiniCiv (*.miniciv)", "*.miniciv"));
	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private void save() {
	fileChooser.setTitle("Enregistrer un fichier MiniCiv");
	File file = fileChooser.showSaveDialog(getWindow());
	if (file != null) {
	    try {
		Loader loader = Loader.getLoader(new Version(Main.VERSION));
		List<String> data = loader.save(this);
		Files.write(file.toPath(), data);
	    } catch (IOException e) {
		new Alert(AlertType.ERROR,
			"Une erreur inconnue s'est produite, votre action n'a pas pu être menée à terme. Merci de réessayer.",
			ButtonType.OK).showAndWait();
	    }
	}
    }

    private void load() {
	fileChooser.setTitle("Ouvrir un fichier MiniCiv");
	File file = fileChooser.showOpenDialog(getWindow());
	if (file != null) {
	    try {
		List<String> data = Files.readAllLines(file.toPath());
		Version version = new Version(data.get(0));
		Loader loader = Loader.getLoader(version);
		loader.load(this, data);
	    } catch (FileFormatException e) {
		new Alert(AlertType.ERROR,
			"Le fichier que vous essayez de charger n'est pas reconnu par cette version de MiniCiv. Il s'agit d'un fichier corrompu, d'un fichier fait à la main ou d'une version soit antérieure, soit bien plus ancienne que la version actuelle du logiciel ("
				+ Main.VERSION + ").");
	    } catch (Exception e) {
		new Alert(AlertType.ERROR,
			"Une erreur inconnue s'est produite, votre action n'a pas pu être menée à terme. Merci de réessayer.",
			ButtonType.OK).showAndWait();
	    }
	}
    }
}
