package com.intut.luckylottery.cms.dialogs;

import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.SearchResult;
import com.intut.luckylottery.cms.modelProviders.ReportDialogModelProvider;
import com.intut.luckylottery.cms.util.Util;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;

public class ReportDialog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private ReportDialogModelProvider modelProvider;
	private DateTime dateTime;
	private DateTime dateTime_1;
	private Button btnSen;
	private Button btnRadioButton;
	private Button btnBumper;
	private Button btnBoth;
	private Combo combo;
	private Label lblName;
	private Text text_4;
	private Label label;
	private Group grpTicketType;
	private Group grpDate;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ReportDialog(Shell parent) {
		super(parent);
		setText("Search Customers");
		modelProvider = new ReportDialogModelProvider();
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
		shell.setSize(600, 463);
		shell.setBounds(Util.setBouunds(600, 450));
		shell.setText(getText());
		shell.setLayout(new FormLayout());

		Label lblPhoneNumber = new Label(shell, SWT.NONE);
		lblPhoneNumber.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		FormData fd_lblPhoneNumber = new FormData();
		fd_lblPhoneNumber.left = new FormAttachment(0, 10);
		fd_lblPhoneNumber.right = new FormAttachment(100, -318);
		lblPhoneNumber.setLayoutData(fd_lblPhoneNumber);
		lblPhoneNumber.setText("Phone Number");

		text = new Text(shell, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblPhoneNumber, 6);
		fd_text.left = new FormAttachment(0, 10);
		text.setLayoutData(fd_text);

		Label lblTicketNumber = new Label(shell, SWT.NONE);
		lblTicketNumber.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		FormData fd_lblTicketNumber = new FormData();
		fd_lblTicketNumber.top = new FormAttachment(text, 6);
		fd_lblTicketNumber.left = new FormAttachment(0, 10);
		lblTicketNumber.setLayoutData(fd_lblTicketNumber);
		lblTicketNumber.setText("Ticket Number");

		text_1 = new Text(shell, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.left = new FormAttachment(0, 10);
		fd_text_1.top = new FormAttachment(lblTicketNumber, 6);
		text_1.setLayoutData(fd_text_1);

		Label lblAddress = new Label(shell, SWT.NONE);
		lblAddress.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_lblAddress = new FormData();
		fd_lblAddress.top = new FormAttachment(text_1, 6);
		fd_lblAddress.left = new FormAttachment(0, 10);
		lblAddress.setLayoutData(fd_lblAddress);
		lblAddress.setText("Address");

		text_2 = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		fd_text.right = new FormAttachment(text_2, 0, SWT.RIGHT);
		FormData fd_text_2 = new FormData();
		fd_text_2.left = new FormAttachment(0, 10);
		fd_text_2.right = new FormAttachment(100, -10);
		fd_text_2.top = new FormAttachment(lblAddress, 6);
		text_2.setLayoutData(fd_text_2);

		Label lblEmailId = new Label(shell, SWT.NONE);
		lblEmailId.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		fd_text_2.bottom = new FormAttachment(lblEmailId, -6);
		FormData fd_lblEmailId = new FormData();
		fd_lblEmailId.right = new FormAttachment(lblAddress, 0, SWT.RIGHT);
		lblEmailId.setLayoutData(fd_lblEmailId);
		lblEmailId.setText("Email id");

		text_3 = new Text(shell, SWT.BORDER);
		fd_lblEmailId.bottom = new FormAttachment(text_3, -6);
		FormData fd_text_3 = new FormData();
		fd_text_3.right = new FormAttachment(100, -10);
		fd_text_3.left = new FormAttachment(0, 10);
		text_3.setLayoutData(fd_text_3);
		Button btnSearch = new Button(shell, SWT.NONE);
		btnSearch.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/system-search-4.png"));
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					SearchResult searchResult = modelProvider.searchResults();
					if (searchResult.customers.size() == 0)
						MessageDialog.openInformation(shell, "No Results",
								"No Result found!");
					else {
						ReportResultsDialog dialog = new ReportResultsDialog(
								shell, searchResult);
						dialog.open();
					}
				} catch (Exception e1) {
					MessageDialog.openError(shell, "Error", e1.getMessage());
				}
			}
		});
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.bottom = new FormAttachment(100, -10);
		fd_btnSearch.right = new FormAttachment(100, -10);
		btnSearch.setLayoutData(fd_btnSearch);

		btnSen = new Button(shell, SWT.CHECK);
		btnSen.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		FormData fd_btnSen = new FormData();
		fd_btnSen.left = new FormAttachment(0, 10);
		btnSen.setLayoutData(fd_btnSen);
		btnSen.setText("Sent Messages");

		lblName = new Label(shell, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		fd_text_3.bottom = new FormAttachment(lblName, -6);
		FormData fd_lblName = new FormData();
		fd_lblName.left = new FormAttachment(0, 10);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name");

		text_4 = new Text(shell, SWT.BORDER);
		fd_lblName.bottom = new FormAttachment(text_4, -6);
		FormData fd_text_4 = new FormData();
		fd_text_4.right = new FormAttachment(100, -10);
		fd_text_4.left = new FormAttachment(0, 10);
		fd_text_4.bottom = new FormAttachment(btnSen, -6);
		text_4.setLayoutData(fd_text_4);

		label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_btnSen.bottom = new FormAttachment(label, -6);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(btnSearch, -8, SWT.TOP);
		fd_label.left = new FormAttachment(0, 10);
		fd_label.bottom = new FormAttachment(btnSearch, -6);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);

		grpTicketType = new Group(shell, SWT.NONE);
		grpTicketType.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		grpTicketType.setText("Ticket Type");
		FormLayout fl_grpTicketType = new FormLayout();
		fl_grpTicketType.marginLeft = 5;
		fl_grpTicketType.marginBottom = 5;
		grpTicketType.setLayout(fl_grpTicketType);
		FormData fd_grpTicketType = new FormData();
		fd_grpTicketType.bottom = new FormAttachment(text_2, -113);
		fd_grpTicketType.top = new FormAttachment(0, 10);
		fd_grpTicketType.left = new FormAttachment(text_2, -302);
		fd_grpTicketType.right = new FormAttachment(text_2, 0, SWT.RIGHT);

		grpTicketType.setLayoutData(fd_grpTicketType);

		btnRadioButton = new Button(grpTicketType, SWT.RADIO);
		btnRadioButton.setFont(SWTResourceManager.getFont("Segoe UI", 8,
				SWT.BOLD));
		FormData fd_btnRadioButton = new FormData();
		btnRadioButton.setLayoutData(fd_btnRadioButton);
		btnRadioButton.setText("Monthly");

		btnBumper = new Button(grpTicketType, SWT.RADIO);
		btnBumper.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		FormData fd_btnBumper = new FormData();
		fd_btnBumper.top = new FormAttachment(btnRadioButton, 0, SWT.TOP);
		btnBumper.setLayoutData(fd_btnBumper);
		btnBumper.setText("Bumper");

		btnBoth = new Button(grpTicketType, SWT.RADIO);
		btnBoth.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		fd_btnBumper.right = new FormAttachment(100, -162);
		FormData fd_btnBoth = new FormData();
		fd_btnBoth.top = new FormAttachment(btnRadioButton, 0, SWT.TOP);
		fd_btnBoth.left = new FormAttachment(btnBumper, 8);
		btnBoth.setLayoutData(fd_btnBoth);
		btnBoth.setText("Both");

		combo = new Combo(grpTicketType, SWT.READ_ONLY);
		fd_btnRadioButton.bottom = new FormAttachment(combo, -6);
		fd_btnRadioButton.left = new FormAttachment(combo, 0, SWT.LEFT);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0, 34);
		fd_combo.left = new FormAttachment(0);
		combo.setLayoutData(fd_combo);
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modelProvider.setSelectedBumper(combo.getText());
			}
		});
		combo.setVisible(false);
		combo.setItems(modelProvider.getBumpers());
		combo.setText(combo.getItem(0));

		grpDate = new Group(shell, SWT.NONE);
		grpDate.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		fd_lblPhoneNumber.top = new FormAttachment(grpDate, 6);
		grpDate.setText("Date");
		FormLayout fl_grpDate = new FormLayout();
		fl_grpDate.marginBottom = 5;
		fl_grpDate.marginLeft = 5;
		grpDate.setLayout(fl_grpDate);
		FormData fd_grpDate = new FormData();
		fd_grpDate.left = new FormAttachment(0, 10);
		fd_grpDate.right = new FormAttachment(grpTicketType, -6);

		fd_grpDate.top = new FormAttachment(0, 10);
		grpDate.setLayoutData(fd_grpDate);

		dateTime = new DateTime(grpDate, SWT.BORDER | SWT.DROP_DOWN);
		FormData fd_dateTime = new FormData();
		dateTime.setLayoutData(fd_dateTime);

		Label lblDate = new Label(grpDate, SWT.NONE);
		lblDate.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		fd_dateTime.top = new FormAttachment(lblDate, 6);
		fd_dateTime.left = new FormAttachment(lblDate, 0, SWT.LEFT);
		FormData fd_lblDate = new FormData();
		fd_lblDate.top = new FormAttachment(0, 11);
		fd_lblDate.left = new FormAttachment(0, 7);
		lblDate.setLayoutData(fd_lblDate);
		lblDate.setText("From Date");

		Label lblToDate = new Label(grpDate, SWT.NONE);
		lblToDate.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		FormData fd_lblToDate = new FormData();
		fd_lblToDate.top = new FormAttachment(lblDate, 0, SWT.TOP);
		lblToDate.setLayoutData(fd_lblToDate);
		lblToDate.setText("To Date");

		dateTime_1 = new DateTime(grpDate, SWT.BORDER | SWT.DROP_DOWN);
		fd_lblToDate.left = new FormAttachment(dateTime_1, 0, SWT.LEFT);
		FormData fd_dateTime_1 = new FormData();
		fd_dateTime_1.top = new FormAttachment(dateTime, 0, SWT.TOP);
		fd_dateTime_1.right = new FormAttachment(100, -10);
		dateTime_1.setLayoutData(fd_dateTime_1);

		Label label_1 = new Label(grpDate, SWT.SEPARATOR | SWT.VERTICAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(lblToDate, -21);
		label_1.setLayoutData(fd_label_1);
		m_bindingContext = initDataBindings();

	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue dateTimeObserveSelectionObserveWidget = SWTObservables
				.observeSelection(dateTime);
		IObservableValue modelProviderFromDateObserveValue = BeansObservables
				.observeValue(modelProvider, "fromDate");
		bindingContext.bindValue(dateTimeObserveSelectionObserveWidget,
				modelProviderFromDateObserveValue, null, null);
		//
		IObservableValue dateTime_1ObserveSelectionObserveWidget = SWTObservables
				.observeSelection(dateTime_1);
		IObservableValue modelProviderToDateObserveValue = BeansObservables
				.observeValue(modelProvider, "toDate");
		bindingContext.bindValue(dateTime_1ObserveSelectionObserveWidget,
				modelProviderToDateObserveValue, null, null);
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables
				.observeText(text, SWT.Modify);
		IObservableValue modelProviderPhoneNumberObserveValue = BeansObservables
				.observeValue(modelProvider, "phoneNumber");
		bindingContext.bindValue(textObserveTextObserveWidget,
				modelProviderPhoneNumberObserveValue, null, null);
		//
		IObservableValue text_1ObserveTextObserveWidget = SWTObservables
				.observeText(text_1, SWT.Modify);
		IObservableValue modelProviderTicketNumberObserveValue = BeansObservables
				.observeValue(modelProvider, "ticketNumber");
		bindingContext.bindValue(text_1ObserveTextObserveWidget,
				modelProviderTicketNumberObserveValue, null, null);
		//
		IObservableValue text_2ObserveTextObserveWidget = SWTObservables
				.observeText(text_2, SWT.Modify);
		IObservableValue modelProviderAddressObserveValue = BeansObservables
				.observeValue(modelProvider, "address");
		bindingContext.bindValue(text_2ObserveTextObserveWidget,
				modelProviderAddressObserveValue, null, null);
		//
		IObservableValue text_3ObserveTextObserveWidget = SWTObservables
				.observeText(text_3, SWT.Modify);
		IObservableValue modelProviderEmailIdObserveValue = BeansObservables
				.observeValue(modelProvider, "emailId");
		bindingContext.bindValue(text_3ObserveTextObserveWidget,
				modelProviderEmailIdObserveValue, null, null);
		//
		IObservableValue btnSenObserveSelectionObserveWidget = SWTObservables
				.observeSelection(btnSen);
		IObservableValue modelProviderSendMessagesObserveValue = BeansObservables
				.observeValue(modelProvider, "sendMessages");
		bindingContext.bindValue(btnSenObserveSelectionObserveWidget,
				modelProviderSendMessagesObserveValue, null, null);
		//
		IObservableValue btnRadioButtonObserveSelectionObserveWidget = SWTObservables
				.observeSelection(btnRadioButton);
		IObservableValue modelProviderMonthlyRadioButtonObserveValue = BeansObservables
				.observeValue(modelProvider, "monthlyRadioButton");
		bindingContext.bindValue(btnRadioButtonObserveSelectionObserveWidget,
				modelProviderMonthlyRadioButtonObserveValue, null, null);
		//
		IObservableValue btnBumperObserveSelectionObserveWidget = SWTObservables
				.observeSelection(btnBumper);
		IObservableValue modelProviderBumperRadioButtonObserveValue = BeansObservables
				.observeValue(modelProvider, "bumperRadioButton");
		bindingContext.bindValue(btnBumperObserveSelectionObserveWidget,
				modelProviderBumperRadioButtonObserveValue, null, null);
		//
		IObservableValue btnBothObserveSelectionObserveWidget = SWTObservables
				.observeSelection(btnBoth);
		IObservableValue modelProviderBothRadioButtonObserveValue = BeansObservables
				.observeValue(modelProvider, "bothRadioButton");
		bindingContext.bindValue(btnBothObserveSelectionObserveWidget,
				modelProviderBothRadioButtonObserveValue, null, null);
		//
		IObservableValue comboObserveVisibleObserveWidget = SWTObservables
				.observeVisible(combo);
		IObservableValue modelProviderComboVisibleObserveValue = BeansObservables
				.observeValue(modelProvider, "comboVisible");
		bindingContext.bindValue(comboObserveVisibleObserveWidget,
				modelProviderComboVisibleObserveValue, null, null);
		//
		IObservableValue text_4ObserveTextObserveWidget = SWTObservables
				.observeText(text_4, SWT.Modify);
		IObservableValue modelProviderNameObserveValue = BeansObservables
				.observeValue(modelProvider, "name");
		bindingContext.bindValue(text_4ObserveTextObserveWidget,
				modelProviderNameObserveValue, null, null);
		//
		return bindingContext;
	}
}
