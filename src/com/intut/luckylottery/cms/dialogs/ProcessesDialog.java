package com.intut.luckylottery.cms.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.crudDatabase.Dbloader;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class ProcessesDialog extends Dialog {

	protected Object result;
	protected Shell shlProcesses;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ProcessesDialog(Shell parent) {
		super(parent);
		setText("SWT Dialog");
	}

	private ScrolledComposite scrolledComposite;
	private Composite composite_1;
	private Text text;

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlProcesses.open();
		shlProcesses.layout();
		Display display = getParent().getDisplay();
		while (!shlProcesses.isDisposed()) {
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
		shlProcesses = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		shlProcesses.setBounds(Util.setBouunds(800, 600));
		shlProcesses.setText("Processes");
		shlProcesses.setLayout(new FormLayout());

		final Button btnCreateNewProcess = new Button(shlProcesses, SWT.NONE);
		btnCreateNewProcess.setEnabled(false);
		FormData fd_btnCreateNewProcess = new FormData();
		fd_btnCreateNewProcess.right = new FormAttachment(100, -10);
		btnCreateNewProcess.setLayoutData(fd_btnCreateNewProcess);
		btnCreateNewProcess.setText("Create New Process");
		btnCreateNewProcess.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dbloader loader = new Dbloader();
				String processName = text.getText();
				try {

					boolean isValid = loader.insertProcess(processName);
					if (!isValid) {
						MessageDialog.openError(shlProcesses, "Already Exists",
								"Please Select a different name.");
					} else {
						loader.createMessageTable(processName);
						addProcess(composite_1, processName);
					}
				} catch (Exception e1) {
					MessageDialog.openError(shlProcesses, "Error", "");
				}

			}
		});

		Composite composite = new Composite(shlProcesses, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.bottom = new FormAttachment(100, -10);
		fd_composite.right = new FormAttachment(100, -10);
		composite.setLayoutData(fd_composite);

		scrolledComposite = new ScrolledComposite(composite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		Label lblAddNewProcess = new Label(shlProcesses, SWT.NONE);
		fd_btnCreateNewProcess.top = new FormAttachment(lblAddNewProcess, -3,
				SWT.TOP);
		lblAddNewProcess.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblAddNewProcess = new FormData();
		fd_lblAddNewProcess.top = new FormAttachment(0, 10);
		fd_lblAddNewProcess.left = new FormAttachment(0, 10);
		lblAddNewProcess.setLayoutData(fd_lblAddNewProcess);
		lblAddNewProcess.setText("Add New Process");

		text = new Text(shlProcesses, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (Util.isStringNullOrEmpty(text.getText()))
					btnCreateNewProcess.setEnabled(false);
				else
					btnCreateNewProcess.setEnabled(true);
			}
		});
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(btnCreateNewProcess, -6);
		fd_text.top = new FormAttachment(0, 10);
		fd_text.left = new FormAttachment(lblAddNewProcess, 6);
		text.setLayoutData(fd_text);

		Label label_1 = new Label(shlProcesses, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(btnCreateNewProcess, 14);
		fd_label_1.bottom = new FormAttachment(btnCreateNewProcess, 16,
				SWT.BOTTOM);
		fd_label_1.right = new FormAttachment(100, -10);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);

		Label lblExistingProcesses = new Label(shlProcesses, SWT.NONE);
		fd_composite.top = new FormAttachment(lblExistingProcesses, 6);
		lblExistingProcesses.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.BOLD));
		FormData fd_lblExistingProcesses = new FormData();
		fd_lblExistingProcesses.right = new FormAttachment(btnCreateNewProcess,
				0, SWT.RIGHT);
		fd_lblExistingProcesses.top = new FormAttachment(label_1, 3);
		fd_lblExistingProcesses.left = new FormAttachment(0, 10);
		lblExistingProcesses.setLayoutData(fd_lblExistingProcesses);
		lblExistingProcesses.setText("Existing Processes");

		try {
			Dbloader loader = new Dbloader();
			List<String> processes = loader.getProcesses();
			for (String process : processes)
				addProcess(composite_1, process);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void addProcess(Composite composite_1, final String processName) {
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new FormLayout());
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_2.heightHint = 44;
		gd_composite_2.widthHint = 433;
		composite_2.setLayoutData(gd_composite_2);

		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();

		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);

		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText(processName);

		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProcessDailog dialog = new ProcessDailog(shlProcesses,
						processName);
				dialog.open();
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(lblNewLabel, -5, SWT.TOP);
		fd_btnNewButton.left = new FormAttachment(lblNewLabel, 6);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("View/Start Process");
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	}
}
