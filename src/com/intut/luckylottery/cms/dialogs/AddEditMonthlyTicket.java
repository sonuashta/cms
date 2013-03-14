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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;

import com.intut.luckylottery.cms.modelProviders.AddEditTicketModelProvider;
import com.intut.luckylottery.cms.modelProviders.ManualEntrydialogModelProvider;
import com.intut.luckylottery.cms.util.Util;

import dummydata.GetDummyData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.BeansObservables;

public class AddEditMonthlyTicket extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shlLotteryInformation;
	private Text text;
	private Text text_1;
	private AddEditTicketModelProvider modelProvider;
	private ManualEntrydialogModelProvider manualModelProvider;
	private boolean isBumper;
	private Combo combo;
	private boolean isEdit;
	private int selectionIndex;
	private String bumperName;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param bumperName
	 * @param style
	 */
	public AddEditMonthlyTicket(Shell parent,
			ManualEntrydialogModelProvider manualModelProvider,
			boolean isBumper, String series, String ticketNumber,
			String bumperName, boolean isEdit, int selectionIndex) {
		super(parent);
		setText("SWT Dialog");
		modelProvider = new AddEditTicketModelProvider(series, ticketNumber);
		this.isBumper = isBumper;
		this.manualModelProvider = manualModelProvider;
		this.isEdit = isEdit;
		this.selectionIndex = selectionIndex;
		this.bumperName = bumperName;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlLotteryInformation.open();
		shlLotteryInformation.layout();
		Display display = getParent().getDisplay();
		while (!shlLotteryInformation.isDisposed()) {
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
		shlLotteryInformation = new Shell(getParent());
		shlLotteryInformation.setSize(359, 245);
		shlLotteryInformation.setText("Lottery Information");
		shlLotteryInformation.setLayout(new FormLayout());

		Label lblSeries = new Label(shlLotteryInformation, SWT.NONE);
		FormData fd_lblSeries = new FormData();
		fd_lblSeries.top = new FormAttachment(0, 10);
		fd_lblSeries.left = new FormAttachment(0, 10);
		lblSeries.setLayoutData(fd_lblSeries);
		lblSeries.setText("Series");

		text = new Text(shlLotteryInformation, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblSeries, 6);
		fd_text.left = new FormAttachment(lblSeries, 0, SWT.LEFT);
		text.setLayoutData(fd_text);

		Label lblTicketNumber = new Label(shlLotteryInformation, SWT.NONE);
		FormData fd_lblTicketNumber = new FormData();
		fd_lblTicketNumber.top = new FormAttachment(text, 6);
		fd_lblTicketNumber.left = new FormAttachment(lblSeries, 0, SWT.LEFT);
		lblTicketNumber.setLayoutData(fd_lblTicketNumber);
		lblTicketNumber.setText("Ticket Number");

		text_1 = new Text(shlLotteryInformation, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.top = new FormAttachment(lblTicketNumber, 6);
		fd_text_1.left = new FormAttachment(lblSeries, 0, SWT.LEFT);
		text_1.setLayoutData(fd_text_1);

		Button btnDone = new Button(shlLotteryInformation, SWT.NONE);
		fd_text.right = new FormAttachment(btnDone, 0, SWT.RIGHT);
		btnDone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				
				shlLotteryInformation.close();

			}
		});
		FormData fd_btnDone = new FormData();
		fd_btnDone.bottom = new FormAttachment(100, -10);
		fd_btnDone.right = new FormAttachment(100, -10);
		btnDone.setLayoutData(fd_btnDone);
		btnDone.setText("Done");

		Composite composite = new Composite(shlLotteryInformation, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_composite.bottom = new FormAttachment(100, -51);
		fd_composite.top = new FormAttachment(text_1, 6);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		if (!isBumper)
			composite.setVisible(false);
		Label lblBumperName = new Label(composite, SWT.NONE);
		FormData fd_lblBumperName = new FormData();
		fd_lblBumperName.top = new FormAttachment(0, 10);
		fd_lblBumperName.left = new FormAttachment(0, 10);
		lblBumperName.setLayoutData(fd_lblBumperName);
		lblBumperName.setText("Bumper Name");

		combo = new Combo(composite, SWT.NONE);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(0, 31);
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		combo.setItems(GetDummyData.getBumperNames());
		
		Label label = new Label(shlLotteryInformation, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(btnDone, -8, SWT.TOP);
		fd_label.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_label.bottom = new FormAttachment(btnDone, -6);
		fd_label.left = new FormAttachment(lblSeries, 0, SWT.LEFT);
		label.setLayoutData(fd_label);

		m_bindingContext = initDataBindings();

		if (isBumper)
			combo.setText(combo.getItem(0));
		if (!Util.isStringNullOrEmpty(bumperName)) {

			combo.setText(bumperName);
		}
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables
				.observeText(text, SWT.Modify);
		IObservableValue modelProviderSeriesObserveValue = BeansObservables
				.observeValue(modelProvider, "series");
		bindingContext.bindValue(textObserveTextObserveWidget,
				modelProviderSeriesObserveValue, null, null);
		//
		IObservableValue text_1ObserveTextObserveWidget = SWTObservables
				.observeText(text_1, SWT.Modify);
		IObservableValue modelProviderTicketNumberObserveValue = BeansObservables
				.observeValue(modelProvider, "ticketNumber");
		bindingContext.bindValue(text_1ObserveTextObserveWidget,
				modelProviderTicketNumberObserveValue, null, null);
		//
		IObservableValue comboObserveTextObserveWidget = SWTObservables
				.observeText(combo);
		IObservableValue modelProviderSelectedBumperObserveValue = BeansObservables
				.observeValue(modelProvider, "selectedBumper");
		bindingContext.bindValue(comboObserveTextObserveWidget,
				modelProviderSelectedBumperObserveValue, null, null);
		//
		return bindingContext;
	}
}
