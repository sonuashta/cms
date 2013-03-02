package com.intut.luckylottery.cms.dialogs;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Aboutdialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public Aboutdialog(Shell parent) {
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
		shell = new Shell(getParent(), SWT.NONE);
		
		shell.setSize(450, 300);
		shell.setText(getText());
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				shell.close();
			}
		});
		composite.setFocus();
		composite.setBounds(0, 0, 448, 298);

	}
}
