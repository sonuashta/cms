package com.intut.luckylottery.cms.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.intut.luckylottery.cms.customEvents.PropertyChangeEvent;
import com.intut.luckylottery.cms.customEvents.PropertyChangeListener;
import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.modelProviders.ProcessDialogModelProvider;
import com.intut.luckylottery.cms.modelProviders.ProcessesProgressDialogModelProvider;
import com.intut.luckylottery.cms.modelProviders.ProgressDialogModelProvider;
import com.intut.luckylottery.cms.util.Util;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.wb.swt.SWTResourceManager;

public class ProcessesProgressDialog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shell;
	private Text text;
	private ProcessesProgressDialogModelProvider modelProvider;
	private ProgressBar progressBar;
	private Label lblProgress;


	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ProcessesProgressDialog(Shell parent, List<Customer> customers,
			boolean isMail, String text,String tableName , ProcessDialogModelProvider proceesDailogModelProvider) {
		super(parent);
		setText("SWT Dialog");
		modelProvider = new ProcessesProgressDialogModelProvider(customers,
				isMail, text,tableName,proceesDailogModelProvider);
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
		shell.setSize(450, 300);
		shell.setBounds(Util.setBouunds(450, 300));
		shell.setText(getText());
		shell.setLayout(new FormLayout());

		lblProgress = new Label(shell, SWT.NONE);
		FormData fd_lblProgress = new FormData();
		fd_lblProgress.right = new FormAttachment(100, -10);
		fd_lblProgress.top = new FormAttachment(0, 10);
		fd_lblProgress.left = new FormAttachment(0, 10);
		lblProgress.setLayoutData(fd_lblProgress);
		lblProgress.setText("Progress");

		progressBar = new ProgressBar(shell, SWT.NONE);
		FormData fd_progressBar = new FormData();
		fd_progressBar.top = new FormAttachment(lblProgress, 6);
		fd_progressBar.right = new FormAttachment(0, 434);
		fd_progressBar.left = new FormAttachment(0, 10);
		progressBar.setLayoutData(fd_progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(modelProvider.getCustomers().size());
		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
				| SWT.V_SCROLL);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FormData fd_text = new FormData();
		fd_text.bottom = new FormAttachment(progressBar, 214, SWT.BOTTOM);
		fd_text.top = new FormAttachment(progressBar, 6);
		fd_text.right = new FormAttachment(progressBar, 0, SWT.RIGHT);
		fd_text.left = new FormAttachment(0, 10);
		text.setLayoutData(fd_text);
		modelProvider.addCustomEventListener(new PropertyChangeListener() {

			@Override
			public void propertyChanged(final PropertyChangeEvent evt) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						progressBar.setSelection(evt.getProgressValue());
					}
				});

			}
		});
		m_bindingContext = initDataBindings();
		modelProvider.sendSMS();

	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue lblProgressObserveTextObserveWidget = SWTObservables
				.observeText(lblProgress);
		IObservableValue modelProviderLabelMessageObserveValue = BeansObservables
				.observeValue(modelProvider, "labelMessage");
		bindingContext.bindValue(lblProgressObserveTextObserveWidget,
				modelProviderLabelMessageObserveValue, null, null);
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables
				.observeText(text, SWT.Modify);
		IObservableValue modelProviderLogMessageObserveValue = BeansObservables
				.observeValue(modelProvider, "logMessage");
		bindingContext.bindValue(textObserveTextObserveWidget,
				modelProviderLogMessageObserveValue, null, null);
		//
		return bindingContext;
	}
}
