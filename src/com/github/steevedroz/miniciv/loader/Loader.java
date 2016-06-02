package com.github.steevedroz.miniciv.loader;

import java.util.List;

import com.github.steevedroz.miniciv.FileFormatException;
import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.Version;

public abstract class Loader {
    public static Loader getLoader(Version version) {
	if (version.isAtLeast("v0.1-alpha")) {
	    return new Loader0_1_alpha();
	}
	return null;
    }

    public abstract void load(MiniCiv miniCiv, List<String> data) throws FileFormatException;

    public abstract List<String> save(MiniCiv miniCiv);
}
