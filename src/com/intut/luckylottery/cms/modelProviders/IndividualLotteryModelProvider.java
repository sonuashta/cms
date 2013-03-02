package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.domain.Lottery;

import dummydata.GetDummyData;

public class IndividualLotteryModelProvider {

	public IndividualLotteryModelProvider() {
		lotteryList = GetDummyData.getLotteryData();
		bumperList = new ArrayList<String>();
		codeList = new WritableList();
		existingCodeList = new ArrayList<String>();
		lotteryNameList = new WritableList();
		for (Lottery lottery : lotteryList) {
			if (lottery.getType().equalsIgnoreCase("bumper"))
				bumperList.add(lottery.getName());
		}

		setType(new String[] { "monthly", "bumper" });
		monthlyList = new ArrayList<String>();
		monthlyList.add("Monthly");

	}

	private String name;
	private String code;
	private String mobile1;
	private String mobile2;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String email;
	private List<Lottery> lotteryList;
	private List<String> bumperList;
	private String[] type;
	private WritableList codeList;
	private WritableList lotteryNameList;
	private String selectedType = "monthly";
	private List<String> monthlyList;
	private boolean addButtonEnabled;
	private boolean removeButtonEnabled;
	private boolean sendButtonenabled;
	private boolean verifyButtonEnabled;
	private List<String> existingCodeList;
	private String selectedcode;

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {

		this.selectedType = selectedType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.name,
				this.name = name);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		propertyChangeSupport.firePropertyChange("code", this.code,
				this.code = code);
		if (code.trim().length() >= 1)
			setAddButtonEnabled(true);
		else
			setAddButtonEnabled(false);

	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		propertyChangeSupport.firePropertyChange("mobile1", this.mobile1,
				this.mobile1 = mobile1);
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		propertyChangeSupport.firePropertyChange("mobile2", this.mobile2,
				this.mobile2 = mobile2);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		propertyChangeSupport.firePropertyChange("address", this.address,
				this.address = address);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		propertyChangeSupport.firePropertyChange("city", this.city,
				this.city = city);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		propertyChangeSupport.firePropertyChange("state", this.state,
				this.state = state);
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		propertyChangeSupport.firePropertyChange("zip", this.zip,
				this.zip = zip);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		propertyChangeSupport.firePropertyChange("email", this.email,
				this.email = email);
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

	public WritableList getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codesList) {
		this.codeList.clear();
		this.codeList.addAll(codesList);
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public WritableList getLotteryNameList() {
		return lotteryNameList;
	}

	public void setLotteryNameList() {

		lotteryNameList.clear();

		if (selectedType.equalsIgnoreCase("monthly")) {
			lotteryNameList.addAll(monthlyList);
		} else {
			lotteryNameList.addAll(bumperList);
		}

	}

	public boolean isAddButtonEnabled() {
		return addButtonEnabled;
	}

	public void setAddButtonEnabled(boolean addButtonEnabled) {
		propertyChangeSupport
				.firePropertyChange("addButtonEnabled", this.addButtonEnabled,
						this.addButtonEnabled = addButtonEnabled);
	}

	public boolean isRemoveButtonEnabled() {
		return removeButtonEnabled;
	}

	public void setRemoveButtonEnabled(boolean removeButtonEnabled) {
		propertyChangeSupport.firePropertyChange("removeButtonEnabled",
				this.removeButtonEnabled,
				this.removeButtonEnabled = removeButtonEnabled);
	}

	public boolean isSendButtonenabled() {
		return sendButtonenabled;
	}

	public void setSendButtonenabled(boolean sendButtonenabled) {
		propertyChangeSupport.firePropertyChange("sendButtonenabled",
				this.sendButtonenabled,
				this.sendButtonenabled = sendButtonenabled);
	}

	public boolean isVerifyButtonEnabled() {
		return verifyButtonEnabled;
	}

	public void setVerifyButtonEnabled(boolean verifyButtonEnabled) {
		propertyChangeSupport.firePropertyChange("verifyButtonEnabled",
				this.verifyButtonEnabled,
				this.verifyButtonEnabled = verifyButtonEnabled);
	}

	public void addCodeToCodeList() {
		existingCodeList.add(getCode().trim());
		codeList.clear();
		codeList.addAll(existingCodeList);
		setSelectedcode(getCode());
		setCode("");
		if (existingCodeList.size() > 0)
			setRemoveButtonEnabled(true);

	}

	public void removeCode() {
		existingCodeList.remove(getSelectedcode());
		codeList.clear();
		codeList.addAll(existingCodeList);
		if (existingCodeList.size() >= 1)

			setSelectedcode(existingCodeList.get(existingCodeList.size() - 1));
		if (existingCodeList.size() == 0)
			setRemoveButtonEnabled(false);
	}

	public String getSelectedcode() {
		return selectedcode;
	}

	public void setSelectedcode(String selectedcode) {
		propertyChangeSupport.firePropertyChange("selectedcode",
				this.selectedcode, this.selectedcode = selectedcode);
	}

	public void computeAndDisplayResults() {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Initializing Sending Process", 3); // begin task
					monitor.worked(1);
					

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LotteryLogger.getInstance().setError("Error while forcing thread to sleep"+e.getMessage());
					}
				
					monitor.setTaskName("Sending Message");

					monitor.worked(2);
					monitor.setTaskName("Saving to database");

					monitor.setTaskName("Done");

					monitor.done();

				}
			});
		} catch (InvocationTargetException e) {
			LotteryLogger.getInstance().setError("Progress Dialog Error:"+e.getMessage());

		} catch (InterruptedException e) {
			LotteryLogger.getInstance().setError("Progress Dialog Error:"+e.getMessage());
		}
		

	}

	public boolean sendSms() {
		String str;
		String messageText="";
		if (Util.isStringNullOrEmpty(messageText))
			messageText = "empty";
		try {
			 URI uri = new URI("http",  messageText, "");
			
		//	 URL onlyMsgUrl = uri.toURL();
			
			URL msgUrl = new URL(
					"http://www.smszone.in/sendsms.asp?page=SendSmsBulk&username="+Util.getUserName()+"&password="+Util.getPassword()+"&number=91"
							+ getNumberText() + "&message="+uri.toString().replace("http:", ""));

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

	
	
}
