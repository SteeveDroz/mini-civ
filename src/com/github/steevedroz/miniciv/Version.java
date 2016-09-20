package com.github.steevedroz.miniciv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
    private int major;
    private int minor;
    private int hotfix;
    private Version.Type type;

    public Version(String version) {
	Pattern pattern = Pattern.compile("^v?(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?(?:-([a-z]+))?$",
		Pattern.CASE_INSENSITIVE);
	Matcher matcher = pattern.matcher(version);
	if (matcher.find()) {
	    try {
		major = Integer.parseInt(matcher.group(1));
	    } catch (NumberFormatException e) {
		major = 0;
	    }

	    try {
		minor = Integer.parseInt(matcher.group(2));
	    } catch (NumberFormatException e) {
		minor = 0;
	    }

	    try {
		hotfix = Integer.parseInt(matcher.group(3));
	    } catch (NumberFormatException e) {
		hotfix = 0;
	    }

	    try {
		for (Version.Type versionType : Version.Type.values()) {
		    if (versionType.name().toLowerCase().equals(matcher.group(4).toLowerCase())) {
			type = versionType;
			break;
		    }
		}
	    } catch (NullPointerException e) {
		type = Version.Type.NONE;
	    }
	}
    }

    public Version(int major) {
	this.major = major;
    }

    public Version(int major, int minor) {
	this(major);
	this.minor = minor;
    }

    public Version(int major, int minor, int hotfix) {
	this(major, minor);
	this.hotfix = hotfix;
    }

    public Version(int major, Version.Type type) {
	this(major);
	this.type = type;
    }

    public Version(int major, int minor, Type type) {
	this(major, minor);
	this.type = type;
    }

    public Version(int major, int minor, int hotfix, Type type) {
	this(major, minor, hotfix);
	this.type = type;
    }

    @Override
    public int compareTo(Version other) {
	if (this.type != other.type) {
	    return this.type.ordinal() - other.type.ordinal();
	}
	if (this.major != other.major) {
	    return this.major - other.major;
	}
	if (this.minor != other.minor) {
	    return this.minor - other.minor;
	}
	if (this.hotfix != other.hotfix) {
	    return this.hotfix - other.hotfix;
	}
	return 0;
    }

    public boolean isAtLeast(Version version) {
	return this.compareTo(version) >= 0;
    }

    public boolean isAtLeast(String version) {
	return this.isAtLeast(new Version(version));
    }

    public int getMajor() {
	return major;
    }

    public int getMinor() {
	return minor;
    }

    public int getHotfix() {
	return hotfix;
    }

    public Version.Type getType() {
	return type;
    }

    public void setMajor(int major) {
	this.major = major;
    }

    public void setMinor(int minor) {
	this.minor = minor;
    }

    public void setHotfix(int hotfix) {
	this.hotfix = hotfix;
    }

    public void setType(Version.Type type) {
	this.type = type;
    }

    public enum Type {
	ALPHA, BETA, DEV, NONE
    }
}
