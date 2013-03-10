//package com.intut.luckylottery.cms.dialogs;
//
//import org.eclipse.swt.widgets.Dialog;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.layout.FormLayout;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.FormData;
//import org.eclipse.swt.layout.FormAttachment;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Group;
//
////import com.intut.luckylottery.cms.modelProviders.AddEditCustomerDialogModelProvider;
//import com.intut.luckylottery.cms.util.Util;
//import com.intut.luckylottery.domain.Customer;
//
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.jface.databinding.swt.SWTObservables;
//import org.eclipse.core.databinding.beans.BeansObservables;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.core.databinding.observable.list.IObservableList;
//import org.eclipse.swt.widgets.List;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.wb.swt.ResourceManager;
//
//public class AddEditCustomerDialog extends Dialog {
//	private DataBindingContext m_bindingContext;
//
//	protected Object result;
//	protected Shell shell;
//	private Text nameText;
//	private Text mobile2Text;
//	private Text addressText;
//	private Text cityText;
//	private Text statetext;
//	private Text zipText;
//	private Text emailText;
//	private Label lblType;
//	private Text addCodeText;
//	private Text mobile1Text;
////	private AddEditCustomerDialogModelProvider modelProvider;
//	private Combo bumperCombo;
//	private Combo typeCombo;
//	private Button btnNewButton;
//	private List list;
//	private Button btnRemove;
//
//	/**
//	 * Create the dialog.
//	 * 
//	 * @param parent
//	 * @param style
//	 */
//	public AddEditCustomerDialog(Shell parent, Customer customer,
//			MultiCustomerDialog multiCustomerDialog) {
//		super(parent);
//		setText("Add/Edit ");
//		modelProvider = new AddEditCustomerDialogModelProvider(customer,
//				multiCustomerDialog);
//	}
//
//	/**
//	 * Open the dialog.
//	 * 
//	 * @return the result
//	 */
//	public Object open() {
//		createContents();
//		shell.open();
//		shell.layout();
//		Display display = getParent().getDisplay();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * Create contents of the dialog.
//	 */
//	private void createContents() {
//		shell = new Shell(getParent(), SWT.DIALOG_TRIM
//				| SWT.APPLICATION_MODAL);
//		shell.setSize(600, 470);
////		shell.setBounds(Util.setBouunds(600, 470));
//		shell.setText(getText());
//		shell.setLayout(new FormLayout());
//
//		Group grpPersonalDetails = new Group(shell, SWT.NONE);
//		grpPersonalDetails.setText("Addidtional Details");
//		grpPersonalDetails.setLayout(new FormLayout());
//		FormData fd_grpPersonalDetails = new FormData();
//		fd_grpPersonalDetails.top = new FormAttachment(0, 10);
//		fd_grpPersonalDetails.right = new FormAttachment(100, -5);
//		grpPersonalDetails.setLayoutData(fd_grpPersonalDetails);
//
//		Label lblName = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblName = new FormData();
//		fd_lblName.top = new FormAttachment(0, 10);
//		fd_lblName.left = new FormAttachment(0, 10);
//		lblName.setLayoutData(fd_lblName);
//		lblName.setText("Name");
//
//		nameText = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_nameText = new FormData();
//		fd_nameText.top = new FormAttachment(lblName, 6);
//		nameText.setLayoutData(fd_nameText);
//
//		Label lblMobile = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblMobile = new FormData();
//		fd_lblMobile.top = new FormAttachment(lblName, 0, SWT.TOP);
//		fd_lblMobile.left = new FormAttachment(lblName, 57);
//		lblMobile.setLayoutData(fd_lblMobile);
//		lblMobile.setText("Mobile 2");
//
//		mobile2Text = new Text(grpPersonalDetails, SWT.BORDER);
//		fd_nameText.right = new FormAttachment(mobile2Text, -6);
//		FormData fd_mobile2Text = new FormData();
//		fd_mobile2Text.left = new FormAttachment(0, 92);
//		fd_mobile2Text.top = new FormAttachment(lblMobile, 6);
//		mobile2Text.setLayoutData(fd_mobile2Text);
//
//		Label lblAddress = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblAddress = new FormData();
//		fd_lblAddress.top = new FormAttachment(nameText, 6);
//		fd_lblAddress.left = new FormAttachment(lblName, 0, SWT.LEFT);
//		lblAddress.setLayoutData(fd_lblAddress);
//		lblAddress.setText("Address");
//
//		addressText = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_addressText = new FormData();
//		fd_addressText.top = new FormAttachment(lblAddress, 6);
//		fd_addressText.right = new FormAttachment(nameText, 0, SWT.RIGHT);
//		addressText.setLayoutData(fd_addressText);
//
//		Label lblZip = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblZip = new FormData();
//		fd_lblZip.top = new FormAttachment(addressText, 6);
//		fd_lblZip.left = new FormAttachment(lblName, 0, SWT.LEFT);
//		lblZip.setLayoutData(fd_lblZip);
//		lblZip.setText("City");
//
//		cityText = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_cityText = new FormData();
//		fd_cityText.top = new FormAttachment(lblZip, 6);
//		fd_cityText.left = new FormAttachment(lblName, 0, SWT.LEFT);
//		cityText.setLayoutData(fd_cityText);
//
//		Label lblState = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblState = new FormData();
//		fd_lblState.top = new FormAttachment(lblAddress, 0, SWT.TOP);
//		fd_lblState.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
//		lblState.setLayoutData(fd_lblState);
//		lblState.setText("State");
//
//		statetext = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_statetext = new FormData();
//		fd_statetext.top = new FormAttachment(addressText, 0, SWT.TOP);
//		fd_statetext.left = new FormAttachment(mobile2Text, 0, SWT.LEFT);
//		statetext.setLayoutData(fd_statetext);
//
//		Label lblZip_1 = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblZip_1 = new FormData();
//		fd_lblZip_1.top = new FormAttachment(lblZip, 0, SWT.TOP);
//		fd_lblZip_1.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
//		lblZip_1.setLayoutData(fd_lblZip_1);
//		lblZip_1.setText("Zip");
//
//		zipText = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_zipText = new FormData();
//		fd_zipText.top = new FormAttachment(cityText, 0, SWT.TOP);
//		fd_zipText.left = new FormAttachment(lblMobile, 0, SWT.LEFT);
//		zipText.setLayoutData(fd_zipText);
//
//		Label lblNewLabel_1 = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblNewLabel_1 = new FormData();
//		fd_lblNewLabel_1.top = new FormAttachment(lblName, 0, SWT.TOP);
//		fd_lblNewLabel_1.left = new FormAttachment(lblMobile, 39);
//		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
//		lblNewLabel_1.setText("Email");
//
//		emailText = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_emailText = new FormData();
//		fd_emailText.top = new FormAttachment(nameText, 0, SWT.TOP);
//		fd_emailText.left = new FormAttachment(mobile2Text, 6);
//		emailText.setLayoutData(fd_emailText);
//
//		Group grpCode = new Group(shell, SWT.NONE);
//		fd_grpPersonalDetails.left = new FormAttachment(grpCode, 6);
//		grpCode.setText("Code");
//		FormData fd_grpCode = new FormData();
//		fd_grpCode.bottom = new FormAttachment(100, -67);
//		fd_grpCode.top = new FormAttachment(0);
//		fd_grpCode.left = new FormAttachment(0, 10);
//		fd_grpCode.right = new FormAttachment(100, -276);
//
//		Label lblNewLabel = new Label(grpPersonalDetails, SWT.NONE);
//		FormData fd_lblNewLabel = new FormData();
//		fd_lblNewLabel.top = new FormAttachment(mobile2Text, 6);
//		fd_lblNewLabel.left = new FormAttachment(emailText, 0, SWT.LEFT);
//		lblNewLabel.setLayoutData(fd_lblNewLabel);
//		lblNewLabel.setText("Mobile 1*");
//
//		mobile1Text = new Text(grpPersonalDetails, SWT.BORDER);
//		FormData fd_mobile1Text = new FormData();
//		fd_mobile1Text.top = new FormAttachment(addressText, 0, SWT.TOP);
//		fd_mobile1Text.left = new FormAttachment(statetext, 6);
//		mobile1Text.setLayoutData(fd_mobile1Text);
//		grpCode.setLayout(new FormLayout());
//		grpCode.setLayoutData(fd_grpCode);
//
//		addCodeText = new Text(grpCode, SWT.BORDER);
//		FormData fd_addCodeText = new FormData();
//		
//		
//		addCodeText.setLayoutData(fd_addCodeText);
//
//		btnNewButton = new Button(grpCode, SWT.NONE);
//		fd_addCodeText.right = new FormAttachment(btnNewButton, -10);
//		btnNewButton.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/add24.png"));
//		btnNewButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				modelProvider.setSendButtonenabled(true);
//				modelProvider.addCodeToCodeList();
//			}
//		});
//		FormData fd_btnNewButton = new FormData();
//		fd_btnNewButton.bottom = new FormAttachment(100,-10);
//		btnNewButton.setLayoutData(fd_btnNewButton);
//		btnNewButton.setText("Add code");
//
//		Group grpLotteryType = new Group(shell, SWT.NONE);
//		fd_grpPersonalDetails.bottom = new FormAttachment(grpLotteryType, -7);
//		grpLotteryType.setText("Lottery type");
//		grpLotteryType.setLayout(new FormLayout());
//		FormData fd_grpLotteryType = new FormData();
//		fd_grpLotteryType.bottom = new FormAttachment(grpCode, 0, SWT.BOTTOM);
//		fd_grpLotteryType.left = new FormAttachment(grpPersonalDetails, 0, SWT.LEFT);
//		fd_grpLotteryType.top = new FormAttachment(0, 191);
//		fd_grpLotteryType.right = new FormAttachment(100, -5);
//
//		list = new List(grpCode, SWT.BORDER | SWT.V_SCROLL);
//		fd_addCodeText.bottom = new FormAttachment(list, 37, SWT.BOTTOM);
//		fd_addCodeText.top = new FormAttachment(list, 10);
//		FormData fd_list = new FormData();
//		fd_list.top = new FormAttachment(0, 10);
//		fd_list.bottom = new FormAttachment(btnNewButton, -3);
//		fd_list.right = new FormAttachment(100, -16);
//		fd_list.left = new FormAttachment(0, 10);
//		list.setLayoutData(fd_list);
//
//		btnRemove = new Button(grpCode, SWT.NONE);
//		fd_btnNewButton.right = new FormAttachment(btnRemove, -12);
//		btnRemove.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/delete24.png"));
//		btnRemove.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				modelProvider.removeCode();
//			}
//		});
//		FormData fd_btnRemove = new FormData();
//		fd_btnRemove.top = new FormAttachment(addCodeText, -7, SWT.TOP);
//		fd_btnRemove.right = new FormAttachment(list, 0, SWT.RIGHT);
//		btnRemove.setLayoutData(fd_btnRemove);
//		btnRemove.setText("Remove");
//		grpLotteryType.setLayoutData(fd_grpLotteryType);
//
//		typeCombo = new Combo(grpLotteryType, SWT.READ_ONLY);
//		typeCombo.setItems(modelProvider.getType());
//		typeCombo.select(0);
//
//		typeCombo.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				modelProvider.setSelectedType(typeCombo.getText());
//				modelProvider.setLotteryNameList();
//				bumperCombo.select(0);
//			}
//		});
//		FormData fd_typeCombo = new FormData();
//		fd_typeCombo.left = new FormAttachment(0, 10);
//		typeCombo.setLayoutData(fd_typeCombo);
//
//		lblType = new Label(grpLotteryType, SWT.NONE);
//		fd_typeCombo.top = new FormAttachment(0, 21);
//		FormData fd_lblType = new FormData();
//		fd_lblType.bottom = new FormAttachment(typeCombo, -6);
//		fd_lblType.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
//		lblType.setLayoutData(fd_lblType);
//		lblType.setText("Type");
//
//		Label lblBumper = new Label(grpLotteryType, SWT.NONE);
//		FormData fd_lblBumper = new FormData();
//		fd_lblBumper.top = new FormAttachment(typeCombo, 6);
//		fd_lblBumper.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
//		lblBumper.setLayoutData(fd_lblBumper);
//		lblBumper.setText("Bumper");
//
//		bumperCombo = new Combo(grpLotteryType, SWT.READ_ONLY);
//		FormData fd_bumperCombo = new FormData();
//		fd_bumperCombo.top = new FormAttachment(lblBumper, 6);
//		fd_bumperCombo.left = new FormAttachment(typeCombo, 0, SWT.LEFT);
//		bumperCombo.setLayoutData(fd_bumperCombo);
//
//		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//		FormData fd_label = new FormData();
//		fd_label.left = new FormAttachment(0, 10);
//		fd_label.right = new FormAttachment(100, -10);
//		fd_label.top = new FormAttachment(0, 390);
//		label.setLayoutData(fd_label);
//		m_bindingContext = initDataBindings();
//		modelProvider.setSelectedType(typeCombo.getText());
//		modelProvider.setLotteryNameList();
//		modelProvider.loadCustomerData();
//		typeCombo.setText(modelProvider.getSelectedType());
//		bumperCombo.setText(modelProvider.getSelectedBumper());
//
//		Button btnSave = new Button(shell, SWT.NONE);
//		fd_label.bottom = new FormAttachment(btnSave, -6);
//		btnSave.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/save.png"));
//		btnSave.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				modelProvider.save();
//				shell.close();
//			}
//		});
//		FormData fd_btnSave = new FormData();
//		fd_btnSave.right = new FormAttachment(100, -10);
//		fd_btnSave.top = new FormAttachment(0, 398);
//		btnSave.setLayoutData(fd_btnSave);
//		btnSave.setText("Save");
//	}
//
//	protected DataBindingContext initDataBindings() {
//		DataBindingContext bindingContext = new DataBindingContext();
//		//
//		IObservableValue addCodeTextObserveTextObserveWidget = SWTObservables
//				.observeText(addCodeText, SWT.Modify);
//		IObservableValue modelProviderCodeObserveValue = BeansObservables
//				.observeValue(modelProvider, "code");
//		bindingContext.bindValue(addCodeTextObserveTextObserveWidget,
//				modelProviderCodeObserveValue, null, null);
//		//
//		IObservableValue nameTextObserveTextObserveWidget = SWTObservables
//				.observeText(nameText, SWT.Modify);
//		IObservableValue modelProviderNameObserveValue = BeansObservables
//				.observeValue(modelProvider, "name");
//		bindingContext.bindValue(nameTextObserveTextObserveWidget,
//				modelProviderNameObserveValue, null, null);
//		//
//		IObservableValue addressTextObserveTextObserveWidget = SWTObservables
//				.observeText(addressText, SWT.Modify);
//		IObservableValue modelProviderAddressObserveValue = BeansObservables
//				.observeValue(modelProvider, "address");
//		bindingContext.bindValue(addressTextObserveTextObserveWidget,
//				modelProviderAddressObserveValue, null, null);
//		//
//		IObservableValue cityTextObserveTextObserveWidget = SWTObservables
//				.observeText(cityText, SWT.Modify);
//		IObservableValue modelProviderCityObserveValue = BeansObservables
//				.observeValue(modelProvider, "city");
//		bindingContext.bindValue(cityTextObserveTextObserveWidget,
//				modelProviderCityObserveValue, null, null);
//		//
//		IObservableValue mobile2TextObserveTextObserveWidget = SWTObservables
//				.observeText(mobile2Text, SWT.Modify);
//		IObservableValue modelProviderMobile2ObserveValue = BeansObservables
//				.observeValue(modelProvider, "mobile2");
//		bindingContext.bindValue(mobile2TextObserveTextObserveWidget,
//				modelProviderMobile2ObserveValue, null, null);
//		//
//		IObservableValue statetextObserveTextObserveWidget = SWTObservables
//				.observeText(statetext, SWT.Modify);
//		IObservableValue modelProviderStateObserveValue = BeansObservables
//				.observeValue(modelProvider, "state");
//		bindingContext.bindValue(statetextObserveTextObserveWidget,
//				modelProviderStateObserveValue, null, null);
//		//
//		IObservableValue zipTextObserveTextObserveWidget = SWTObservables
//				.observeText(zipText, SWT.Modify);
//		IObservableValue modelProviderZipObserveValue = BeansObservables
//				.observeValue(modelProvider, "zip");
//		bindingContext.bindValue(zipTextObserveTextObserveWidget,
//				modelProviderZipObserveValue, null, null);
//		//
//		IObservableValue emailTextObserveTextObserveWidget = SWTObservables
//				.observeText(emailText, SWT.Modify);
//		IObservableValue modelProviderEmailObserveValue = BeansObservables
//				.observeValue(modelProvider, "email");
//		bindingContext.bindValue(emailTextObserveTextObserveWidget,
//				modelProviderEmailObserveValue, null, null);
//		//
//		IObservableValue mobile1TextObserveTextObserveWidget = SWTObservables
//				.observeText(mobile1Text, SWT.Modify);
//		IObservableValue modelProviderMobile1ObserveValue = BeansObservables
//				.observeValue(modelProvider, "mobile1");
//		bindingContext.bindValue(mobile1TextObserveTextObserveWidget,
//				modelProviderMobile1ObserveValue, null, null);
//		//
//		IObservableList bumperComboObserveItemsObserveListWidget = SWTObservables
//				.observeItems(bumperCombo);
//		bindingContext.bindList(bumperComboObserveItemsObserveListWidget,
//				modelProvider.getLotteryNameList(), null, null);
//		//
//		IObservableValue typeComboObserveSelectionObserveWidget = SWTObservables
//				.observeSelection(typeCombo);
//		IObservableValue modelProviderSelectedTypeObserveValue = BeansObservables
//				.observeValue(modelProvider, "selectedType");
//		bindingContext.bindValue(typeComboObserveSelectionObserveWidget,
//				modelProviderSelectedTypeObserveValue, null, null);
//		//
//		IObservableValue btnNewButtonObserveEnabledObserveWidget = SWTObservables
//				.observeEnabled(btnNewButton);
//		IObservableValue modelProviderAddButtonEnabledObserveValue = BeansObservables
//				.observeValue(modelProvider, "addButtonEnabled");
//		bindingContext.bindValue(btnNewButtonObserveEnabledObserveWidget,
//				modelProviderAddButtonEnabledObserveValue, null, null);
//		//
//		IObservableList listObserveItemsObserveListWidget = SWTObservables
//				.observeItems(list);
//		bindingContext.bindList(listObserveItemsObserveListWidget,
//				modelProvider.getCodeList(), null, null);
//		//
//		IObservableValue listObserveSelectionObserveWidget = SWTObservables
//				.observeSelection(list);
//		IObservableValue modelProviderSelectedcodeObserveValue = BeansObservables
//				.observeValue(modelProvider, "selectedcode");
//		bindingContext.bindValue(listObserveSelectionObserveWidget,
//				modelProviderSelectedcodeObserveValue, null, null);
//		//
//		IObservableValue btnRemoveObserveEnabledObserveWidget = SWTObservables
//				.observeEnabled(btnRemove);
//		IObservableValue modelProviderRemoveButtonEnabledObserveValue = BeansObservables
//				.observeValue(modelProvider, "removeButtonEnabled");
//		bindingContext.bindValue(btnRemoveObserveEnabledObserveWidget,
//				modelProviderRemoveButtonEnabledObserveValue, null, null);
//		//
//		IObservableValue bumperComboObserveTextObserveWidget = SWTObservables
//				.observeText(bumperCombo);
//		IObservableValue modelProviderSelectedBumperObserveValue = BeansObservables
//				.observeValue(modelProvider, "selectedBumper");
//		bindingContext.bindValue(bumperComboObserveTextObserveWidget,
//				modelProviderSelectedBumperObserveValue, null, null);
//		//
//		IObservableValue typeComboObserveTextObserveWidget = SWTObservables
//				.observeText(typeCombo);
//		bindingContext.bindValue(typeComboObserveTextObserveWidget,
//				modelProviderSelectedTypeObserveValue, null, null);
//		//
//		return bindingContext;
//	}
//}
