package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.dummydata.GetDummyData;
import com.intut.luckylottery.cms.util.Util;

public class LotteryModelProvider {

	public LotteryModelProvider() throws Exception {

		loader = new Dbloader();
		bumpers = loader.getBumpers();
		list = new WritableList();
		removeBumperList = new WritableList();
		setList(bumpers);
		setRemoveBumperList(bumpers);
		setRemoveLinkEnable(true);
		setList(bumpers);
		setAddLinkEnabled(true);
		selectedBumper = loader.getSelectedBumper();
		setMessage(loader.getDefaultMessage());

	}

	private boolean addLinkEnabled;
	private boolean addButtonEnabled;
	private boolean cancelButtonEnabled;
	private boolean textEnabled;
	private String text;
	private String items;
	private String selectedBumper;
	private String selectedRemoveBumper;
	private String message;

	public String getSelectedBumper() {
		return selectedBumper;
	}

	public void setSelectedBumper(String selectedBumper) {
		propertyChangeSupport.firePropertyChange("selectedBumper",
				this.selectedBumper, this.selectedBumper = selectedBumper);
	}

	private List<String> bumpers;
	private WritableList list;
	private boolean removeLinkEnable;
	private boolean cancelRemoveVisible;
	private boolean removeButtonVisible;
	private boolean removebumperListVisible;
	private WritableList removeBumperList;
	private Dbloader loader;

	public boolean isRemovebumperListVisible() {
		return removebumperListVisible;
	}

	public void setRemovebumperListVisible(boolean removebumperListVisible) {
		propertyChangeSupport.firePropertyChange("removebumperListVisible",
				this.removebumperListVisible,
				this.removebumperListVisible = removebumperListVisible);
	}

	public WritableList getRemoveBumperList() {
		return removeBumperList;
	}

	public void setRemoveBumperList(List<String> removeBumperList) {
		this.removeBumperList.clear();
		this.removeBumperList.addAll(removeBumperList);
	}

	public boolean isRemoveLinkEnable() {
		return removeLinkEnable;
	}

	public void setRemoveLinkEnable(boolean removeLinkEnable) {
		if (!removeLinkEnable) {
			setCancelRemoveVisible(true);
			setRemoveButtonVisible(true);
			setRemovebumperListVisible(true);
		} else {
			setCancelRemoveVisible(false);
			setRemoveButtonVisible(false);
			setRemovebumperListVisible(false);
		}
		propertyChangeSupport
				.firePropertyChange("removeLinkEnable", this.removeLinkEnable,
						this.removeLinkEnable = removeLinkEnable);
	}

	public boolean isCancelRemoveVisible() {
		return cancelRemoveVisible;
	}

	public void setCancelRemoveVisible(boolean cancelRemoveVisible) {
		propertyChangeSupport.firePropertyChange("cancelRemoveVisible",
				this.cancelRemoveVisible,
				this.cancelRemoveVisible = cancelRemoveVisible);
	}

	public boolean isRemoveButtonVisible() {
		return removeButtonVisible;
	}

	public void setRemoveButtonVisible(boolean removeButtonVisible) {
		propertyChangeSupport.firePropertyChange("removeButtonVisible",
				this.removeButtonVisible,
				this.removeButtonVisible = removeButtonVisible);
	}

	public boolean isAddLinkEnabled() {
		return addLinkEnabled;
	}

	public void setAddLinkEnabled(boolean addLinkEnabled) {
		propertyChangeSupport.firePropertyChange("addLinkEnabled",
				this.addLinkEnabled, this.addLinkEnabled = addLinkEnabled);
	}

	public boolean isAddButtonEnabled() {
		return addButtonEnabled;
	}

	public void setAddButtonEnabled(boolean addButtonEnabled) {
		propertyChangeSupport
				.firePropertyChange("addButtonEnabled", this.addButtonEnabled,
						this.addButtonEnabled = addButtonEnabled);
	}

	public boolean isCancelButtonEnabled() {
		return cancelButtonEnabled;
	}

	public void setCancelButtonEnabled(boolean cancelButtonEnabled) {
		propertyChangeSupport.firePropertyChange("cancelButtonEnabled",
				this.cancelButtonEnabled,
				this.cancelButtonEnabled = cancelButtonEnabled);
	}

	public boolean isTextEnabled() {
		return textEnabled;
	}

	public void setTextEnabled(boolean textEnabled) {
		propertyChangeSupport.firePropertyChange("textEnabled",
				this.textEnabled, this.textEnabled = textEnabled);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		propertyChangeSupport.firePropertyChange("text", this.text,
				this.text = text);
		if (!Util.isStringNullOrEmpty(text))
			setAddButtonEnabled(true);
		else {
			setAddButtonEnabled(false);
		}
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public List<String> getBumpers() {
		return bumpers;
	}

	public void setBumpers(List<String> bumpers) {
		this.bumpers = bumpers;
	}

	public WritableList getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list.clear();
		this.list.addAll(list);
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

	public void addBumper() throws Exception {
		String bumper = getSelectedBumper();
		loader.insertBumper(getText());
		bumpers = loader.getBumpers();
		setList(bumpers);
		setRemoveBumperList(bumpers);
		setSelectedBumper(bumper);
	}

	public String getSelectedRemoveBumper() {
		return selectedRemoveBumper;
	}

	public void setSelectedRemoveBumper(String selectedRemoveBumper) {

		propertyChangeSupport.firePropertyChange("selectedRemoveBumper",
				this.selectedRemoveBumper,
				this.selectedRemoveBumper = selectedRemoveBumper);
	}

	public void deleteBumper() throws Exception {
		String lastSelected = getSelectedBumper();
		loader.deleteBumper(selectedRemoveBumper);

		bumpers = loader.getBumpers();
		setList(bumpers);
		setRemoveBumperList(bumpers);
		if (selectedRemoveBumper.equalsIgnoreCase(lastSelected))
			setSelectedBumper("");
		else
			setSelectedBumper(lastSelected);
		setSelectedRemoveBumper("");

	}

	public void saveSettings() throws Exception {
		loader.selectBumper(getSelectedBumper());
		loader.insertDefaultMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		propertyChangeSupport.firePropertyChange("message", this.message,
				this.message = message);
	}
}
