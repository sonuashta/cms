package com.intut.luckylottery.cms.dialogs;

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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

import com.intut.luckylottery.cms.modelProviders.ManualEntrydialogModelProvider;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;

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
	private List singleMonthlyList;
	private List singleBumperList;
	private List list_2;
	private List list_3;
	private Text serialNumberText;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ManualEntryDialog(Shell parent) {
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
		shell = new Shell(getParent());
		shell.setSize(700, 550);
		shell.setText("Add Customer");
		shell.setLayout(new FormLayout());

		Group grpCustomerInformation = new Group(shell, SWT.NONE);
		grpCustomerInformation.setText("Customer Information");
		grpCustomerInformation.setLayout(new FormLayout());
		FormData fd_grpCustomerInformation = new FormData();
		fd_grpCustomerInformation.left = new FormAttachment(0, 10);
		fd_grpCustomerInformation.top = new FormAttachment(0, 10);
		grpCustomerInformation.setLayoutData(fd_grpCustomerInformation);

		Label lblName = new Label(grpCustomerInformation, SWT.NONE);
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
		FormData fd_lblNumber = new FormData();
		fd_lblNumber.top = new FormAttachment(text, 6);
		fd_lblNumber.left = new FormAttachment(0, 10);
		lblNumber.setLayoutData(fd_lblNumber);
		lblNumber.setText("Number");

		text_1 = new Text(grpCustomerInformation, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.left = new FormAttachment(0, 10);
		fd_text_1.top = new FormAttachment(lblNumber, 6);
		text_1.setLayoutData(fd_text_1);

		Label lblDate = new Label(grpCustomerInformation, SWT.NONE);
		FormData fd_lblDate = new FormData();
		fd_lblDate.left = new FormAttachment(0, 10);
		fd_lblDate.top = new FormAttachment(lblNumber, 35);
		lblDate.setLayoutData(fd_lblDate);
		lblDate.setText("Date");

		dateTime = new DateTime(grpCustomerInformation, SWT.BORDER);
		FormData fd_dateTime = new FormData();
		fd_dateTime.left = new FormAttachment(0, 10);
		fd_dateTime.top = new FormAttachment(lblDate, 6);
		dateTime.setLayoutData(fd_dateTime);

		Label lblEmail = new Label(grpCustomerInformation, SWT.NONE);
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
		fd_grpCustomerInformation.bottom = new FormAttachment(tabFolder, 0,
				SWT.BOTTOM);
		fd_grpCustomerInformation.right = new FormAttachment(tabFolder, -6);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.left = new FormAttachment(0, 191);
		fd_tabFolder.top = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);

		TabItem tbtmMonthly = new TabItem(tabFolder, SWT.NONE);

		tbtmMonthly.setText("Monthly");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmMonthly.setControl(composite);
		composite.setLayout(new FormLayout());

		singleMonthlyList = new List(composite, SWT.BORDER);
		FormData fd_singleMonthlyList = new FormData();
		fd_singleMonthlyList.top = new FormAttachment(0, 10);
		fd_singleMonthlyList.left = new FormAttachment(0, 10);
		singleMonthlyList.setLayoutData(fd_singleMonthlyList);

		Button button = new Button(composite, SWT.NONE);
		fd_singleMonthlyList.bottom = new FormAttachment(100, -41);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.openAddEditDialog(false, "", "", "", false, 0);
			}
		});
		button.setText("Add Ticket");
		FormData fd_button = new FormData();
		fd_button.top = new FormAttachment(singleMonthlyList, 6);

		button.setLayoutData(fd_button);

		Button button_1 = new Button(composite, SWT.NONE);
		fd_button.right = new FormAttachment(button_1, -6);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleMonthlyList.getSelectionIndex();
				if (selectionIndex >= 0) {
					String s[] = modelProvider.getMonthlyList()
							.get(selectionIndex).split(";");
					modelProvider.openAddEditDialog(false, s[0], s[1], "",
							true, selectionIndex);
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a monthly list element which you want to edit");
				}
			}
		});
		button_1.setText("Edit Ticket");
		FormData fd_button_1 = new FormData();

		button_1.setLayoutData(fd_button_1);

		Button button_2 = new Button(composite, SWT.NONE);
		fd_singleMonthlyList.right = new FormAttachment(button_2, 0, SWT.RIGHT);
		fd_button_1.top = new FormAttachment(button_2, 0, SWT.TOP);
		fd_button_1.right = new FormAttachment(button_2, -6);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleMonthlyList.getSelectionIndex();
				if (selectionIndex >= 0) {
					modelProvider.getMonthlyList().remove(selectionIndex);
					modelProvider.setMonthlyWritableList(modelProvider
							.getMonthlyList());
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a monthly list element which you want to delete");
				}
			}
		});
		button_2.setText("Remove Ticket");
		FormData fd_button_2 = new FormData();
		fd_button_2.bottom = new FormAttachment(100, -10);
		fd_button_2.right = new FormAttachment(100, -10);
		button_2.setLayoutData(fd_button_2);

		TabItem tbtmBumper = new TabItem(tabFolder, SWT.NONE);
		tbtmBumper.setText("Bumper");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmBumper.setControl(composite_1);
		composite_1.setLayout(new FormLayout());

		singleBumperList = new List(composite_1, SWT.BORDER);
		FormData fd_singleBumperList = new FormData();
		fd_singleBumperList.top = new FormAttachment(0, 10);
		fd_singleBumperList.left = new FormAttachment(0, 10);
		singleBumperList.setLayoutData(fd_singleBumperList);

		Button button_3 = new Button(composite_1, SWT.NONE);
		fd_singleBumperList.bottom = new FormAttachment(button_3, -6);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.openAddEditDialog(true, "", "", "", false, 0);
			}
		});
		button_3.setText("Add Ticket");
		FormData fd_button_3 = new FormData();
		fd_button_3.left = new FormAttachment(0, 223);
		button_3.setLayoutData(fd_button_3);

		Button button_4 = new Button(composite_1, SWT.NONE);
		fd_button_3.top = new FormAttachment(button_4, 0, SWT.TOP);
		fd_button_3.right = new FormAttachment(button_4, -6);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleBumperList.getSelectionIndex();
				if (selectionIndex >= 0) {
					String s[] = modelProvider.getBumperList()
							.get(selectionIndex).split(";");
					modelProvider.openAddEditDialog(true, s[0], s[1], s[2],
							true, selectionIndex);
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a bumper list element which you want to edit");
				}
			}
		});
		button_4.setText("Edit Ticket");
		FormData fd_button_4 = new FormData();
		button_4.setLayoutData(fd_button_4);

		Button button_5 = new Button(composite_1, SWT.NONE);
		fd_singleBumperList.right = new FormAttachment(button_5, 0, SWT.RIGHT);
		fd_button_4.left = new FormAttachment(button_5, -81, SWT.LEFT);
		fd_button_4.top = new FormAttachment(button_5, 0, SWT.TOP);
		fd_button_4.right = new FormAttachment(button_5, -6);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleBumperList.getSelectionIndex();
				if (selectionIndex >= 0) {
					modelProvider.getBumperList().remove(selectionIndex);
					modelProvider.setBumperWritableList(modelProvider
							.getBumperList());
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a bumper list element which you want to delete");
				}
			}
		});
		button_5.setText("Remove Ticket");
		FormData fd_button_5 = new FormData();
		fd_button_5.bottom = new FormAttachment(100, -10);
		fd_button_5.right = new FormAttachment(100, -10);
		button_5.setLayoutData(fd_button_5);

		TabItem tbtmBoth = new TabItem(tabFolder, SWT.NONE);
		tbtmBoth.setText("Both");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmBoth.setControl(composite_2);
		composite_2.setLayout(new FormLayout());

		list_2 = new List(composite_2, SWT.BORDER);
		FormData fd_list_2 = new FormData();
		fd_list_2.bottom = new FormAttachment(0, 178);
		fd_list_2.top = new FormAttachment(0, 40);
		fd_list_2.left = new FormAttachment(0, 10);
		list_2.setLayoutData(fd_list_2);

		Button button_6 = new Button(composite_2, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.openAddEditDialog(false, "", "", "", false, 0);
			}
		});
		button_6.setText("Add Ticket");
		FormData fd_button_6 = new FormData();
		fd_button_6.top = new FormAttachment(0, 184);
		button_6.setLayoutData(fd_button_6);

		Button button_7 = new Button(composite_2, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleMonthlyList.getSelectionIndex();
				if (selectionIndex >= 0) {
					String s[] = modelProvider.getMonthlyList()
							.get(selectionIndex).split(";");
					modelProvider.openAddEditDialog(false, s[0], s[1], "",
							true, selectionIndex);
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a monthly list element which you want to edit");
				}
			}
		});
		fd_button_6.right = new FormAttachment(button_7, -6);
		button_7.setText("Edit Ticket");
		FormData fd_button_7 = new FormData();
		fd_button_7.top = new FormAttachment(0, 184);

		button_7.setLayoutData(fd_button_7);

		Button button_8 = new Button(composite_2, SWT.NONE);
		fd_list_2.right = new FormAttachment(button_8, 0, SWT.RIGHT);
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleMonthlyList.getSelectionIndex();
				if (selectionIndex >= 0) {
					modelProvider.getMonthlyList().remove(selectionIndex);
					modelProvider.setMonthlyWritableList(modelProvider
							.getMonthlyList());
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a monthly list element which you want to delete");
				}
			}
		});
		fd_button_7.right = new FormAttachment(button_8, -6);
		button_8.setText("Remove Ticket");
		FormData fd_button_8 = new FormData();
		fd_button_8.top = new FormAttachment(0, 184);
		fd_button_8.right = new FormAttachment(100, -10);
		button_8.setLayoutData(fd_button_8);

		Label lblMonthlyTickets = new Label(composite_2, SWT.NONE);
		FormData fd_lblMonthlyTickets = new FormData();
		fd_lblMonthlyTickets.left = new FormAttachment(0, 10);
		fd_lblMonthlyTickets.top = new FormAttachment(0, 10);
		lblMonthlyTickets.setLayoutData(fd_lblMonthlyTickets);
		lblMonthlyTickets.setText("Monthly Tickets");

		Label label = new Label(composite_2, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(lblMonthlyTickets, 6);
		fd_label.bottom = new FormAttachment(lblMonthlyTickets, 8, SWT.BOTTOM);
		fd_label.right = new FormAttachment(list_2, 0, SWT.RIGHT);
		fd_label.left = new FormAttachment(0, 10);
		label.setLayoutData(fd_label);

		Label label_1 = new Label(composite_2, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(0, 10);
		fd_label_1.right = new FormAttachment(100, -10);
		fd_label_1.bottom = new FormAttachment(button_6, 8, SWT.BOTTOM);
		fd_label_1.top = new FormAttachment(button_6, 6);
		label_1.setLayoutData(fd_label_1);

		Label lblBumper = new Label(composite_2, SWT.NONE);
		FormData fd_lblBumper = new FormData();
		fd_lblBumper.left = new FormAttachment(0, 10);
		fd_lblBumper.top = new FormAttachment(label_1, 6);
		lblBumper.setLayoutData(fd_lblBumper);
		lblBumper.setText("Bumper");

		Label label_2 = new Label(composite_2, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_2 = new FormData();
		fd_label_2.left = new FormAttachment(0, 10);
		fd_label_2.right = new FormAttachment(100, -10);
		fd_label_2.bottom = new FormAttachment(lblBumper, 8, SWT.BOTTOM);
		fd_label_2.top = new FormAttachment(lblBumper, 6);
		label_2.setLayoutData(fd_label_2);

		list_3 = new List(composite_2, SWT.BORDER);
		FormData fd_list_3 = new FormData();
		fd_list_3.top = new FormAttachment(label_2, 9);
		fd_list_3.right = new FormAttachment(0, 475);
		fd_list_3.left = new FormAttachment(0, 10);
		list_3.setLayoutData(fd_list_3);

		Button btnRemoveButton = new Button(composite_2, SWT.NONE);
		fd_list_3.bottom = new FormAttachment(btnRemoveButton, -6);
		btnRemoveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleBumperList.getSelectionIndex();
				if (selectionIndex >= 0) {
					modelProvider.getBumperList().remove(selectionIndex);
					modelProvider.setBumperWritableList(modelProvider
							.getBumperList());
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a bumper list element which you want to delete");
				}
			}
		});
		FormData fd_btnRemoveButton = new FormData();
		fd_btnRemoveButton.bottom = new FormAttachment(100, -10);
		fd_btnRemoveButton.right = new FormAttachment(button_8, 0, SWT.RIGHT);
		btnRemoveButton.setLayoutData(fd_btnRemoveButton);
		btnRemoveButton.setText("Remove Ticket");

		Button btnEditTicket = new Button(composite_2, SWT.NONE);
		btnEditTicket.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = singleBumperList.getSelectionIndex();
				if (selectionIndex >= 0) {
					String s[] = modelProvider.getBumperList()
							.get(selectionIndex).split(";");
					modelProvider.openAddEditDialog(true, s[0], s[1], s[2],
							true, selectionIndex);
				} else {
					MessageDialog
							.openInformation(shell, "Information",
									"Please select a bumper list element which you want to edit");
				}
			}
		});
		FormData fd_btnEditTicket = new FormData();
		fd_btnEditTicket.top = new FormAttachment(btnRemoveButton, 0, SWT.TOP);
		fd_btnEditTicket.right = new FormAttachment(button_7, 0, SWT.RIGHT);
		btnEditTicket.setLayoutData(fd_btnEditTicket);
		btnEditTicket.setText("Edit Ticket");

		Button btnAddTicket = new Button(composite_2, SWT.NONE);
		btnAddTicket.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.openAddEditDialog(true, "", "", "", false, 0);
			}
		});
		FormData fd_btnAddTicket = new FormData();
		fd_btnAddTicket.top = new FormAttachment(btnRemoveButton, 0, SWT.TOP);
		fd_btnAddTicket.right = new FormAttachment(button_6, 0, SWT.RIGHT);
		btnAddTicket.setLayoutData(fd_btnAddTicket);
		btnAddTicket.setText("Add Ticket");

		Button btnNewButton = new Button(shell, SWT.NONE);
		fd_tabFolder.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.saveCustomer();
				MessageDialog.openInformation(shell, "Success",
						"Data saved in db");
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(100, -10);

		Label lblNewLabel = new Label(grpCustomerInformation, SWT.NONE);
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
		btnNewButton.setText("Save");

		Button btnSendSms = new Button(shell, SWT.NONE);
		btnSendSms.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.sendAndDisplayMessage();
			}
		});
		FormData fd_btnSendSms = new FormData();
		fd_btnSendSms.top = new FormAttachment(btnNewButton, 0, SWT.TOP);
		fd_btnSendSms.right = new FormAttachment(btnNewButton, -6);
		btnSendSms.setLayoutData(fd_btnSendSms);
		btnSendSms.setText("Send Sms");

		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_tabFolder.bottom = new FormAttachment(label_3, -6);
		FormData fd_label_3 = new FormData();
		fd_label_3.left = new FormAttachment(0, 10);
		fd_label_3.right = new FormAttachment(100, -10);
		fd_label_3.top = new FormAttachment(btnNewButton, -12, SWT.TOP);
		fd_label_3.bottom = new FormAttachment(btnNewButton, -10);
		label_3.setLayoutData(fd_label_3);
		m_bindingContext = initDataBindings();

	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables
				.observeText(text, SWT.Modify);
		IObservableValue modelProviderNameObserveValue = BeansObservables
				.observeValue(modelProvider, "name");
		bindingContext.bindValue(textObserveTextObserveWidget,
				modelProviderNameObserveValue, null, null);
		//
		IObservableValue text_1ObserveTextObserveWidget = SWTObservables
				.observeText(text_1, SWT.Modify);
		IObservableValue modelProviderPhoneNumberObserveValue = BeansObservables
				.observeValue(modelProvider, "phoneNumber");
		bindingContext.bindValue(text_1ObserveTextObserveWidget,
				modelProviderPhoneNumberObserveValue, null, null);
		//
		IObservableValue dateTimeObserveSelectionObserveWidget = SWTObservables
				.observeSelection(dateTime);
		IObservableValue modelProviderDateObserveValue = BeansObservables
				.observeValue(modelProvider, "date");
		bindingContext.bindValue(dateTimeObserveSelectionObserveWidget,
				modelProviderDateObserveValue, null, null);
		//
		IObservableValue text_4ObserveTextObserveWidget = SWTObservables
				.observeText(text_4, SWT.Modify);
		IObservableValue modelProviderEmailIdObserveValue = BeansObservables
				.observeValue(modelProvider, "emailId");
		bindingContext.bindValue(text_4ObserveTextObserveWidget,
				modelProviderEmailIdObserveValue, null, null);
		//
		IObservableValue text_6ObserveTextObserveWidget = SWTObservables
				.observeText(text_6, SWT.Modify);
		IObservableValue modelProviderAddressObserveValue = BeansObservables
				.observeValue(modelProvider, "address");
		bindingContext.bindValue(text_6ObserveTextObserveWidget,
				modelProviderAddressObserveValue, null, null);
		//
		IObservableValue serialNumberTextObserveTextObserveWidget = SWTObservables
				.observeText(serialNumberText, SWT.Modify);
		IObservableValue modelProviderSerialNumberObserveValue = BeansObservables
				.observeValue(modelProvider, "serialNumber");
		bindingContext.bindValue(serialNumberTextObserveTextObserveWidget,
				modelProviderSerialNumberObserveValue, null, null);
		//
		IObservableList singleMonthlyListObserveItemsObserveListWidget = SWTObservables
				.observeItems(singleMonthlyList);
		bindingContext.bindList(singleMonthlyListObserveItemsObserveListWidget,
				modelProvider.getMonthlyWritableList(), null, null);
		//
		IObservableList list_1ObserveItemsObserveListWidget = SWTObservables
				.observeItems(singleBumperList);
		bindingContext.bindList(list_1ObserveItemsObserveListWidget,
				modelProvider.getBumperWritableList(), null, null);
		//
		IObservableList list_2ObserveItemsObserveListWidget = SWTObservables
				.observeItems(list_2);
		bindingContext.bindList(list_2ObserveItemsObserveListWidget,
				modelProvider.getMonthlyWritableList(), null, null);
		//
		IObservableList list_3ObserveItemsObserveListWidget = SWTObservables
				.observeItems(list_3);
		bindingContext.bindList(list_3ObserveItemsObserveListWidget,
				modelProvider.getBumperWritableList(), null, null);
		//
		return bindingContext;
	}
}
