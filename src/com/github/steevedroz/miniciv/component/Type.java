package com.github.steevedroz.miniciv.component;

public enum Type {
    WOOD {
	@Override
	public String getName() {
	    return "Coupe de bois";
	}
    },
    STONE {
	@Override
	public String getName() {
	    return "Minage";
	}
    },
    INVASION {
	@Override
	public String getName() {
	    return "Réduction d'invasion";
	}
    },
    RESEARCH {
	@Override
	public String getName() {
	    return "Recherche";
	}
    };

    @Override
    public String toString() {
	return getName();
    }

    public abstract String getName();
}
