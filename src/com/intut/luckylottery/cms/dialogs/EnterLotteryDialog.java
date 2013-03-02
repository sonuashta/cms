package com.intut.luckylottery.cms.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

import com.intut.luckylottery.cms.modelProviders.IndividualLotteryModelProvider;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class EnterLotteryDialog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shell;
	private Text nameText;
	private Text mobile2Text;
	private Text addressText;
	private Text cityText;
	private Text statetext;
	private Text zipText;
	private Text emailText;
	private Label lblType;
	private Text addCodeText;
	private Text mobile1Text;
	private IndividualLotteryModelProvider modelProvider;
	private Combo bumperCombo;
	private Combo typeCombo;
	private Button btnNewButton;
	private List list;
	private Button btnRemove;
	private Button btnVerified;
	private Button btnSave;
	

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EnterLotteryDialog(Shell parent) {
		super(parent);
		setText("SWT Dialog");
		modelProvider=new IndividualLotteryModelProvider();
	}

	/**
	 * Open the dialog.
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
		shell.setSize(600, 470);
		shell.setText(getText());
		shell.setLayout(new FormLayout());
		
		btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.computeAndDisplayResults();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.right = new FormAttachment(100, -10);
		fd_btnSave.bottom = new FormAttachment(100, -7);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Send SMS");
		
		Group grpPersonalDetails = new Group(shell, SWT.NONE);
		grpPersonalDetails.setText("Addidtional Details");
		grpPersonalDetails.setLayout(new FormLayout());
		FormData fd_grpPersonalDetails = new FormData();
		fd_grpPersonalDetails.right = new FormAttachment(100, -269);
		fd_grpPersonalDetails.left = new FormAttachment(0, 10);
		grpPersonalDetails.setLayoutData(fd_grpPersonalDetails);
		
		Label lblName = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(0, 10);
		fd_lblName.left = new FormAttachment(0, 10);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name");
		
		nameText = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_nameText = new FormData();
		fd_nameText.top = new FormAttachment(lblName, 6);
		nameText.setLayoutData(fd_nameText);
		
		Label lblMobile = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblMobile = new FormData();
		fd_lblMobile.top = new FormAttachment(lblName, 0, SWT.TOP);
		fd_lblMobile.left = new FormAttachment(lblName, 57);
		lblMobile.setLayoutData(fd_lblMobile);
		lblMobile.setText("Mobile 2");
		
		mobile2Text = new Text(grpPersonalDetails, SWT.BORDER);
		fd_nameText.right = new FormAttachment(mobile2Text, -6);
		FormData fd_mobile2Text = new FormData();
		fd_mobile2Text.left = new FormAttachment(0, 92);
		fd_mobile2Text.top = new FormAttachment(lblMobile, 6);
		mobile2Text.setLayoutData(fd_mobile2Text);
		
		Label lblAddress = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblAddress = new FormData();
		fd_lblAddress.top = new FormAttachment(nameText, 6);
		fd_lblAddress.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblAddress.setLayoutData(fd_lblAddress);
		lblAddress.setText("Address");
		
		addressText = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_addressText = new FormData();
		fd_addressText.top = new FormAttachment(lblAddress, 6);
		fd_addressText.right = new FormAttachment(nameText, 0, SWT.RIGHT);
		addressText.setLayoutData(fd_addressText);
		
		Label lblZip = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblZip = new FormData();
		fd_lblZip.top = new FormAttachment(addressText, 6);
		fd_lblZip.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblZip.setLayoutData(fd_lblZip);
		lblZip.setText("City");
		
		cityText = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_cityText = new FormData();
		fd_cityText.top = new FormAttachment(lblZip, 6);
		fd_cityText.left = new FormAttachment(lblName, 0, SWT.LEFT);
		cityText.setLayoutData(fd_cityText);
		
		Label lblState = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblState = new FormData();
		fd_lblState.top = new FormAttachment(lblAddress, 0, SWT.TOP);
		fd_lblState.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
		lblState.setLayoutData(fd_lblState);
		lblState.setText("State");
		
		statetext = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_statetext = new FormData();
		fd_statetext.top = new FormAttachment(addressText, 0, SWT.TOP);
		fd_statetext.left = new FormAttachment(mobile2Text, 0, SWT.LEFT);
		statetext.setLayoutData(fd_statetext);
		
		Label lblZip_1 = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblZip_1 = new FormData();
		fd_lblZip_1.top = new FormAttachment(lblZip, 0, SWT.TOP);
		fd_lblZip_1.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
		lblZip_1.setLayoutData(fd_lblZip_1);
		lblZip_1.setText("Zip");
		
		zipText = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_zipText = new FormData();
		fd_zipText.top = new FormAttachment(cityText, 0, SWT.TOP);
		fd_zipText.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
		zipText.setLayoutData(fd_zipText);
		
		Label lblNewLabel_1 = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(lblName, 0, SWT.TOP);
		fd_lblNewLabel_1.left = new FormAttachment(lblMobile, 39);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("Email");
		
		emailText = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_emailText = new FormData();
		fd_emailText.top = new FormAttachment(nameText, 0, SWT.TOP);
		fd_emailText.left = new FormAttachment(mobile2Text, 6);
		emailText.setLayoutData(fd_emailText);
		
		Group grpCode = new Group(shell, SWT.NONE);
		fd_grpPersonalDetails.top = new FormAttachment(0, 198);
		grpCode.setText("Code");
		FormData fd_grpCode = new FormData();
		fd_grpCode.left = new FormAttachment(0, 10);
		fd_grpCode.bottom = new FormAttachment(grpPersonalDetails, -6);
		fd_grpCode.top = new FormAttachment(0, 10);
		
		Label lblNewLabel = new Label(grpPersonalDetails, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(mobile2Text, 6);
		fd_lblNewLabel.left = new FormAttachment(emailText, 0, SWT.LEFT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Mobile 1*");
		
		mobile1Text = new Text(grpPersonalDetails, SWT.BORDER);
		FormData fd_mobile1Text = new FormData();
		fd_mobile1Text.top = new FormAttachment(addressText, 0, SWT.TOP);
		fd_mobile1Text.left = new FormAttachment(statetext, 6);
		mobile1Text.setLayoutData(fd_mobile1Text);
		grpCode.setLayout(new FormLayout());
		grpCode.setLayoutData(fd_grpCode);
		
		addCodeText = new Text(grpCode, SWT.BORDER);
		FormData fd_addCodeText = new FormData();
		fd_addCodeText.bottom = new FormAttachment(100, -6);
		fd_addCodeText.left = new FormAttachment(0, 10);
		addCodeText.setLayoutData(fd_addCodeText);
		
		btnNewButton = new Button(grpCode, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.setSendButtonenabled(true);
				modelProvider.addCodeToCodeList();
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(addCodeText, -2, SWT.TOP);
		fd_btnNewButton.left = new FormAttachment(addCodeText, 6);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Add code");
		
		Group grpLotteryType = new Group(shell, SWT.NONE);
		fd_grpCode.right = new FormAttachment(grpLotteryType, -6);
		grpLotteryType.setText("Lottery type");
		grpLotteryType.setLayout(new FormLayout());
		FormData fd_grpLotteryType = new FormData();
		fd_grpLotteryType.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		fd_grpLotteryType.bottom = new FormAttachment(btnSave, -262);
		fd_grpLotteryType.top = new FormAttachment(0, 10);
		fd_grpLotteryType.left = new FormAttachment(0, 331);
		
		list = new List(grpCode, SWT.BORDER | SWT.V_SCROLL);
		FormData fd_list = new FormData();
		fd_list.bottom = new FormAttachment(addCodeText, -6);
		fd_list.left = new FormAttachment(addCodeText, 0, SWT.LEFT);
		fd_list.right = new FormAttachment(100, -10);
		fd_list.top = new FormAttachment(0, 10);
		list.setLayoutData(fd_list);
		
		btnRemove = new Button(grpCode, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.removeCode();
			}
		});
		FormData fd_btnRemove = new FormData();
		fd_btnRemove.top = new FormAttachment(addCodeText, -2, SWT.TOP);
		fd_btnRemove.right = new FormAttachment(list, 0, SWT.RIGHT);
		btnRemove.setLayoutData(fd_btnRemove);
		btnRemove.setText("Remove");
		
		Button btnUploadCsv = new Button(grpCode, SWT.NONE);
		FormData fd_btnUploadCsv = new FormData();
		fd_btnUploadCsv.top = new FormAttachment(addCodeText, -2, SWT.TOP);
		fd_btnUploadCsv.left = new FormAttachment(btnNewButton, 6);
		btnUploadCsv.setLayoutData(fd_btnUploadCsv);
		btnUploadCsv.setText("Upload CSV");
		grpLotteryType.setLayoutData(fd_grpLotteryType);
		
		typeCombo = new Combo(grpLotteryType, SWT.READ_ONLY);
		typeCombo.setItems(modelProvider.getType());
		typeCombo.select(0);
		
		typeCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modelProvider.setSelectedType(typeCombo.getText());
				modelProvider.setLotteryNameList();
				bumperCombo.select(0);
			}
		});
		FormData fd_typeCombo = new FormData();
		fd_typeCombo.left = new FormAttachment(0, 10);
		typeCombo.setLayoutData(fd_typeCombo);
		
		lblType = new Label(grpLotteryType, SWT.NONE);
		fd_typeCombo.top = new FormAttachment(0, 21);
		FormData fd_lblType = new FormData();
		fd_lblType.bottom = new FormAttachment(typeCombo, -6);
		fd_lblType.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
		lblType.setLayoutData(fd_lblType);
		lblType.setText("Type");
		
		Label lblBumper = new Label(grpLotteryType, SWT.NONE);
		FormData fd_lblBumper = new FormData();
		fd_lblBumper.top = new FormAttachment(typeCombo, 6);
		fd_lblBumper.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
		lblBumper.setLayoutData(fd_lblBumper);
		lblBumper.setText("Bumper");
		
		bumperCombo = new Combo(grpLotteryType, SWT.READ_ONLY);
		FormData fd_bumperCombo = new FormData();
		fd_bumperCombo.top = new FormAttachment(lblBumper, 6);
		fd_bumperCombo.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
		bumperCombo.setLayoutData(fd_bumperCombo);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_grpPersonalDetails.bottom = new FormAttachment(label, -6);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.top = new FormAttachment(btnSave, -8, SWT.TOP);
		fd_label.bottom = new FormAttachment(btnSave, -6);
		label.setLayoutData(fd_label);
		
		
		btnVerified = new Button(shell, SWT.NONE);
		FormData fd_btnVerified = new FormData();
		fd_btnVerified.top = new FormAttachment(btnSave, 0, SWT.TOP);
		fd_btnVerified.right = new FormAttachment(btnSave, -6);
		btnVerified.setLayoutData(fd_btnVerified);
		btnVerified.setText("Verified");
		m_bindingContext = initDataBindings();
		modelProvider.setSelectedType(typeCombo.getText());
		modelProvider.setLotteryNameList();
		bumperCombo.select(0);

	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue addCodeTextObserveTextObserveWidget = SWTObservables.observeText(addCodeText, SWT.Modify);
		IObservableValue modelProviderCodeObserveValue = BeansObservables.observeValue(modelProvider, "code");
		bindingContext.bindValue(addCodeTextObserveTextObserveWidget, modelProviderCodeObserveValue, null, null);
		//
		IObservableValue nameTextObserveTextObserveWidget = SWTObservables.observeText(nameText, SWT.Modify);
		IObservableValue modelProviderNameObserveValue = BeansObservables.observeValue(modelProvider, "name");
		bindingContext.bindValue(nameTextObserveTextObserveWidget, modelProviderNameObserveValue, null, null);
		//
		IObservableValue addressTextObserveTextObserveWidget = SWTObservables.observeText(addressText, SWT.Modify);
		IObservableValue modelProviderAddressObserveValue = BeansObservables.observeValue(modelProvider, "address");
		bindingContext.bindValue(addressTextObserveTextObserveWidget, modelProviderAddressObserveValue, null, null);
		//
		IObservableValue cityTextObserveTextObserveWidget = SWTObservables.observeText(cityText, SWT.Modify);
		IObservableValue modelProviderCityObserveValue = BeansObservables.observeValue(modelProvider, "city");
		bindingContext.bindValue(cityTextObserveTextObserveWidget, modelProviderCityObserveValue, null, null);
		//
		IObservableValue mobile2TextObserveTextObserveWidget = SWTObservables.observeText(mobile2Text, SWT.Modify);
		IObservableValue modelProviderMobile2ObserveValue = BeansObservables.observeValue(modelProvider, "mobile2");
		bindingContext.bindValue(mobile2TextObserveTextObserveWidget, modelProviderMobile2ObserveValue, null, null);
		//
		IObservableValue statetextObserveTextObserveWidget = SWTObservables.observeText(statetext, SWT.Modify);
		IObservableValue modelProviderStateObserveValue = BeansObservables.observeValue(modelProvider, "state");
		bindingContext.bindValue(statetextObserveTextObserveWidget, modelProviderStateObserveValue, null, null);
		//
		IObservableValue zipTextObserveTextObserveWidget = SWTObservables.observeText(zipText, SWT.Modify);
		IObservableValue modelProviderZipObserveValue = BeansObservables.observeValue(modelProvider, "zip");
		bindingContext.bindValue(zipTextObserveTextObserveWidget, modelProviderZipObserveValue, null, null);
		//
		IObservableValue emailTextObserveTextObserveWidget = SWTObservables.observeText(emailText, SWT.Modify);
		IObservableValue modelProviderEmailObserveValue = BeansObservables.observeValue(modelProvider, "email");
		bindingContext.bindValue(emailTextObserveTextObserveWidget, modelProviderEmailObserveValue, null, null);
		//
		IObservableValue mobile1TextObserveTextObserveWidget = SWTObservables.observeText(mobile1Text, SWT.Modify);
		IObservableValue modelProviderMobile1ObserveValue = BeansObservables.observeValue(modelProvider, "mobile1");
		bindingContext.bindValue(mobile1TextObserveTextObserveWidget, modelProviderMobile1ObserveValue, null, null);
		//
		IObservableList bumperComboObserveItemsObserveListWidget = SWTObservables.observeItems(bumperCombo);
		bindingContext.bindList(bumperComboObserveItemsObserveListWidget, modelProvider.getLotteryNameList(), null, null);
		//
		IObservableValue typeComboObserveSelectionObserveWidget = SWTObservables.observeSelection(typeCombo);
		IObservableValue modelProviderSelectedTypeObserveValue = BeansObservables.observeValue(modelProvider, "selectedType");
		bindingContext.bindValue(typeComboObserveSelectionObserveWidget, modelProviderSelectedTypeObserveValue, null, null);
		//
		IObservableValue btnNewButtonObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnNewButton);
		IObservableValue modelProviderAddButtonEnabledObserveValue = BeansObservables.observeValue(modelProvider, "addButtonEnabled");
		bindingContext.bindValue(btnNewButtonObserveEnabledObserveWidget, modelProviderAddButtonEnabledObserveValue, null, null);
		//
		IObservableList listObserveItemsObserveListWidget = SWTObservables.observeItems(list);
		bindingContext.bindList(listObserveItemsObserveListWidget, modelProvider.getCodeList(), null, null);
		//
		IObservableValue listObserveSelectionObserveWidget = SWTObservables.observeSelection(list);
		IObservableValue modelProviderSelectedcodeObserveValue = BeansObservables.observeValue(modelProvider, "selectedcode");
		bindingContext.bindValue(listObserveSelectionObserveWidget, modelProviderSelectedcodeObserveValue, null, null);
		//
		IObservableValue btnRemoveObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnRemove);
		IObservableValue modelProviderRemoveButtonEnabledObserveValue = BeansObservables.observeValue(modelProvider, "removeButtonEnabled");
		bindingContext.bindValue(btnRemoveObserveEnabledObserveWidget, modelProviderRemoveButtonEnabledObserveValue, null, null);
		//
		IObservableValue btnVerifiedObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnVerified);
		IObservableValue modelProviderVerifyButtonEnabledObserveValue = BeansObservables.observeValue(modelProvider, "verifyButtonEnabled");
		bindingContext.bindValue(btnVerifiedObserveEnabledObserveWidget, modelProviderVerifyButtonEnabledObserveValue, null, null);
		//
		IObservableValue btnSaveObserveEnabledObserveWidget = SWTObservables.observeEnabled(btnSave);
		IObservableValue modelProviderSendButtonenabledObserveValue = BeansObservables.observeValue(modelProvider, "sendButtonenabled");
		bindingContext.bindValue(btnSaveObserveEnabledObserveWidget, modelProviderSendButtonenabledObserveValue, null, null);
		//
		return bindingContext;
	}
}
