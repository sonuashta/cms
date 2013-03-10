package com.intut.luckylottery.cms.dialogs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Fields;
import com.intut.luckylottery.tests.ReadExcel;

import dummydata.GetDummyData;

public class DataLoaderDialog extends Dialog {

	protected Object result;
	protected Shell shlUploadFiles;
	private Text monMonthlyText;
	private Text bumBumperText;
	private Text bothMonthlyText;
	private Text bothBumperText;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DataLoaderDialog(Shell parent) {
		super(parent);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlUploadFiles.open();
		shlUploadFiles.layout();
		Display display = getParent().getDisplay();
		while (!shlUploadFiles.isDisposed()) {
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
		String comboItems[] = GetDummyData.getBumperNames();
		shlUploadFiles = new Shell(getParent());
		shlUploadFiles.setSize(450, 412);
		shlUploadFiles.setText("Upload Files");
		shlUploadFiles.setLayout(new FormLayout());

		TabFolder tabFolder = new TabFolder(shlUploadFiles, SWT.NONE);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.top = new FormAttachment(0, 10);
		fd_tabFolder.left = new FormAttachment(0, 10);
		fd_tabFolder.bottom = new FormAttachment(0, 374);
		fd_tabFolder.right = new FormAttachment(0, 434);
		tabFolder.setLayoutData(fd_tabFolder);

		TabItem tbtmMonthly = new TabItem(tabFolder, SWT.NONE);
		tbtmMonthly.setText("Bumper");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmMonthly.setControl(composite);
		composite.setLayout(new FormLayout());

		Label lblChooseFile = new Label(composite, SWT.NONE);
		FormData fd_lblChooseFile = new FormData();
		fd_lblChooseFile.top = new FormAttachment(0, 10);
		fd_lblChooseFile.left = new FormAttachment(0, 10);
		lblChooseFile.setLayoutData(fd_lblChooseFile);
		lblChooseFile.setText("Choose File");

		monMonthlyText = new Text(composite, SWT.BORDER);
		FormData fd_monMonthlyText = new FormData();
		fd_monMonthlyText.left = new FormAttachment(0, 10);
		fd_monMonthlyText.top = new FormAttachment(lblChooseFile, 6);
		monMonthlyText.setLayoutData(fd_monMonthlyText);

		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlUploadFiles);
				String filePath = dialog.open();
				if (!Util.isStringNullOrEmpty(filePath))
					monMonthlyText.setText(filePath);
			}
		});
		fd_monMonthlyText.right = new FormAttachment(btnBrowse, -6);
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.top = new FormAttachment(0, 29);
		fd_btnBrowse.right = new FormAttachment(100, -10);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.setText("Browse");

		Label lblChooseBumperType = new Label(composite, SWT.NONE);
		FormData fd_lblChooseBumperType = new FormData();
		fd_lblChooseBumperType.top = new FormAttachment(monMonthlyText, 6);
		fd_lblChooseBumperType.left = new FormAttachment(lblChooseFile, 0,
				SWT.LEFT);
		lblChooseBumperType.setLayoutData(fd_lblChooseBumperType);
		lblChooseBumperType.setText("Choose Bumper Type");

		final Combo combo = new Combo(composite, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(lblChooseBumperType, 6);
		fd_combo.left = new FormAttachment(lblChooseFile, 0, SWT.LEFT);
		combo.setLayoutData(fd_combo);
		combo.setItems(comboItems);
		combo.setText(combo.getItem(0));
		Button btnLoad_1 = new Button(composite, SWT.NONE);
		btnLoad_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File monthlyFile = new File(monMonthlyText.getText());

				List<Customer> customers = new ArrayList<Customer>();
				ReadExcel excel = new ReadExcel();
				try {
					customers.addAll(excel.read(monthlyFile, Fields.typeBumper,
							combo.getText()));
				} catch (IOException e1) {
					MessageDialog.openError(shlUploadFiles, "Error",
							"Error in Reading file");
				}

				MessageDialog.openInformation(shlUploadFiles, "Info", "Found "
						+ customers.size() + " records.");
				ProgressDialog dialog = new ProgressDialog(Display.getCurrent()
						.getActiveShell(), customers);
				dialog.open();
			}
		});
		FormData fd_btnLoad_1 = new FormData();
		fd_btnLoad_1.bottom = new FormAttachment(100, -10);
		fd_btnLoad_1.right = new FormAttachment(btnBrowse, 0, SWT.RIGHT);
		btnLoad_1.setLayoutData(fd_btnLoad_1);
		btnLoad_1.setText("Load");

		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Monthly");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_1);
		composite_1.setLayout(new FormLayout());

		Label lblChooseFile_1 = new Label(composite_1, SWT.NONE);
		FormData fd_lblChooseFile_1 = new FormData();
		fd_lblChooseFile_1.top = new FormAttachment(0, 10);
		fd_lblChooseFile_1.left = new FormAttachment(0, 10);
		lblChooseFile_1.setLayoutData(fd_lblChooseFile_1);
		lblChooseFile_1.setText("Choose File");

		bumBumperText = new Text(composite_1, SWT.BORDER);
		FormData fd_bumBumperText = new FormData();
		fd_bumBumperText.top = new FormAttachment(0, 10);
		fd_bumBumperText.left = new FormAttachment(lblChooseFile_1, 6);
		bumBumperText.setLayoutData(fd_bumBumperText);

		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlUploadFiles);
				String filePath = dialog.open();
				if (!Util.isStringNullOrEmpty(filePath))
					bumBumperText.setText(filePath);
			}
		});
		fd_bumBumperText.right = new FormAttachment(btnNewButton, -6);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(lblChooseFile_1, 0, SWT.TOP);
		fd_btnNewButton.right = new FormAttachment(100, -10);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Browse");

		Button btnLoad = new Button(composite_1, SWT.NONE);
		btnLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File monthlyFile = new File(bumBumperText.getText());

				List<Customer> customers = new ArrayList<Customer>();
				ReadExcel excel = new ReadExcel();
				try {
					customers.addAll(excel.read(monthlyFile,
							Fields.typeMonthly, ""));
				} catch (IOException e1) {
					MessageDialog.openError(shlUploadFiles, "Error",
							"Error in Reading file");
				}

				MessageDialog.openInformation(shlUploadFiles, "Info", "Found "
						+ customers.size() + " records.");
				ProgressDialog dialog = new ProgressDialog(Display.getCurrent()
						.getActiveShell(), customers);
				dialog.open();
			}
		});
		FormData fd_btnLoad = new FormData();
		fd_btnLoad.bottom = new FormAttachment(100, -10);
		fd_btnLoad.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		btnLoad.setLayoutData(fd_btnLoad);
		btnLoad.setText("Load");

		TabItem tbtmBoth = new TabItem(tabFolder, SWT.NONE);
		tbtmBoth.setText("Both");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmBoth.setControl(composite_2);
		composite_2.setLayout(new FormLayout());

		Label lblChooseMonthlyFile = new Label(composite_2, SWT.NONE);
		FormData fd_lblChooseMonthlyFile = new FormData();
		fd_lblChooseMonthlyFile.top = new FormAttachment(0, 10);
		fd_lblChooseMonthlyFile.left = new FormAttachment(0, 10);
		lblChooseMonthlyFile.setLayoutData(fd_lblChooseMonthlyFile);
		lblChooseMonthlyFile.setText("Choose Monthly file");

		bothMonthlyText = new Text(composite_2, SWT.BORDER);
		FormData fd_bothMonthlyText = new FormData();
		fd_bothMonthlyText.top = new FormAttachment(lblChooseMonthlyFile, 7);
		fd_bothMonthlyText.left = new FormAttachment(lblChooseMonthlyFile, 0,
				SWT.LEFT);
		bothMonthlyText.setLayoutData(fd_bothMonthlyText);

		Button btnBrowse_1 = new Button(composite_2, SWT.NONE);
		btnBrowse_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlUploadFiles);
				String filePath = dialog.open();
				if (!Util.isStringNullOrEmpty(filePath))
					bothMonthlyText.setText(filePath);

			}
		});
		fd_bothMonthlyText.right = new FormAttachment(btnBrowse_1, -6);
		FormData fd_btnBrowse_1 = new FormData();
		fd_btnBrowse_1.top = new FormAttachment(0, 30);
		fd_btnBrowse_1.right = new FormAttachment(100, -10);
		btnBrowse_1.setLayoutData(fd_btnBrowse_1);
		btnBrowse_1.setText("Browse");

		Label lblChooseBumperFile = new Label(composite_2, SWT.NONE);
		FormData fd_lblChooseBumperFile = new FormData();
		fd_lblChooseBumperFile.top = new FormAttachment(bothMonthlyText, 6);
		fd_lblChooseBumperFile.left = new FormAttachment(lblChooseMonthlyFile,
				0, SWT.LEFT);
		lblChooseBumperFile.setLayoutData(fd_lblChooseBumperFile);
		lblChooseBumperFile.setText("Choose Bumper File");

		bothBumperText = new Text(composite_2, SWT.BORDER);
		FormData fd_bothBumperText = new FormData();
		fd_bothBumperText.right = new FormAttachment(bothMonthlyText, 0,
				SWT.RIGHT);
		fd_bothBumperText.top = new FormAttachment(lblChooseBumperFile, 6);
		fd_bothBumperText.left = new FormAttachment(lblChooseMonthlyFile, 0,
				SWT.LEFT);
		bothBumperText.setLayoutData(fd_bothBumperText);

		Button btnBrowse_2 = new Button(composite_2, SWT.NONE);
		btnBrowse_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlUploadFiles);
				String filePath = dialog.open();
				if (!Util.isStringNullOrEmpty(filePath))
					bothBumperText.setText(filePath);
			}
		});
		FormData fd_btnBrowse_2 = new FormData();
		fd_btnBrowse_2.top = new FormAttachment(btnBrowse_1, 25);
		fd_btnBrowse_2.right = new FormAttachment(btnBrowse_1, 0, SWT.RIGHT);
		btnBrowse_2.setLayoutData(fd_btnBrowse_2);
		btnBrowse_2.setText("Browse");

		Label lblChooseBumperType_1 = new Label(composite_2, SWT.NONE);
		FormData fd_lblChooseBumperType_1 = new FormData();
		fd_lblChooseBumperType_1.top = new FormAttachment(bothBumperText, 6);
		fd_lblChooseBumperType_1.left = new FormAttachment(
				lblChooseMonthlyFile, 0, SWT.LEFT);
		lblChooseBumperType_1.setLayoutData(fd_lblChooseBumperType_1);
		lblChooseBumperType_1.setText("Choose Bumper Type");

		final Combo combo_1 = new Combo(composite_2, SWT.NONE);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.top = new FormAttachment(lblChooseBumperType_1, 3);
		fd_combo_1.left = new FormAttachment(0, 10);
		combo_1.setLayoutData(fd_combo_1);
		combo_1.setItems(comboItems);
		combo_1.setText(combo_1.getItem(0));
		Button btnLoadData = new Button(composite_2, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File monthlyFile = new File(bothMonthlyText.getText());
				File bumperFile = new File(bothBumperText.getText());
				List<Customer> customers = new ArrayList<Customer>();
				ReadExcel excel = new ReadExcel();
				try {
					customers.addAll(excel.read(monthlyFile,
							Fields.typeMonthly, ""));
				} catch (IOException e1) {
					MessageDialog.openError(shlUploadFiles, "Error",
							"Error in Reading file");
				}
				try {
					customers.addAll(excel.read(bumperFile, Fields.typeBumper,
							combo_1.getText()));
				} catch (IOException e1) {
					MessageDialog.openError(shlUploadFiles, "Error",
							"Error in Reading file");
				}

				MessageDialog.openInformation(shlUploadFiles, "Info", "Found "
						+ customers.size() + " records.");
				ProgressDialog dialog = new ProgressDialog(Display.getCurrent()
						.getActiveShell(), customers);
				dialog.open();

			}
		});
		FormData fd_btnLoadData = new FormData();
		fd_btnLoadData.bottom = new FormAttachment(100, -10);
		fd_btnLoadData.right = new FormAttachment(btnBrowse_1, 0, SWT.RIGHT);
		btnLoadData.setLayoutData(fd_btnLoadData);
		btnLoadData.setText("Load Data");

	}
}