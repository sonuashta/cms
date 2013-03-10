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

public class ProcessDailog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shlProcess;
	private Text text;
	private Text text_1;
	private Text text_2;
	private ProcessDialogModelProvider modelProvider;

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
		FormData fd_lblProcessName = new FormData();
		fd_lblProcessName.top = new FormAttachment(0, 10);
		fd_lblProcessName.left = new FormAttachment(0, 10);
		lblProcessName.setLayoutData(fd_lblProcessName);
		lblProcessName.setText("Process Name");

		Label lblTotalCustomers = new Label(shlProcess, SWT.NONE);
		FormData fd_lblTotalCustomers = new FormData();
		fd_lblTotalCustomers.left = new FormAttachment(lblProcessName, 0,
				SWT.LEFT);
		lblTotalCustomers.setLayoutData(fd_lblTotalCustomers);
		lblTotalCustomers.setText("Total Customers");

		text = new Text(shlProcess, SWT.BORDER);
		fd_lblTotalCustomers.top = new FormAttachment(text, 6);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblProcessName, 0, SWT.TOP);
		fd_text.left = new FormAttachment(lblProcessName, 6);
		text.setLayoutData(fd_text);

		text_1 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.top = new FormAttachment(lblTotalCustomers, -3, SWT.TOP);
		fd_text_1.left = new FormAttachment(lblTotalCustomers, 16);
		text_1.setLayoutData(fd_text_1);

		Label lblProcessesLeft = new Label(shlProcess, SWT.NONE);
		FormData fd_lblProcessesLeft = new FormData();
		fd_lblProcessesLeft.top = new FormAttachment(lblTotalCustomers, 17);
		fd_lblProcessesLeft.left = new FormAttachment(0, 10);
		lblProcessesLeft.setLayoutData(fd_lblProcessesLeft);
		lblProcessesLeft.setText("Processes Left");

		text_2 = new Text(shlProcess, SWT.BORDER);
		FormData fd_text_2 = new FormData();
		fd_text_2.bottom = new FormAttachment(lblProcessesLeft, 0, SWT.BOTTOM);
		fd_text_2.left = new FormAttachment(text, 0, SWT.LEFT);
		text_2.setLayoutData(fd_text_2);

		Button btnNewButton = new Button(shlProcess, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(100, -10);
		fd_btnNewButton.right = new FormAttachment(100, -10);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Process");
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
		IObservableValue modelProviderTotalObserveValue = BeansObservables.observeValue(modelProvider, "total");
		bindingContext.bindValue(text_1ObserveTextObserveWidget, modelProviderTotalObserveValue, null, null);
		//
		IObservableValue text_2ObserveTextObserveWidget = SWTObservables.observeText(text_2, SWT.Modify);
		IObservableValue modelProviderLeftProcessesObserveValue = BeansObservables.observeValue(modelProvider, "leftProcesses");
		bindingContext.bindValue(text_2ObserveTextObserveWidget, modelProviderLeftProcessesObserveValue, null, null);
		//
		return bindingContext;
	}
}
