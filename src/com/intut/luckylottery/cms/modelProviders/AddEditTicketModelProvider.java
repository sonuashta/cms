package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddEditTicketModelProvider {

	private String series;
	private String ticketNumber;
	private boolean doneButtonEnable;
	private String selectedBumper;

	public AddEditTicketModelProvider(String series, String ticketNumber) {
		setSeries(series);
		setTicketNumber(ticketNumber);
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		propertyChangeSupport.firePropertyChange("series", this.series,
				this.series = series);
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		propertyChangeSupport.firePropertyChange("ticketNumber",
				this.ticketNumber, this.ticketNumber = ticketNumber);
	}

	public boolean isDoneButtonEnable() {
		return doneButtonEnable;
	}

	public void setDoneButtonEnable(boolean doneButtonEnable) {
		propertyChangeSupport
				.firePropertyChange("doneButtonEnable", this.doneButtonEnable,
						this.doneButtonEnable = doneButtonEnable);
	}

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}


	public String getSelectedBumper() {
		return selectedBumper;
	}

	public void setSelectedBumper(String selectedBumper) {
		propertyChangeSupport
		.firePropertyChange("selectedBumper", this.selectedBumper,
				this.selectedBumper = selectedBumper);
	}
}
