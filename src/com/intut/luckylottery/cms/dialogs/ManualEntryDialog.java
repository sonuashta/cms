package com.intut.luckylottery.cms.dialogs;

import java.awt.Toolkit;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

import com.intut.luckylottery.cms.dummydata.GetDummyData;
import com.intut.luckylottery.cms.modelProviders.ManualEntrydialogModelProvider;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.ResourceManager;

public class ManualEntryDialog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_4;
	private Text text_6;
	private ManualEntrydialogModelProvider modelProvider;
	private DateTime dateTime;
	private Text serialNumberText;
	private Text text_2;
	private Text text_3;
	private Text text_5;
	private Text text_7;
	private Button btnSendSms;
	private Button btnNewButton;
	private Combo combo_1;
	private Combo combo;
	private Text text_8;
	private Button btnCheckButton;
	private Label lblLength;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 * @throws Exception
	 */
	public ManualEntryDialog(Shell parent) throws Exception {
		super(parent);
		setText("SWT Dialog");
		modelProvider = new ManualEntrydialogModelProvider();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(ResourceManager.getPluginImage(
				"com.intut.luckylottery.cms",
				"icons/appIcons/toolbar/edit-user.png"));
		shell.setBounds(Util.setBouunds(700, 550));
		shell.setText("Add Customer");
		shell.setLayout(new FormLayout());

		Group grpCustomerInformation = new Group(shell, SWT.NONE);
		grpCustomerInformation.setFont(SWTResourceManager.getFont("Segoe UI",
				12, SWT.BOLD));
		grpCustomerInformation.setText("Customer Information");
		grpCustomerInformation.setLayout(new FormLayout());
		FormData fd_grpCustomerInformation = new FormData();
		fd_grpCustomerInformation.left = new FormAttachment(0, 10);
		fd_grpCustomerInformation.top = new FormAttachment(0, 10);
		grpCustomerInformation.setLayoutData(fd_grpCustomerInformation);

		Label lblName = new Label(grpCustomerInformation, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(0, 10);
		fd_lblName.left = new FormAttachment(0, 10);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name");

		text = new Text(grpCustomerInformation, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -10);
		fd_text.top = new FormAttachment(0, 31);
		fd_text.left = new FormAttachment(0, 10);
		text.setLayoutData(fd_text);

		Label lblNumber = new Label(grpCustomerInformation, SWT.NONE);
		lblNumber.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblNumber = new FormData();
		fd_lblNumber.top = new FormAttachment(text, 6);
		fd_lblNumber.left = new FormAttachment(0, 10);
		lblNumber.setLayoutData(fd_lblNumber);
		lblNumber.setText("Phone Number");

		text_1 = new Text(grpCustomerInformation, SWT.BORDER);
		modelProvider.setMobileText(text_1);
		text_1.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				final String oldS = text_1.getText();
				final String newS = oldS.substring(0, e.start) + e.text
						+ oldS.substring(e.end);

				try {
					if (!newS.equals(""))
						Long.parseLong((newS));
					// value is decimal
				} catch (final NumberFormatException numberFormatException) {
					Toolkit tk = Toolkit.getDefaultToolkit();
					tk.beep();

					e.doit = false;
					modelProvider.setPhoneNumber(oldS);
					return;
				}
				if (!Util.isStringNullOrEmpty(newS) && newS.length() > 10) {
					MessageDialog
							.openWarning(shell, "Warning",
									"Number length should be less than or equals to 10");
					e.doit = false;
					modelProvider.setPhoneNumber(oldS);
					return;
				}
				modelProvider.setPhoneNumber(newS);

			}

		});
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.left = new FormAttachment(0, 10);
		fd_text_1.top = new FormAttachment(lblNumber, 6);
		text_1.setLayoutData(fd_text_1);

		Label lblDate = new Label(grpCustomerInformation, SWT.NONE);
		lblDate.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblDate = new FormData();
		fd_lblDate.left = new FormAttachment(0, 10);
		fd_lblDate.top = new FormAttachment(lblNumber, 35);
		lblDate.setLayoutData(fd_lblDate);
		lblDate.setText("Date");

		dateTime = new DateTime(grpCustomerInformation, SWT.BORDER
				| SWT.DROP_DOWN);
		FormData fd_dateTime = new FormData();
		fd_dateTime.left = new FormAttachment(0, 10);
		fd_dateTime.top = new FormAttachment(lblDate, 6);
		dateTime.setLayoutData(fd_dateTime);

		Label lblEmail = new Label(grpCustomerInformation, SWT.NONE);
		lblEmail.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblEmail = new FormData();
		fd_lblEmail.left = new FormAttachment(0, 13);
		lblEmail.setLayoutData(fd_lblEmail);
		lblEmail.setText("Email");

		text_4 = new Text(grpCustomerInformation, SWT.BORDER);
		FormData fd_text_4 = new FormData();
		fd_text_4.right = new FormAttachment(100, -10);
		fd_text_4.left = new FormAttachment(0, 10);
		fd_text_4.top = new FormAttachment(lblEmail, 6);
		text_4.setLayoutData(fd_text_4);

		Label lblAddress_1 = new Label(grpCustomerInformation, SWT.NONE);
		lblAddress_1.setFont(SWTResourceManager
				.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblAddress_1 = new FormData();
		fd_lblAddress_1.top = new FormAttachment(lblEmail, 33);
		fd_lblAddress_1.left = new FormAttachment(0, 10);
		lblAddress_1.setLayoutData(fd_lblAddress_1);
		lblAddress_1.setText("Address");

		text_6 = new Text(grpCustomerInformation, SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL | SWT.MULTI);
		FormData fd_text_6 = new FormData();
		fd_text_6.bottom = new FormAttachment(100, -10);
		fd_text_6.right = new FormAttachment(100, -10);
		fd_text_6.left = new FormAttachment(0, 10);
		fd_text_6.top = new FormAttachment(lblAddress_1, 6);
		text_6.setLayoutData(fd_text_6);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		fd_grpCustomerInformation.right = new FormAttachment(100, -471);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.left = new FormAttachment(grpCustomerInformation, 6);
		fd_tabFolder.top = new FormAttachment(0, 143);
		tabFolder.setLayoutData(fd_tabFolder);

		TabItem tbtmMonthly = new TabItem(tabFolder, SWT.NONE);

		tbtmMonthly.setText("Monthly");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmMonthly.setControl(composite);
		composite.setLayout(new FormLayout());

		Label lblTicketNumber = new Label(composite, SWT.NONE);
		lblTicketNumber.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblTicketNumber = new FormData();
		fd_lblTicketNumber.top = new FormAttachment(0, 10);
		fd_lblTicketNumber.left = new FormAttachment(0, 10);
		lblTicketNumber.setLayoutData(fd_lblTicketNumber);
		lblTicketNumber.setText("Ticket Number(s)");

		text_2 = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		FormData fd_text_2 = new FormData();
		fd_text_2.bottom = new FormAttachment(100, -10);
		fd_text_2.top = new FormAttachment(lblTicketNumber, 6);
		fd_text_2.left = new FormAttachment(0, 10);
		fd_text_2.right = new FormAttachment(100, -10);
		text_2.setLayoutData(fd_text_2);

		TabItem tbtmBumper = new TabItem(tabFolder, SWT.NONE);
		tbtmBumper.setText("Bumper");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmBumper.setControl(composite_1);
		composite_1.setLayout(new FormLayout());

		Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(0, 10);
		fd_lblNewLabel_1.left = new FormAttachment(0, 10);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("Bumper Ticket(s)");

		text_3 = new Text(composite_1, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		FormData fd_text_3 = new FormData();
		fd_text_3.bottom = new FormAttachment(100, -10);
		fd_text_3.left = new FormAttachment(0, 10);
		fd_text_3.right = new FormAttachment(100, -10);
		text_3.setLayoutData(fd_text_3);

		combo = new Combo(composite_1, SWT.READ_ONLY);
		fd_text_3.top = new FormAttachment(combo, 6);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0, 9);
		fd_combo.right = new FormAttachment(100, -10);
		combo.setLayoutData(fd_combo);
		combo.setItems(GetDummyData.getBumperNames());
		combo.setText(modelProvider.getConfigBumper());
		Label lblSelectBumper = new Label(composite_1, SWT.NONE);
		FormData fd_lblSelectBumper = new FormData();
		fd_lblSelectBumper.bottom = new FormAttachment(lblNewLabel_1, 0,
				SWT.BOTTOM);
		fd_lblSelectBumper.right = new FormAttachment(combo, -6);
		lblSelectBumper.setLayoutData(fd_lblSelectBumper);
		lblSelectBumper.setText("Select Bumper");

		TabItem tbtmBoth = new TabItem(tabFolder, SWT.NONE);
		tbtmBoth.setText("Both");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmBoth.setControl(composite_2);
		composite_2.setLayout(new FormLayout());

		Label lblMonthlyTickets = new Label(composite_2, SWT.NONE);
		lblMonthlyTickets.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblMonthlyTickets = new FormData();
		fd_lblMonthlyTickets.left = new FormAttachment(0, 10);
		fd_lblMonthlyTickets.top = new FormAttachment(0, 10);
		lblMonthlyTickets.setLayoutData(fd_lblMonthlyTickets);
		lblMonthlyTickets.setText("Monthly Ticket(s)");

		Label label_1 = new Label(composite_2, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(lblMonthlyTickets, 0, SWT.LEFT);
		fd_label_1.right = new FormAttachment(100, -10);
		label_1.setLayoutData(fd_label_1);

		Label lblBumper = new Label(composite_2, SWT.NONE);
		lblBumper.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		FormData fd_lblBumper = new FormData();
		fd_lblBumper.top = new FormAttachment(label_1, 6);
		fd_lblBumper.left = new FormAttachment(lblMonthlyTickets, 0, SWT.LEFT);
		lblBumper.setLayoutData(fd_lblBumper);
		lblBumper.setText("Bumper Ticket(s)");

		text_5 = new Text(composite_2, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		fd_label_1.top = new FormAttachment(text_5, 6);
		FormData fd_text_5 = new FormData();
		fd_text_5.bottom = new FormAttachment(100, -145);
		fd_text_5.top = new FormAttachment(lblMonthlyTickets, 6);
		
		fd_text_5.left = new FormAttachment(0, 10);
		fd_text_5.right = new FormAttachment(100, -10);
		text_5.setLayoutData(fd_text_5);

		text_7 = new Text(composite_2, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		FormData fd_text_7 = new FormData();
		fd_text_7.top = new FormAttachment(lblBumper, 13);
		fd_text_7.bottom = new FormAttachment(100, -10);
		fd_text_7.left = new FormAttachment(0, 10);
		fd_text_7.right = new FormAttachment(100, -10);
		text_7.setLayoutData(fd_text_7);

		combo_1 = new Combo(composite_2, SWT.READ_ONLY);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.top = new FormAttachment(label_1, 6);
		fd_combo_1.right = new FormAttachment(label_1, 0, SWT.RIGHT);
		combo_1.setLayoutData(fd_combo_1);
		combo_1.setItems(GetDummyData.getBumperNames());
		Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.top = new FormAttachment(lblBumper, 2, SWT.TOP);
		fd_lblNewLabel_2.right = new FormAttachment(combo_1, -6);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setText("Select Bumper");
		combo_1.setText(modelProvider.getConfigBumper());
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setToolTipText("Save");
		btnNewButton.setImage(ResourceManager
				.getPluginImage("com.intut.luckylottery.cms",
						"icons/appIcons/database-b-w.png"));
		fd_tabFolder.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (Util.isStringNullOrEmpty(modelProvider.getName())) {
					boolean result = MessageDialog.openConfirm(shell,
							"Are you sure?",
							"Are sure you want to skip name of a customer?");
					if (!result)
						return;
				}
				modelProvider.logMessage();
				if (!modelProvider.saveCustomer()) {
					MessageDialog.openError(shell, "Error",
							modelProvider.getErrorMessage());
					if (!modelProvider.resetData())
						MessageDialog.openError(shell, "Error",
								modelProvider.getErrorMessage());
				} else {
					MessageDialog.openInformation(shell, "Success",
							"All data has been saved successfully");
				}
				if (!modelProvider.resetData())
					MessageDialog.openError(shell, "Error",
							modelProvider.getErrorMessage());

			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(100, -10);

		Label lblNewLabel = new Label(grpCustomerInformation, SWT.NONE);
		lblNewLabel
				.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		fd_lblEmail.top = new FormAttachment(lblNewLabel, 33);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		fd_lblNewLabel.top = new FormAttachment(dateTime, 6);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Starting Serial Number");

		serialNumberText = new Text(grpCustomerInformation, SWT.BORDER
				| SWT.READ_ONLY | SWT.MULTI);
		FormData fd_serialNumberText = new FormData();
		fd_serialNumberText.right = new FormAttachment(100, -10);
		fd_serialNumberText.left = new FormAttachment(0, 10);
		fd_serialNumberText.top = new FormAttachment(lblNewLabel, 6);
		serialNumberText.setLayoutData(fd_serialNumberText);
		fd_btnNewButton.right = new FormAttachment(100, -10);
		btnNewButton.setLayoutData(fd_btnNewButton);

		btnSendSms = new Button(shell, SWT.NONE);
		btnSendSms.setToolTipText("Send Sms");
		btnSendSms.setImage(ResourceManager
				.getPluginImage("com.intut.luckylottery.cms",
						"icons/appIcons/send-sms-b-w.png"));
		btnSendSms.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (Util.isStringNullOrEmpty(modelProvider.getName())) {
					boolean result = MessageDialog.openConfirm(shell,
							"Are you sure?",
							"Are sure you want to skip name of a customer?");
					if (!result)
						return;
				}
				if (modelProvider.getCustomMessage().length()>158) {
					boolean result = MessageDialog.openConfirm(shell,
							"Are you sure?",
							"Message length exceeded, Still want to send this message?");
					if (!result)
						return;
				}
				if (modelProvider.getPhoneNumber().length()<6) {
					 MessageDialog.openError(shell,"Error",
							"Phone Number lenght must be greater than 5");
					
						return;
				}
				modelProvider.logMessage();
				if (!modelProvider.sendAndDisplayMessage()) {
					MessageDialog.openError(shell, "Error",
							modelProvider.getErrorMessage());
					if (!modelProvider.resetData())
						MessageDialog.openError(shell, "Error",
								modelProvider.getErrorMessage());
				}
				if (!modelProvider.resetData())
					MessageDialog.openError(shell, "Error",
							modelProvider.getErrorMessage());
			}
		});
		FormData fd_btnSendSms = new FormData();
		fd_btnSendSms.top = new FormAttachment(btnNewButton, 0, SWT.TOP);
		fd_btnSendSms.right = new FormAttachment(btnNewButton, -6);
		btnSendSms.setLayoutData(fd_btnSendSms);

		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_tabFolder.bottom = new FormAttachment(label_3, -6);
		fd_grpCustomerInformation.bottom = new FormAttachment(label_3, -6);
		FormData fd_label_3 = new FormData();
		fd_label_3.left = new FormAttachment(0, 10);
		fd_label_3.right = new FormAttachment(100, -10);
		fd_label_3.top = new FormAttachment(btnNewButton, -12, SWT.TOP);
		fd_label_3.bottom = new FormAttachment(btnNewButton, -10);
		label_3.setLayoutData(fd_label_3);

		Button btnReset = new Button(shell, SWT.NONE);
		btnReset.setToolTipText("Reset");
		btnReset.setImage(ResourceManager.getPluginImage(
				"com.intut.luckylottery.cms", "icons/appIcons/reset-b-w.png"));
		btnReset.setSelection(true);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!modelProvider.resetData()) {
					MessageDialog.openError(shell, "Error in reseting Data",
							modelProvider.getErrorMessage());
				}
			}
		});
		FormData fd_btnReset = new FormData();
		fd_btnReset.bottom = new FormAttachment(btnNewButton, 0, SWT.BOTTOM);
		fd_btnReset.right = new FormAttachment(btnSendSms, -6);
		btnReset.setLayoutData(fd_btnReset);
		
		Group grpMessage = new Group(shell, SWT.NONE);
		grpMessage.setText("Message");
		grpMessage.setLayout(new FormLayout());
		FormData fd_grpMessage = new FormData();
		fd_grpMessage.bottom = new FormAttachment(tabFolder, -6);
		fd_grpMessage.top = new FormAttachment(grpCustomerInformation, 0, SWT.TOP);
		fd_grpMessage.right = new FormAttachment(tabFolder, 0, SWT.RIGHT);
		fd_grpMessage.left = new FormAttachment(grpCustomerInformation, 6);
		grpMessage.setLayoutData(fd_grpMessage);
		
		text_8 = new Text(grpMessage, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_text_8 = new FormData();
		fd_text_8.bottom = new FormAttachment(100, -10);
		fd_text_8.right = new FormAttachment(100, -10);
		fd_text_8.top = new FormAttachment(0, 25);
		fd_text_8.left = new FormAttachment(0, 7);
		text_8.setLayoutData(fd_text_8);
		
		btnCheckButton = new Button(grpMessage, SWT.CHECK);
		FormData fd_btnCheckButton = new FormData();
		fd_btnCheckButton.bottom = new FormAttachment(text_8, -6);
		fd_btnCheckButton.left = new FormAttachment(text_8, 0, SWT.LEFT);
		btnCheckButton.setLayoutData(fd_btnCheckButton);
		btnCheckButton.setText("Default Message");
		
		lblLength = new Label(grpMessage, SWT.RIGHT);
		FormData fd_lblLength = new FormData();
		fd_lblLength.top = new FormAttachment(btnCheckButton, 0, SWT.TOP);
		fd_lblLength.left = new FormAttachment(btnCheckButton, 6);
		fd_lblLength.right = new FormAttachment(100, -10);
		lblLength.setLayoutData(fd_lblLength);
		lblLength.setText("Length");
		m_bindingContext = initDataBindings();

	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables.observeText(text, SWT.Modify);
		IObservableValue modelProviderNameObserveValue = BeansObservables.observeValue(modelProvider, "name");
		bindingContext.bindValue(textObserveTextObserveWidget, modelProviderNameObserveValue, null, null);
		//
		IObservableValue text_1ObserveTextObserveWidget = SWTObservables.observeText(text_1, SWT.Modify);
		IObservableValue modelProviderPhoneNumberObserveValue = BeansObservables.observeValue(modelProvider, "phoneNumber");
		bindingContext.bindValue(text_1ObserveTextObserveWidget, modelProviderPhoneNumberObserveValue, null, null);
		//
		IObservableValue dateTimeObserveSelectionObserveWidget = SWTObservables.observeSelection(dateTime);
		IObservableValue modelProviderDateObserveValue = BeansObservables.observeValue(modelProvider, "date");
		bindingContext.bindValue(dateTimeObserveSelectionObserveWidget, modelProviderDateObserveValue, null, null);
		//
		IObservableValue text_4ObserveTextObserveWidget = SWTObservables.observeText(text_4, SWT.Modify);
		IObservableValue modelProviderEmailIdObserveValue = BeansObservables.observeValue(modelProvider, "emailId");
		bindingContext.bindValue(text_4ObserveTextObserveWidget, modelProviderEmailIdObserveValue, null, null);
		//
		IObservableValue text_6ObserveTextObserveWidget = SWTObservables.observeText(text_6, SWT.Modify);
		IObservableValue modelProviderAddressObserveValue = BeansObservables.observeValue(modelProvider, "address");
		bindingContext.bindValue(text_6ObserveTextObserveWidget, modelProviderAddressObserveValue, null, null);
		//
		IObservableValue serialNumberTextObserveTextObserveWidget = SWTObservables.observeText(serialNumberText, SWT.Modify);
		IObservableValue modelProviderSerialNumberObserveValue = BeansObservables.observeValue(modelProvider, "serialNumber");
		bindingContext.bindValue(serialNumberTextObserveTextObserveWidget, modelProviderSerialNumberObserveValue, null, null);
		//
		IObservableValue text_2ObserveTextObserveWidget = SWTObservables.observeText(text_2, SWT.Modify);
		IObservableValue modelProviderMonthlyTicketsObserveValue = BeansObservables.observeValue(modelProvider, "monthlyTickets");
		bindingContext.bindValue(text_2ObserveTextObserveWidget, modelProviderMonthlyTicketsObserveValue, null, null);
		//
		IObservableValue text_3ObserveTextObserveWidget = SWTObservables.observeText(text_3, SWT.Modify);
		IObservableValue modelProviderBumperTicketsObserveValue = BeansObservables.observeValue(modelProvider, "bumperTickets");
		bindingContext.bindValue(text_3ObserveTextObserveWidget, modelProviderBumperTicketsObserveValue, null, null);
		//
		IObservableValue text_5ObserveTextObserveWidget = SWTObservables.observeText(text_5, SWT.Modify);
		bindingContext.bindValue(text_5ObserveTextObserveWidget, modelProviderMonthlyTicketsObserveValue, null, null);
		//
		IObservableValue text_7ObserveTextObserveWidget = SWTObservables.observeText(text_7, SWT.Modify);
		bindingContext.bindValue(text_7ObserveTextObserveWidget, modelProviderBumperTicketsObserveValue, null, null);
		//
		IObservableValue btnSendSmsObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnSendSms);
		IObservableValue modelProviderSendSMSButtonObserveValue = BeansObservables.observeValue(modelProvider, "sendSMSButton");
		bindingContext.bindValue(btnSendSmsObserveEnabledObserveWidget, modelProviderSendSMSButtonObserveValue, null, null);
		//
		IObservableValue btnNewButtonObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnNewButton);
		IObservableValue modelProviderSaveToDatabaseObserveValue = BeansObservables.observeValue(modelProvider, "saveToDatabase");
		bindingContext.bindValue(btnNewButtonObserveEnabledObserveWidget, modelProviderSaveToDatabaseObserveValue, null, null);
		//
		IObservableValue combo_1ObserveSelectionObserveWidget = SWTObservables.observeSelection(combo_1);
		IObservableValue modelProviderSelectedBumperObserveValue = BeansObservables.observeValue(modelProvider, "selectedBumper");
		bindingContext.bindValue(combo_1ObserveSelectionObserveWidget, modelProviderSelectedBumperObserveValue, null, null);
		//
		IObservableValue comboObserveSelectionObserveWidget = SWTObservables.observeSelection(combo);
		bindingContext.bindValue(comboObserveSelectionObserveWidget, modelProviderSelectedBumperObserveValue, null, null);
		//
		IObservableValue btnCheckButtonObserveSelectionObserveWidget = SWTObservables.observeSelection(btnCheckButton);
		IObservableValue modelProviderCustomSelectedObserveValue = BeansObservables.observeValue(modelProvider, "customSelected");
		bindingContext.bindValue(btnCheckButtonObserveSelectionObserveWidget, modelProviderCustomSelectedObserveValue, null, null);
		//
		IObservableValue lblLengthObserveTextObserveWidget = SWTObservables.observeText(lblLength);
		IObservableValue modelProviderMessageLengthObserveValue = BeansObservables.observeValue(modelProvider, "messageLength");
		bindingContext.bindValue(lblLengthObserveTextObserveWidget, modelProviderMessageLengthObserveValue, null, null);
		//
		IObservableValue text_8ObserveTextObserveWidget = SWTObservables.observeText(text_8, SWT.Modify);
		IObservableValue modelProviderCustomMessageObserveValue = BeansObservables.observeValue(modelProvider, "customMessage");
		bindingContext.bindValue(text_8ObserveTextObserveWidget, modelProviderCustomMessageObserveValue, null, null);
		//
		return bindingContext;
	}
}
