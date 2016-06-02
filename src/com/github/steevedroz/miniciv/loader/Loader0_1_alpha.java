package com.github.steevedroz.miniciv.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.steevedroz.miniciv.MiniCiv;

public class Loader0_1_alpha extends Loader {
    @Override
    public void load(MiniCiv miniCiv, List<String> data) {
	data.remove(0);
	Pattern pattern = Pattern.compile("^([a-z0-9_]+): *(.+)$");
	for (String entry : data) {
	    try {
		Matcher matcher = pattern.matcher(entry);
		matcher.find();
		String key = matcher.group(1);
		String value = matcher.group(2);
		if (key.equals("inhabitants")) {
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
		} else if (key.equals("research_points")) {
		    miniCiv.getLaboratory().setResearchPoints(Integer.parseInt(value));
		}
	    } catch (Exception e) {
		continue;
	    }
	}
    }

    @Override
    public List<String> save(MiniCiv miniCiv) {
	List<String> data = new ArrayList<String>();
	data.add("v0.1-alpha");
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
	data.add("research_points: " + miniCiv.getLaboratory().getResearchPoints());
	return data;
    }
}
