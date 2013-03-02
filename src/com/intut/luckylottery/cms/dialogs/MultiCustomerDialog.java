package com.intut.luckylottery.cms.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.FormAttachment;

public class MultiCustomerDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public MultiCustomerDialog(Shell parent) {
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
		shell = new Shell(getParent());
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new FormLayout());

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(82);
		tableColumn.setText("S.No");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Description");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Quantity");

	}

	private void reallocatesItem() {
		

		TableItem item = new TableItem(table, getStyle());
		item.setText(new String[] { "", });
	}
}
