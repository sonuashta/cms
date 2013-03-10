package com.intut.luckylottery.cms.dialogs;

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

import com.intut.luckylottery.crudDatabase.Dbloader;

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
		shlProcesses = new Shell(getParent());
		
		shlProcesses.setSize(800, 600);
		shlProcesses.setText("Processes");
		shlProcesses.setLayout(new FormLayout());

		Composite composite = new Composite(shlProcesses, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100, -10);
		composite.setLayoutData(fd_composite);

		Button btnCreateNewProcess = new Button(shlProcesses, SWT.NONE);

		FormData fd_btnCreateNewProcess = new FormData();
		fd_btnCreateNewProcess.right = new FormAttachment(100, -10);
		btnCreateNewProcess.setLayoutData(fd_btnCreateNewProcess);
		btnCreateNewProcess.setText("Create New Process");

		Label label = new Label(shlProcesses, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_composite.bottom = new FormAttachment(label, -6);
		fd_btnCreateNewProcess.top = new FormAttachment(label, 6);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 10);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(100, -41);

		scrolledComposite = new ScrolledComposite(composite, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		label.setLayoutData(fd_label);
		btnCreateNewProcess.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dbloader loader = new Dbloader();
				String processName = "Holi";
				try {

					// loader.insertProcess(processName);
					// loader.createMessageTable(processName);
				} catch (Exception e1) {
					MessageDialog.openError(shlProcesses, "Error", "");
				}
				addProcess(composite_1, processName);
			}
		});

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
				ProcessDailog dialog = new ProcessDailog(shlProcesses, processName);
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
