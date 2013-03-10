package com.intut.luckylottery.cms.customEvents;


import java.util.EventObject;

public class PropertyChangeEvent extends EventObject {

	private String propertyName;

	private int progressValue;

	public PropertyChangeEvent(Object source, String propertyName,
			int progressValue) {
		super(source);

		this.propertyName = propertyName;

		this.progressValue = progressValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public int getProgressValue() {
		return this.progressValue;
	}
}