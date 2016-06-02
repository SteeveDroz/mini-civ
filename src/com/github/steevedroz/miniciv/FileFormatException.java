package com.github.steevedroz.miniciv;

public class FileFormatException extends Exception {
    private static final long serialVersionUID = 1L;

    public FileFormatException() {
	super();
    }

    public FileFormatException(String message) {
	super(message);
    }
}
