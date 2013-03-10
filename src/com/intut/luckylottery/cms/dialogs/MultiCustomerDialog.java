//package com.intut.luckylottery.cms.dialogs;
//
//import org.eclipse.swt.widgets.Dialog;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.layout.FormLayout;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.FormData;
//import org.eclipse.swt.widgets.TableColumn;
//import org.eclipse.swt.layout.FormAttachment;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//
//import com.intut.luckylottery.cms.modelProviders.MultidialogModelProvider;
//import com.intut.luckylottery.cms.util.Util;
//import com.intut.luckylottery.domain.Customer;
//
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.jface.databinding.swt.SWTObservables;
//import org.eclipse.core.databinding.beans.BeansObservables;
//import org.eclipse.wb.swt.SWTResourceManager;
//
//public class MultiCustomerDialog extends Dialog {
//	private DataBindingContext m_bindingContext;
//
//	protected Object result;
//	protected Shell shell;
//	private Table table;
//	private Text text;
//	private MultidialogModelProvider modelProvider;
//	private Button btnUploadCsv;
//	private Button btnSend;
//	private MultiCustomerDialog multiCustomerDialog;
//
//	/**
//	 * Create the dialog.
//	 * 
//	 * @param parent
//	 * @param style
//	 */
//	public MultiCustomerDialog(Shell parent) {
//		super(parent);
//		setText("SWT Dialog");
//
//		modelProvider = new MultidialogModelProvider(this);
//		multiCustomerDialog = this;
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
//		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
//		shell.setSize(600, 450);
//		// shell.setSize(450, 300);
//		shell.setBounds(Util.setBouunds(600, 450));
//		shell.setText(getText());
//		shell.setLayout(new FormLayout());
//
//		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
//		table.setLinesVisible(true);
//		table.setHeaderVisible(true);
//		FormData fd_table = new FormData();
//		fd_table.left = new FormAttachment(0, 10);
//		fd_table.right = new FormAttachment(100, -10);
//		table.setLayoutData(fd_table);
//
//		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
//		tableColumn.setWidth(47);
//		tableColumn.setText("S.No");
//
//		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
//		tblclmnNewColumn.setWidth(100);
//		tblclmnNewColumn.setText("Name");
//
//		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
//		tblclmnName.setWidth(250);
//		tblclmnName.setText("Codes");
//
//		TableColumn tblclmnMobile = new TableColumn(table, SWT.NONE);
//		tblclmnMobile.setWidth(171);
//		tblclmnMobile.setText("Mobile");
//
//		Label lblFile = new Label(shell, SWT.NONE);
//		FormData fd_lblFile = new FormData();
//		fd_lblFile.left = new FormAttachment(table, 0, SWT.LEFT);
//		lblFile.setLayoutData(fd_lblFile);
//		lblFile.setText("File");
//
//		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
//		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//		fd_lblFile.top = new FormAttachment(text, 3, SWT.TOP);
//		FormData fd_text = new FormData();
//		fd_text.left = new FormAttachment(lblFile, 6);
//		fd_text.top = new FormAttachment(0, 10);
//		text.setLayoutData(fd_text);
//
//		Button btnBrowse = new Button(shell, SWT.NONE);
//		fd_text.right = new FormAttachment(100, -68);
//		btnBrowse.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				FileDialog fileDialog = new FileDialog(shell);
//				String path = fileDialog.open();
//				if (!Util.isStringNullOrEmpty(path)) {
//					modelProvider.setFilePath(path);
//				}
//			}
//		});
//		FormData fd_btnBrowse = new FormData();
//		fd_btnBrowse.top = new FormAttachment(lblFile, -5, SWT.TOP);
//		fd_btnBrowse.left = new FormAttachment(text, 6);
//		btnBrowse.setLayoutData(fd_btnBrowse);
//		btnBrowse.setText("browse");
//
//		btnUploadCsv = new Button(shell, SWT.NONE);
//		btnUploadCsv.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				modelProvider.uploadCSV();
//			}
//		});
//		FormData fd_btnUploadCsv = new FormData();
//		btnUploadCsv.setLayoutData(fd_btnUploadCsv);
//		btnUploadCsv.setText("Upload CSV");
//
//		btnSend = new Button(shell, SWT.NONE);
//		btnSend.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				modelProvider.sendSMS();
//			}
//		});
//		fd_btnUploadCsv.top = new FormAttachment(btnSend, 0, SWT.TOP);
//		fd_btnUploadCsv.right = new FormAttachment(btnSend, -6);
//		FormData fd_btnSend = new FormData();
//		fd_btnSend.bottom = new FormAttachment(100, -10);
//		fd_btnSend.right = new FormAttachment(100, -10);
//		btnSend.setLayoutData(fd_btnSend);
//		btnSend.setText("Send");
//
//		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//		fd_table.bottom = new FormAttachment(label, -6);
//		FormData fd_label = new FormData();
//		fd_label.left = new FormAttachment(0, 10);
//		fd_label.right = new FormAttachment(100, -10);
//		fd_label.top = new FormAttachment(btnUploadCsv, -8, SWT.TOP);
//		fd_label.bottom = new FormAttachment(btnUploadCsv, -6);
//		label.setLayoutData(fd_label);
//
//		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//		fd_table.top = new FormAttachment(0, 51);
//		FormData fd_label_1 = new FormData();
//		fd_label_1.top = new FormAttachment(0, 43);
//		fd_label_1.right = new FormAttachment(100, -10);
//		fd_label_1.bottom = new FormAttachment(table, -6);
//		fd_label_1.left = new FormAttachment(0, 10);
//		label_1.setLayoutData(fd_label_1);
//
//		Button btnEdit = new Button(shell, SWT.NONE);
//		btnEdit.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//				int index = table.getSelectionIndex();
//				if(index<0)
//					return ;
////				AddEditCustomerDialog dialog = new AddEditCustomerDialog(
////						new Shell(), modelProvider.getCustomers().get(index),
////						multiCustomerDialog);
////				dialog.open();
//			}
//		});
//		FormData fd_btnEdit = new FormData();
//		fd_btnEdit.top = new FormAttachment(btnUploadCsv, 0, SWT.TOP);
//		fd_btnEdit.right = new FormAttachment(btnUploadCsv, -9);
//		btnEdit.setLayoutData(fd_btnEdit);
//		btnEdit.setText("Edit");
//
//		Button btnRemove = new Button(shell, SWT.NONE);
//		btnRemove.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				int index = table.getSelectionIndex();
//				if(index<0)
//					return ;
//				modelProvider.getCustomers().remove(index);
//				reAllocateItems();
//			}
//		});
//		FormData fd_btnRemove = new FormData();
//		fd_btnRemove.bottom = new FormAttachment(btnUploadCsv, 0, SWT.BOTTOM);
//		fd_btnRemove.right = new FormAttachment(btnEdit, -6);
//		btnRemove.setLayoutData(fd_btnRemove);
//		btnRemove.setText("Remove");
//
//		Button btnAdd = new Button(shell, SWT.NONE);
//		btnAdd.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				Customer customer = new Customer();
////				customer.setLotterTypeId(1);
////				modelProvider.getCustomers().add(customer);
////				AddEditCustomerDialog dialog = new AddEditCustomerDialog(
////						new Shell(), customer, multiCustomerDialog);
////				dialog.open();
//			}
//		});
//		FormData fd_btnAdd = new FormData();
//		fd_btnAdd.bottom = new FormAttachment(btnUploadCsv, 0, SWT.BOTTOM);
//		fd_btnAdd.right = new FormAttachment(btnRemove, -6);
//		btnAdd.setLayoutData(fd_btnAdd);
//		btnAdd.setText("Add");
//		m_bindingContext = initDataBindings();
//
//	}
//
//	public void reAllocateItems() {
//		// table.removeAll();
//		// modelProvider.setTableItems();
//		// table.redraw();
//
//		table.removeAll();
//		table.redraw();
//		modelProvider.setTableItems();
//	}
//
//	public Table getTable() {
//		return table;
//	}
//
//	protected DataBindingContext initDataBindings() {
//		DataBindingContext bindingContext = new DataBindingContext();
//		//
//		IObservableValue textObserveTextObserveWidget = SWTObservables
//				.observeText(text, SWT.Modify);
//		IObservableValue modelProviderFilePathObserveValue = BeansObservables
//				.observeValue(modelProvider, "filePath");
//		bindingContext.bindValue(textObserveTextObserveWidget,
//				modelProviderFilePathObserveValue, null, null);
//		//
//		IObservableValue btnUploadCsvObserveEnabledObserveWidget = SWTObservables
//				.observeEnabled(btnUploadCsv);
//		IObservableValue modelProviderUploadCSVButtonEnabledObserveValue = BeansObservables
//				.observeValue(modelProvider, "uploadCSVButtonEnabled");
//		bindingContext.bindValue(btnUploadCsvObserveEnabledObserveWidget,
//				modelProviderUploadCSVButtonEnabledObserveValue, null, null);
//		//
//		IObservableValue btnSendObserveEnabledObserveWidget = SWTObservables
//				.observeEnabled(btnSend);
//		IObservableValue modelProviderSendButtonEnabledObserveValue = BeansObservables
//				.observeValue(modelProvider, "sendButtonEnabled");
//		bindingContext.bindValue(btnSendObserveEnabledObserveWidget,
//				modelProviderSendButtonEnabledObserveValue, null, null);
//		//
//		return bindingContext;
//	}
//}
