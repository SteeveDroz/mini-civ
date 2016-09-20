package com.github.steevedroz.miniciv.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.steevedroz.miniciv.FileFormatException;
import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.Version;
import com.github.steevedroz.miniciv.component.Research;
import com.github.steevedroz.miniciv.component.Research.State;

public class Loader1_1 extends Loader {
    protected Loader1_1(Version version) {
	super(version);
    }

    @Override
    public void load(MiniCiv miniCiv, List<String> data) throws FileFormatException {
	data.remove(0);
	Pattern pattern = Pattern.compile("^([a-z0-9_]+): *(.+)$");
	for (String entry : data) {
	    try {
		Matcher matcher = pattern.matcher(entry);
		matcher.find();
		String key = matcher.group(1);
		String value = matcher.group(2);
		if (key.equals("previous_inhabitants")) {
		    miniCiv.getChangeVillage().setPreviousInhabitants(Integer.parseInt(value));
		} else if (key.equals("inhabitants")) {
		    miniCiv.getPopulation().setInhabitants(Integer.parseInt(value));
		} else if (key.equals("houses")) {
		    miniCiv.getBuilding().setHouses(Integer.parseInt(value));
		} else if (key.equals("towers")) {
		    miniCiv.getBuilding().setTowers(Integer.parseInt(value));
		} else if (key.equals("wood")) {
		    miniCiv.getResources().setWood(Integer.parseInt(value));
		} else if (key.equals("stone")) {
		    miniCiv.getResources().setStone(Integer.parseInt(value));
		} else if (key.equals("woodcutters")) {
		    miniCiv.getWork().setWoodcutters(Integer.parseInt(value));
		} else if (key.equals("miners")) {
		    miniCiv.getWork().setMiners(Integer.parseInt(value));
		} else if (key.equals("ambassadors")) {
		    miniCiv.getWork().setAmbassadors(Integer.parseInt(value));
		} else if (key.equals("warriors")) {
		    miniCiv.getWork().setWarriors(Integer.parseInt(value));
		} else if (key.equals("inventors")) {
		    miniCiv.getWork().setInventors(Integer.parseInt(value));
		} else if (key.equals("research")) {
		    String[] elements = value.split(", *");
		    Research research = miniCiv.getLaboratory().getResearch(elements[0],
			    Double.parseDouble(elements[1]));
		    research.setState(State.RESEARCHED);
		    miniCiv.getLaboratory().discover(research);
		} else if (key.equals("research_points")) {
		    miniCiv.getLaboratory().setResearchPoints(Double.parseDouble(value));
		} else {
		    throw new Exception();
		}
	    } catch (Exception e) {
		throw new FileFormatException();
	    }
	}
    }

    @Override
    public List<String> save(MiniCiv miniCiv) {
	List<String> data = new ArrayList<String>();
	data.add("v1.1");
	data.add("previous_inhabitants: " + miniCiv.getChangeVillage().getPreviousInhabitants());
	data.add("inhabitants: " + miniCiv.getPopulation().getInhabitants());
	data.add("houses: " + miniCiv.getBuilding().getHouses());
	data.add("towers: " + miniCiv.getBuilding().getTowers());
	data.add("wood: " + miniCiv.getResources().getWood());
	data.add("stone: " + miniCiv.getResources().getStone());
	data.add("woodcutters: " + miniCiv.getWork().getWoodcutters());
	data.add("miners: " + miniCiv.getWork().getMiners());
	data.add("ambassadors: " + miniCiv.getWork().getAmbassadors());
	data.add("warriors: " + miniCiv.getWork().getWarriors());
	data.add("inventors: " + miniCiv.getWork().getInventors());
	List<Research> researches = miniCiv.getLaboratory().getResearches();
	for (Research research : researches) {
	    if (research.getState().equals(State.RESEARCHED)) {
		data.add("research: " + research.getType().name() + ", " + research.getPrice());
	    }
	}
	data.add("research_points: " + miniCiv.getLaboratory().getResearchPoints());
	return data;
    }
}
