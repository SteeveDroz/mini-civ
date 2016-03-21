package com.github.steevedroz.miniciv.widget;

import com.github.steevedroz.miniciv.MiniCiv;

import javafx.scene.layout.GridPane;

public abstract class MiniCivWidget extends GridPane {
    protected MiniCiv parent;

    public MiniCivWidget(MiniCiv parent) {
	this.parent = parent;
    }

    public abstract void update();
}
