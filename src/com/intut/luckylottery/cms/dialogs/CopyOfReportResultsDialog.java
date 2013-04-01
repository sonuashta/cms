package com.intut.luckylottery.cms.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.SearchResult;
import com.intut.luckylottery.cms.modelProviders.CopyOfReportDialogModelProvider;
import com.intut.luckylottery.cms.modelProviders.CopyOfReportResultModelProvider;
import com.intut.luckylottery.cms.modelProviders.ReportResultModelProvider;
import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;

public class CopyOfReportResultsDialog extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shlResults;
	private Table table;
	private CopyOfReportResultModelProvider
	modelProvider;
	private Button btnPrevious;
	private Button btnNext;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param searchResult
	 * @param style
	 */
	public CopyOfReportResultsDialog(Shell parent, SearchResult searchResult) {
		super(parent);
		setText("SWT Dialog");
		modelProvider = new CopyOfReportResultModelProvider(searchResult);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlResults.open();
		shlResults.layout();
		Display display = getParent().getDisplay();
		while (!shlResults.isDisposed()) {
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
		shlResults = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlResults.setBounds(Util.setBouunds(950, 620));
//		shlResults.setSize(950, 620);
		shlResults.setText("Results");
		shlResults.setLayout(new FormLayout());

		Group grpJkk = new Group(shlResults, SWT.NONE);
		grpJkk.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		FormLayout fl_grpJkk = new FormLayout();
		fl_grpJkk.marginTop = 5;
		fl_grpJkk.marginBottom = 5;
		grpJkk.setLayout(fl_grpJkk);
		FormData fd_grpJkk = new FormData();

		fd_grpJkk.right = new FormAttachment(100, -10);
		fd_grpJkk.top = new FormAttachment(0,10);
		fd_grpJkk.left = new FormAttachment(0, 10);
		grpJkk.setLayoutData(fd_grpJkk);

		btnPrevious = new Button(grpJkk, SWT.NONE);
		btnPrevious.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/previous.png"));
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.setNextButton(true);
				boolean status = openProgressDialog(true);
				if (status) {

					int count = modelProvider.getSearchResult().searchFilter
							.getPageNumber()
							* modelProvider.getSearchResult().searchFilter
									.getPageCount();
					modelProvider.setLabelMessage("Showing results "
							+ (count - Constants.pageCount + 1) + "-" + count
							+ " of "
							+ modelProvider.getSearchResult().totalResults);

					if (modelProvider.getSearchResult().searchFilter
							.getPageNumber() == 1)
						modelProvider.setPreviousButton(false);
					else {
						modelProvider.setPreviousButton(true);
					}
					table.removeAll();
					table.redraw();

					reallocatesItem();
				} else {
					MessageDialog.openError(shlResults, "Error",
							"Error in retrieving results.");
				}

			}
		});
		FormData fd_btnPrevious = new FormData();
		fd_btnPrevious.left = new FormAttachment(0, 4);
		fd_btnPrevious.top = new FormAttachment(0);
		btnPrevious.setLayoutData(fd_btnPrevious);

		btnNext = new Button(grpJkk, SWT.NONE);
		btnNext.setImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/next.png"));
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean status = openProgressDialog(false);
				if (status) {
					modelProvider.setPreviousButton(true);
					int count = modelProvider.getSearchResult().searchFilter
							.getPageNumber()
							* modelProvider.getSearchResult().searchFilter
									.getPageCount();
					if(modelProvider.getSearchResult().totalResults<=count)

					modelProvider.setLabelMessage("Showing results "
							+ (count - Constants.pageCount + 1) + "-" + modelProvider.getSearchResult().totalResults);
					else
						modelProvider.setLabelMessage("Showing results "
								+ (count - Constants.pageCount + 1) + "-" + count
								+ " of "
								+ modelProvider.getSearchResult().totalResults);
					if (count >= modelProvider.getSearchResult().totalResults)
						{modelProvider.setNextButton(false);table.removeAll();
						table.redraw();

						reallocatesItem();
}
					else {
						modelProvider.setNextButton(true);
						table.removeAll();
						table.redraw();

						reallocatesItem();
					}
				} else {
					MessageDialog.openError(shlResults, "Error",
							"Error in retrieving results.");
				}

			}
		});
		FormData fd_btnNext = new FormData();
		fd_btnNext.top = new FormAttachment(0);
		fd_btnNext.left = new FormAttachment(btnPrevious, 6);
		btnNext.setLayoutData(fd_btnNext);

		table = new Table(shlResults, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(grpJkk, 6);
		fd_table.bottom = new FormAttachment(100, -10);
		fd_table.right = new FormAttachment(100, -10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(126);
		tblclmnName.setText("Name");

		TableColumn tblclmnLotteryType = new TableColumn(table, SWT.NONE);
		tblclmnLotteryType.setWidth(100);
		tblclmnLotteryType.setText("Lottery Type");

		TableColumn tblclmnBumperName = new TableColumn(table, SWT.NONE);
		tblclmnBumperName.setWidth(100);
		tblclmnBumperName.setText("Bumper Name");

		TableColumn tblclmnEmailId = new TableColumn(table, SWT.NONE);
		tblclmnEmailId.setWidth(122);
		tblclmnEmailId.setText("Email id");

		TableColumn tblclmnAddress = new TableColumn(table, SWT.NONE);
		tblclmnAddress.setWidth(171);
		tblclmnAddress.setText("Address");
		
		TableColumn tblclmnPhoneNumber = new TableColumn(table, SWT.NONE);
		tblclmnPhoneNumber.setWidth(100);
		tblclmnPhoneNumber.setText("Phone Number");

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Ticket number");

		TableColumn tblclmnDate = new TableColumn(table, SWT.NONE);
		tblclmnDate.setWidth(100);
		tblclmnDate.setText("Date");
		m_bindingContext = initDataBindings();
		grpJkk.setText(modelProvider.getLabelMessage());
		reallocatesItem();
		modelProvider.setGroup(grpJkk);
		
		Button btnSendMessages = new Button(grpJkk, SWT.NONE);
		btnSendMessages.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modelProvider.sendMessages();
			}
		});
		FormData fd_btnSendMessages = new FormData();
		fd_btnSendMessages.top = new FormAttachment(btnPrevious, 0, SWT.TOP);
		fd_btnSendMessages.left = new FormAttachment(btnNext, 6);
		btnSendMessages.setLayoutData(fd_btnSendMessages);
		btnSendMessages.setText("Send Messages");
	}

	private void reallocatesItem() {
		for (Customer customer : modelProvider.getCustomers()) {
			TableItem item = new TableItem(table, getStyle());
			item.setText(new String[] { customer.getName(),
					customer.getLotteryType(), customer.getBumperName(),
					customer.getEmailId(), customer.getAddress(),customer.getPhoneNumber(),
					customer.getTicketNumber(),
					Util.getResultFormatedDate(customer.getDate()) });
		}
	}

	private boolean status;

	public boolean openProgressDialog(final boolean isPrevious) {
		status = true;
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Searching Results ....", 2);
					// begin task
					monitor.worked(1);

					try {
						if (isPrevious)
							modelProvider.searchPreviousResults();
						else
							modelProvider.searchNextResults();
					} catch (Exception e) {
						MessageDialog.openError(shlResults, "Error",
								e.getMessage());
						status = false;
					}

					monitor.setTaskName("Done");

					monitor.done();

				}
			});
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Progress Dialog Error:" + e.getMessage());

		}
		return status;
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue btnPreviousObserveEnabledObserveWidget = SWTObservables
				.observeEnabled(btnPrevious);
		IObservableValue modelProviderPreviousButtonObserveValue = BeansObservables
				.observeValue(modelProvider, "previousButton");
		bindingContext.bindValue(btnPreviousObserveEnabledObserveWidget,
				modelProviderPreviousButtonObserveValue, null, null);
		//
		IObservableValue btnNextObserveEnabledObserveWidget = SWTObservables
				.observeEnabled(btnNext);
		IObservableValue modelProviderNextButtonObserveValue = BeansObservables
				.observeValue(modelProvider, "nextButton");
		bindingContext.bindValue(btnNextObserveEnabledObserveWidget,
				modelProviderNextButtonObserveValue, null, null);
		//
		return bindingContext;
	}
}
