//package com.intut.luckylottery.cms.modelProviders;
//
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.jface.dialogs.ProgressMonitorDialog;
//import org.eclipse.jface.operation.IRunnableWithProgress;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.widgets.TableItem;
//
//import com.intut.luckylottery.cms.dialogs.AddEditCustomerDialog;
//import com.intut.luckylottery.cms.dialogs.MultiCustomerDialog;
//import com.intut.luckylottery.cms.dialogs.ProgressDialog;
//import com.intut.luckylottery.cms.util.LotteryLogger;
//import com.intut.luckylottery.cms.util.Util;
//import com.intut.luckylottery.domain.Customer;
//
//public class MultidialogModelProvider {
//
//	public MultidialogModelProvider(MultiCustomerDialog multiCustomerDialog) {
//
//		customers = new ArrayList<Customer>();
//		this.multiCustomerDialog = multiCustomerDialog;
//	}
//
//	private String filePath;
//	private List<Customer> customers;
//	private boolean sendButtonEnabled;
//	private boolean uploadCSVButtonEnabled;
//	private MultiCustomerDialog multiCustomerDialog;
//
//	public String getFilePath() {
//		return filePath;
//	}
//
//	public void setFilePath(String filePath) {
//		propertyChangeSupport.firePropertyChange("filePath", this.filePath,
//				this.filePath = filePath);
//		if (!Util.isStringNullOrEmpty(filePath))
//			setUploadCSVButtonEnabled(true);
//		else
//			setUploadCSVButtonEnabled(false);
//	}
//
//	public boolean isSendButtonEnabled() {
//		return sendButtonEnabled;
//	}
//
//	public void setSendButtonEnabled(boolean sendButtonEnabled) {
//		propertyChangeSupport.firePropertyChange("sendButtonEnabled",
//				this.sendButtonEnabled,
//				this.sendButtonEnabled = sendButtonEnabled);
//	}
//
//	public boolean isUploadCSVButtonEnabled() {
//		return uploadCSVButtonEnabled;
//	}
//
//	public void setUploadCSVButtonEnabled(boolean uploadCSVButtonEnabled) {
//		propertyChangeSupport.firePropertyChange("uploadCSVButtonEnabled",
//				this.uploadCSVButtonEnabled,
//				this.uploadCSVButtonEnabled = uploadCSVButtonEnabled);
//	}
//
//	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
//			this);
//
//	public void addPropertyChangeListener(String propertyName,
//			PropertyChangeListener listener) {
//		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
//	}
//
//	public void removePropertyChangeListener(PropertyChangeListener listener) {
//		propertyChangeSupport.removePropertyChangeListener(listener);
//	}
//
//	public void setTableItems() {
//		Display.getDefault().asyncExec(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//
//					Table table = multiCustomerDialog.getTable();
//					int index = 1;
//					for (Customer customer : customers) {
//						TableItem item = new TableItem(table, table.getStyle());
//						item.setText(new String[] { "" + index++,
//								customer.getName(), customer.getCode(),
//								customer.getMobile1() });
//					}
//				} catch (Exception e) {
//					LotteryLogger.getInstance().setError(e.getMessage());
//				}
//
//			}
//		});
//
//	}
//
//	public void uploadCSV() {
//		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
//				.getCurrent().getActiveShell());
//
//		try {
//			dialog.run(true, true, new IRunnableWithProgress() {
//				public void run(IProgressMonitor monitor) {
//					monitor.beginTask("Reading Customers Details", 3); // begin
//																		// task
//					monitor.worked(1);
//					File file = new File(filePath);
//					try {
//						customers.addAll(Util.readAllCustomers(file));
//					} catch (IOException e) {
//
//					}
//					monitor.setTaskName("Setting table");
//					setTableItems();
//					monitor.worked(2);
//					if (customers.size() > 0)
//						setSendButtonEnabled(true);
//					else
//						setSendButtonEnabled(false);
//					monitor.setTaskName("Done");
//
//					monitor.done();
//
//				}
//			});
//		} catch (InvocationTargetException e) {
//			LotteryLogger.getInstance().setError(
//					"Progress Dialog Error:" + e.getMessage());
//
//		} catch (InterruptedException e) {
//			LotteryLogger.getInstance().setError(
//					"Progress Dialog Error:" + e.getMessage());
//		}
//
//	}
//
//	public List<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void editCustomer(int index) {
//
//	}
//
//	public void sendSMS() {
//		ProgressDialog dialog = new ProgressDialog(new Shell(), customers);
//		dialog.open();
//
//	}
//}
