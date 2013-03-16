package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.intut.luckylottery.cms.customEvents.*;
import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Fields;

public class ProgressDialogModelProvider {
	private String labelMessage = "";
	private String logMessage = "";
	private Dbloader dbloader;

	public ProgressDialogModelProvider(List<Customer> customers) {
		setCustomers(customers);
		dbloader = new Dbloader();
	}

	private List<Customer> customers;

	private String filterMessageText(String ids) {
		String messageText = "Thanks for buying ticket(s) with Lucky Lottery.\n";
		String s[] = ids.split(",");
		if (s.length >= 5)
			messageText += "Total No. of tickets bought is: " + s.length;
		else
			messageText += "Your ticket no.s are " + ids;
		return messageText;
	}

	public void sendSMS() {
		Runnable worker = new Runnable() {
			@Override
			public synchronized void run() {
				int count = 0;
				for (Customer customer : customers) {
					fireCustomPropertyChangeEvent(new PropertyChangeEvent(this,
							"totalProgressBar", ++count));
					setLabelMessage(" Entering record of customer where s.no. is and name is "
							+ customer.getSerialNumber()
							+ " and "
							+ customer.getName());
					setLogMessage(getLogMessage()
							+ "\n Entering record of customer where s.no. is and name is "
							+ customer.getSerialNumber() + " and "
							+ customer.getName());
					try {
						dbloader.updateCustomer(customer, false, false,
								Constants.pendingMessage);
					} catch (Exception e) {
						
					}
				}
				setLogMessage(getLogMessage() + "\n" + "Completed");
				setLabelMessage("Completed!");
			}

		};

		Thread workerThread = new Thread(worker);
		try {
			workerThread.join();
		} catch (InterruptedException e) {
		}
		workerThread.start();
	}

	public boolean sendSms(String messageText, String number) {
		String str;

		if (Util.isStringNullOrEmpty(messageText))
			messageText = "empty";
		try {
			URI uri = new URI("http", messageText, "");

			// URL onlyMsgUrl = uri.toURL();

			URL msgUrl = new URL(
					"http://www.smszone.in/sendsms.asp?page=SendSmsBulk&username="
							+ Util.getUserName() + "&password="
							+ Util.getPassword() + "&number=91" + number
							+ "&message=" + uri.toString().replace("http:", ""));

			HttpURLConnection connection = (HttpURLConnection) msgUrl
					.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);

			String res = connection.getResponseMessage();
			System.out.println(res);
			String returnstring = "";
			int code = connection.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {
				// Get response data.
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				while (null != ((str = in.readLine()))) {
					returnstring = returnstring + str;
				}
			}
			connection.disconnect();
			if (returnstring.equals("SUCCESS"))
				return true;
		} catch (IOException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
		return false;
	}

	private String getNumberText() {
		return "1234567890";
	}

	public String getLabelMessage() {
		return labelMessage;
	}

	public void setLabelMessage(String labelMessage) {
		propertyChangeSupport.firePropertyChange("labelMessage",
				this.labelMessage, this.labelMessage = labelMessage);
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		propertyChangeSupport.firePropertyChange("logMessage", this.logMessage,
				this.logMessage = logMessage);
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

	protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

	public void addCustomEventListener(
			com.intut.luckylottery.cms.customEvents.PropertyChangeListener listener) {
		listenerList
				.add(com.intut.luckylottery.cms.customEvents.PropertyChangeListener.class,
						listener);
	}

	public void removeCompositeChangeListener(
			com.intut.luckylottery.cms.customEvents.PropertyChangeListener listener) {
		listenerList
				.remove(com.intut.luckylottery.cms.customEvents.PropertyChangeListener.class,
						listener);
	}

	public void fireCustomPropertyChangeEvent(PropertyChangeEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == com.intut.luckylottery.cms.customEvents.PropertyChangeListener.class) {
				((com.intut.luckylottery.cms.customEvents.PropertyChangeListener) listeners[i + 1])
						.propertyChanged(evt);
			}
		}
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
