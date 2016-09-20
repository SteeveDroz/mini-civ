package com.github.steevedroz.miniciv.loader;

import java.util.List;

import com.github.steevedroz.miniciv.FileFormatException;
import com.github.steevedroz.miniciv.MiniCiv;
import com.github.steevedroz.miniciv.Version;

public abstract class Loader {
    protected Version version;

    protected Loader(Version version) {
	this.version = version;
    }

    public static Loader getLoader(Version version) {
	if (version.isAtLeast("v1.1")) {
	    return new Loader1_1(version);
	}
	if (version.isAtLeast("v1.0")) {
	    return new Loader1_0(version);
	}
	return null;
    }

    public abstract void load(MiniCiv miniCiv, List<String> data) throws FileFormatException;

    public abstract List<String> save(MiniCiv miniCiv);
}
