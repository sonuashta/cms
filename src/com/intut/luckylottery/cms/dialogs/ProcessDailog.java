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
import org.eclipse.swt.widgets.Button;

import com.intut.luckylottery.cms.modelProviders.ProcessDialogModelProvider;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ProcessDailog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shlProcess;
	private Text text;
	private Text text_1;
	private Text text_2;
	private ProcessDialogModelProvider modelProvider;
	private Text text_3;
	private Text text_4;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ProcessDailog(Shell parent, String processName) {
		super(parent);
		setText("SWT Dialog");
		modelProvider = new ProcessDialogModelProvider(processName);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlProcess.open();
		shlProcess.layout();
		Display display = getParent().getDisplay();
		while (!shlProcess.isDisposed()) {
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
		shlProcess = new Shell(getParent());
		shlProcess.setSize(450, 300);
		shlProcess.setText("Process");
		shlProcess.setLayout(new FormLayout());

		Label lblProcessName = new Label(shlProcess, SWT.NONE);
		lblProcessName.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblProcessName = new FormData();
		fd_lblProcessName.top = new FormAttachment(0, 10);
		fd_lblProcessName.left = new FormAttachment(0, 10);
		lblProcessName.setLayoutData(fd_lblProcessName);
		lblProcessName.setText("Process Name");

		Label lblTotalCustomers = new Label(shlProcess, SWT.NONE);
		lblTotalCustomers.setFont(SWTResourceManager.getFont("Segoe UI", 8,
				SWT.BOLD));
		FormData fd_lblTotalCustomers = new FormData();
		fd_lblTotalCustomers.left = new FormAttachment(lblProcessName, 0,
				SWT.LEFT);
		lblTotalCustomers.setLayoutData(fd_lblTotalCustomers);
		lblTotalCustomers.setText("Total Number of Messages");

		text = new Text(shlProcess, SWT.BORDER);
		fd_lblTotalCustomers.top = new FormAttachment(text, 7);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblProcessName, 6);
		fd_text.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		text.setLayoutData(fd_text);

		text_1 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.top = new FormAttachment(lblTotalCustomers, 6);
		fd_text_1.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		text_1.setLayoutData(fd_text_1);

		Label lblProcessesLeft = new Label(shlProcess, SWT.NONE);
		lblProcessesLeft.setFont(SWTResourceManager.getFont("Segoe UI", 8,
				SWT.BOLD));
		FormData fd_lblProcessesLeft = new FormData();
		fd_lblProcessesLeft.top = new FormAttachment(text_1, 6);
		fd_lblProcessesLeft.left = new FormAttachment(lblProcessName, 0,
				SWT.LEFT);
		lblProcessesLeft.setLayoutData(fd_lblProcessesLeft);
		lblProcessesLeft.setText("Messages Left");

		text_2 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_2 = new FormData();
		fd_text_2.top = new FormAttachment(lblProcessesLeft, 6);
		fd_text_2.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		text_2.setLayoutData(fd_text_2);

		Button btnNewButton = new Button(shlProcess, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.processMessages();
			}
		});
		fd_text.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(100, -10);
		fd_btnNewButton.right = new FormAttachment(100, -10);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Process Messages");

		Label lblTotalEmails = new Label(shlProcess, SWT.NONE);
		lblTotalEmails.setFont(SWTResourceManager.getFont("Segoe UI", 8,
				SWT.BOLD));
		FormData fd_lblTotalEmails = new FormData();
		fd_lblTotalEmails.top = new FormAttachment(text_2, 6);
		fd_lblTotalEmails.left = new FormAttachment(0, 10);
		lblTotalEmails.setLayoutData(fd_lblTotalEmails);
		lblTotalEmails.setText("Total Emails");

		text_3 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_3 = new FormData();
		fd_text_3.top = new FormAttachment(lblTotalEmails, 6);
		fd_text_3.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		text_3.setLayoutData(fd_text_3);

		Label lblNewLabel = new Label(shlProcess, SWT.NONE);
		lblNewLabel
				.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(text_3, 6);
		fd_lblNewLabel.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Emails to be Processed");

		text_4 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_4 = new FormData();
		fd_text_4.top = new FormAttachment(lblNewLabel, 4);
		fd_text_4.left = new FormAttachment(lblProcessName, 0, SWT.LEFT);
		text_4.setLayoutData(fd_text_4);

		Button btnProcessmails = new Button(shlProcess, SWT.NONE);
		btnProcessmails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.processMails();
			}
		});
		FormData fd_btnProcessmails = new FormData();
		fd_btnProcessmails.bottom = new FormAttachment(btnNewButton, 0,
				SWT.BOTTOM);
		fd_btnProcessmails.right = new FormAttachment(btnNewButton, -6);
		btnProcessmails.setLayoutData(fd_btnProcessmails);
		btnProcessmails.setText("Process Mails");
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
		IObservableValue modelProviderTotalObserveValue = BeansObservables
				.observeValue(modelProvider, "total");
		bindingContext.bindValue(text_1ObserveTextObserveWidget,
				modelProviderTotalObserveValue, null, null);
		//
		IObservableValue text_2ObserveTextObserveWidget = SWTObservables
				.observeText(text_2, SWT.Modify);
		IObservableValue modelProviderLeftProcessesObserveValue = BeansObservables
				.observeValue(modelProvider, "leftProcesses");
		bindingContext.bindValue(text_2ObserveTextObserveWidget,
				modelProviderLeftProcessesObserveValue, null, null);
		//
		IObservableValue text_3ObserveTextObserveWidget = SWTObservables
				.observeText(text_3, SWT.Modify);
		IObservableValue modelProviderTotalEmailsObserveValue = BeansObservables
				.observeValue(modelProvider, "totalEmails");
		bindingContext.bindValue(text_3ObserveTextObserveWidget,
				modelProviderTotalEmailsObserveValue, null, null);
		//
		IObservableValue text_4ObserveTextObserveWidget = SWTObservables
				.observeText(text_4, SWT.Modify);
		IObservableValue modelProviderLeftMailsObserveValue = BeansObservables
				.observeValue(modelProvider, "leftMails");
		bindingContext.bindValue(text_4ObserveTextObserveWidget,
				modelProviderLeftMailsObserveValue, null, null);
		//
		return bindingContext;
	}
}
