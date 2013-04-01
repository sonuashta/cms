package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.SearchFilter;
import com.intut.luckylottery.cms.domain.SearchResult;
import com.intut.luckylottery.cms.util.Constants;

public class ReportResultModelProvider {

	public ReportResultModelProvider(SearchResult searchResult) {
		this.searchResult = searchResult;
		setCustomers(this.searchResult.customers);
		loader = new Dbloader();
		setPreviousButton(false);
		if (searchResult.totalResults > Constants.pageCount) {
			this.labelMessage = "Showing results 1-" + Constants.pageCount
					+ " of " + searchResult.totalResults;
			setNextButton(true);
		} else {
			setNextButton(false);
			this.labelMessage = "Showing results 1-"
					+ searchResult.totalResults + " of "
					+ searchResult.totalResults;
			;
		}

	}

	private Group group;
	private String labelMessage;
	private int pageNumber;
	private int pageCount;
	private boolean nextButton;
	private boolean previousButton;
	private SearchResult searchResult;
	private Dbloader loader;

	public String getLabelMessage() {
		return labelMessage;
	}

	public void setGroup(Group gp) {
		this.group = gp;
	}

	public void setLabelMessage(final String labelMessage) {
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				group.setText(labelMessage);
			}
		});
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public boolean isNextButton() {
		return nextButton;
	}

	public void setNextButton(boolean nextButton) {
		propertyChangeSupport.firePropertyChange("nextButton", this.nextButton,
				this.nextButton = nextButton);
	}

	public boolean isPreviousButton() {
		return previousButton;
	}

	private List<Customer> customers;

	public void setPreviousButton(boolean previousButton) {
		propertyChangeSupport.firePropertyChange("previousButton",
				this.previousButton, this.previousButton = previousButton);
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

	public List<Customer> getCustomers() {
		return customers;
	}

	public SearchResult getSearchResult() {
		return this.searchResult;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void searchPreviousResults() throws Exception {
		SearchFilter filter = searchResult.searchFilter;
		filter.setPageNumber(filter.getPageNumber() - 1);
		SearchResult result = loader.getCustomers(filter);
		this.searchResult = result;
		setCustomers(result.customers);
	}

	public void searchNextResults() throws Exception {
		SearchFilter filter = searchResult.searchFilter;
		filter.setPageNumber(filter.getPageNumber() + 1);
		SearchResult result = loader.getCustomers(filter);
		this.searchResult = result;
		setCustomers(result.customers);
	}
}
