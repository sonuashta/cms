package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.customEvents.*;
import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.Message;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;

public class ProcessesProgressDialogModelProvider {
	private String labelMessage = "";
	private String logMessage = "";
	private Dbloader dbloader;
	private String tableName;
	private ProcessDialogModelProvider proceesDailogModelProvider;

	public ProcessesProgressDialogModelProvider(List<Customer> customers,
			boolean isMail, String text, String tableName,
			ProcessDialogModelProvider proceesDailogModelProvider) {
		setCustomers(customers);
		dbloader = new Dbloader();
		this.message = text;
		this.isMail = isMail;
		this.tableName = tableName;
		this.proceesDailogModelProvider = proceesDailogModelProvider;
	}

	private String message;
	private boolean isMail;
	private List<Customer> customers;

	private String getMessageText(Customer customer) {
		if (isMail)
			return "Sending mail to " + customer.getName()
					+ " and his/her emailId is " + customer.getEmailId();
		else
			return "Sending message to " + customer.getName()
					+ " and his/her number is " + customer.getPhoneNumber();
	}

	public void sendSMS() {
		Runnable worker = new Runnable() {
			@Override
			public synchronized void run() {
				int count = 0;
				for (Customer customer : customers) {
					fireCustomPropertyChangeEvent(new PropertyChangeEvent(this,
							"totalProgressBar", ++count));
					setLabelMessage(getMessageText(customer));
					setLogMessage(getLogMessage() + "\n "
							+ getMessageText(customer));
					Message message1 = sendSms(message,
							customer.getPhoneNumber());
					boolean isMessageSend = false;
					if (message1.getCode() == HttpURLConnection.HTTP_OK)
						isMessageSend = true;
					try {
						dbloader.insertMessageData(tableName, customer,
								isMessageSend, message1.getMessage());
					} catch (Exception e) {
						LotteryLogger.getInstance().setError(
								"Error in sending message" + e.getMessage());
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

	public Message sendSms(String messageText, String number) {
		Message message1 = new Message();
		String str;

		if (Util.isStringNullOrEmpty(messageText))
			messageText = "empty";
		try {
			URI uri = new URI("http", messageText, "");

			// URL onlyMsgUrl = uri.toURL();

			URL msgUrl = new URL(
					"http://dndopen.dove-sms.com/SMSAPI.jsp?username="
							+ Util.getUserName() + "&password="
							+ Util.getPassword() + "&sendername="
							+ Util.getSenderName() + "&mobileno=91" + number
							+ "&message=" + uri.toString().replace("http:", ""));

			HttpURLConnection connection = (HttpURLConnection) msgUrl
					.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);

			String res = connection.getResponseMessage();
			System.out.println(res);
			String returnstring = "";
			int code = connection.getResponseCode();
			message1.setCode(code);
			if (code == HttpURLConnection.HTTP_OK) {
				// Get response data.
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				while (null != ((str = in.readLine()))) {
					returnstring = returnstring + str;
				}
			}
			message1.setMessage(returnstring);
			connection.disconnect();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in sending message," + e.getMessage());
			message1.setCode(0);
			message1.setMessage("Exception");
		}
		return message1;
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
