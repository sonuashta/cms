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
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

import com.intut.luckylottery.cms.modelProviders.LotteryModelProvider;
import com.intut.luckylottery.cms.util.Util;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class SelectBumper extends Dialog {
	private DataBindingContext m_bindingContext;

	protected Object result;
	protected Shell shell;
	private Text bumperText;
	private LotteryModelProvider moodelProvider;
	private Link link;
	private Button cancelButton;
	private Button addButton, btnDone;
	private Link link_1;
	private Combo combo_1;
	private Button btnCancel;
	private Button btnAdd;
	private Combo combo;
	private Text text;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 * @throws Exception
	 */
	public SelectBumper(Shell parent) throws Exception {
		super(parent);
		setText("SWT Dialog");
		moodelProvider = new LotteryModelProvider();
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
		shell.setImage(ResourceManager.getPluginImage(
				"com.intut.luckylottery.cms",
				"icons/appIcons/toolbar/1363818154_banknote.png"));
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new FormLayout());

		btnDone = new Button(shell, SWT.NONE);
		btnDone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (moodelProvider.getList().size() > 0
							&& !Util.isStringNullOrEmpty(moodelProvider
									.getSelectedBumper()))

						moodelProvider.saveSettings();
					shell.close();
				} catch (Exception e1) {
					MessageDialog.openError(shell, "Error", e1.getMessage());
				}
			}
		});
		FormData fd_btnDone = new FormData();
		fd_btnDone.right = new FormAttachment(100, -10);
		fd_btnDone.bottom = new FormAttachment(100, -10);
		btnDone.setLayoutData(fd_btnDone);
		btnDone.setText("Done");

		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(btnDone, -8, SWT.TOP);
		fd_label.left = new FormAttachment(0, 10);
		fd_label.bottom = new FormAttachment(btnDone, -6);
		fd_label.right = new FormAttachment(100, -10);
		label.setLayoutData(fd_label);

		Label lblSelectDefaultBumper = new Label(shell, SWT.NONE);
		FormData fd_lblSelectDefaultBumper = new FormData();
		fd_lblSelectDefaultBumper.top = new FormAttachment(0, 10);
		fd_lblSelectDefaultBumper.left = new FormAttachment(label, 0, SWT.LEFT);
		lblSelectDefaultBumper.setLayoutData(fd_lblSelectDefaultBumper);
		lblSelectDefaultBumper
				.setText("Select Default Bumper which will be selected in each screen.");

		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(lblSelectDefaultBumper, 8,
				SWT.BOTTOM);
		fd_label_1.top = new FormAttachment(lblSelectDefaultBumper, 6);
		fd_label_1.right = new FormAttachment(0, 434);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);

		Label lblSelectBumper = new Label(shell, SWT.NONE);
		FormData fd_lblSelectBumper = new FormData();
		fd_lblSelectBumper.top = new FormAttachment(label_1, 6);
		fd_lblSelectBumper.left = new FormAttachment(label, 0, SWT.LEFT);
		lblSelectBumper.setLayoutData(fd_lblSelectBumper);
		lblSelectBumper.setText("Select Bumper ");

		combo = new Combo(shell, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.top = new FormAttachment(lblSelectBumper, 6);
		fd_combo.left = new FormAttachment(label, 0, SWT.LEFT);
		combo.setLayoutData(fd_combo);

		link = new Link(shell, SWT.NONE);
		link.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.ITALIC));
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButton.setVisible(true);
				cancelButton.setVisible(true);
				moodelProvider.setCancelButtonEnabled(true);
				bumperText.setVisible(true);
				link.setEnabled(false);
			}
		});
		FormData fd_link = new FormData();
		fd_link.left = new FormAttachment(label, 0, SWT.LEFT);
		link.setLayoutData(fd_link);
		link.setText("<a>Add Bumper</a>");

		bumperText = new Text(shell, SWT.BORDER);
		fd_link.top = new FormAttachment(bumperText, 0, SWT.TOP);
		bumperText.setVisible(false);
		FormData fd_bumperText = new FormData();
		fd_bumperText.top = new FormAttachment(combo, 6);
		fd_bumperText.left = new FormAttachment(link, 6);
		bumperText.setLayoutData(fd_bumperText);

		cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setVisible(false);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButton.setVisible(false);
				cancelButton.setVisible(false);
				moodelProvider.setText("");
				link.setEnabled(true);
				bumperText.setVisible(false);
			}
		});
		fd_bumperText.right = new FormAttachment(cancelButton, -6);
		FormData fd_cancelButton = new FormData();
		fd_cancelButton.top = new FormAttachment(label_1, 56);
		cancelButton.setLayoutData(fd_cancelButton);
		cancelButton.setText("Cancel");

		addButton = new Button(shell, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					moodelProvider.addBumper();
					addButton.setVisible(false);
					cancelButton.setVisible(false);
					moodelProvider.setText("");
					bumperText.setVisible(false);
					link.setEnabled(true);
				} catch (Exception e1) {
					MessageDialog.openError(shell, "", "");
				}
			}
		});
		addButton.setVisible(false);
		fd_cancelButton.right = new FormAttachment(addButton, -6);
		FormData fd_addButton = new FormData();
		fd_addButton.top = new FormAttachment(label_1, 56);
		fd_addButton.right = new FormAttachment(btnDone, 0, SWT.RIGHT);
		addButton.setLayoutData(fd_addButton);
		addButton.setText("Add");

		link_1 = new Link(shell, SWT.NONE);
		link_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moodelProvider.setRemoveLinkEnable(false);
			}
		});
		link_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.ITALIC));
		FormData fd_link_1 = new FormData();
		fd_link_1.left = new FormAttachment(label, 0, SWT.LEFT);
		link_1.setLayoutData(fd_link_1);
		link_1.setText("<a>Remove Bumper</a>");

		combo_1 = new Combo(shell, SWT.READ_ONLY);
		fd_link_1.top = new FormAttachment(combo_1, 0, SWT.TOP);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.top = new FormAttachment(bumperText, 10);
		fd_combo_1.left = new FormAttachment(link_1, 6);
		combo_1.setLayoutData(fd_combo_1);

		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moodelProvider.setRemoveLinkEnable(true);
			}
		});
		fd_combo_1.right = new FormAttachment(100, -126);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.left = new FormAttachment(combo_1, 6);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");

		btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					moodelProvider.deleteBumper();
					moodelProvider.setRemoveLinkEnable(true);
				} catch (Exception e1) {
					MessageDialog.openError(shell, "Error", e1.getMessage());
				}

			}
		});
		fd_btnCancel.top = new FormAttachment(btnAdd, 0, SWT.TOP);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.top = new FormAttachment(cancelButton, 6);
		fd_btnAdd.right = new FormAttachment(btnDone, 0, SWT.RIGHT);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.setText("Remove");
		
		Label lblMessage = new Label(shell, SWT.NONE);
		FormData fd_lblMessage = new FormData();
		fd_lblMessage.top = new FormAttachment(link_1, 16);
		fd_lblMessage.left = new FormAttachment(0, 10);
		lblMessage.setLayoutData(fd_lblMessage);
		lblMessage.setText("Message");
		
		text = new Text(shell, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.bottom = new FormAttachment(label, -6);
		fd_text.right = new FormAttachment(btnDone, 0, SWT.RIGHT);
		fd_text.top = new FormAttachment(lblMessage, 6);
		fd_text.left = new FormAttachment(label, 0, SWT.LEFT);
		text.setLayoutData(fd_text);
		m_bindingContext = initDataBindings();
		moodelProvider.setSelectedBumper(moodelProvider.getSelectedBumper());

	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue linkObserveEnabledObserveWidget = SWTObservables.observeEnabled(link);
		IObservableValue moodelProviderAddLinkEnabledObserveValue = BeansObservables.observeValue(moodelProvider, "addLinkEnabled");
		bindingContext.bindValue(linkObserveEnabledObserveWidget, moodelProviderAddLinkEnabledObserveValue, null, null);
		//
		IObservableValue textObserveTextObserveWidget = SWTObservables.observeText(bumperText, SWT.Modify);
		IObservableValue moodelProviderTextObserveValue = BeansObservables.observeValue(moodelProvider, "text");
		bindingContext.bindValue(textObserveTextObserveWidget, moodelProviderTextObserveValue, null, null);
		//
		IObservableValue textObserveVisibleObserveWidget = SWTObservables.observeVisible(bumperText);
		IObservableValue moodelProviderTextEnabledObserveValue = BeansObservables.observeValue(moodelProvider, "textEnabled");
		bindingContext.bindValue(textObserveVisibleObserveWidget, moodelProviderTextEnabledObserveValue, null, null);
		//
		IObservableValue btnNewButtonObserveEnabledObserveWidget = SWTObservables.observeEnabled(cancelButton);
		IObservableValue moodelProviderCancelButtonEnabledObserveValue = BeansObservables.observeValue(moodelProvider, "cancelButtonEnabled");
		bindingContext.bindValue(btnNewButtonObserveEnabledObserveWidget, moodelProviderCancelButtonEnabledObserveValue, null, null);
		//
		IObservableValue btnNewButton_1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(addButton);
		IObservableValue moodelProviderAddButtonEnabledObserveValue = BeansObservables.observeValue(moodelProvider, "addButtonEnabled");
		bindingContext.bindValue(btnNewButton_1ObserveEnabledObserveWidget, moodelProviderAddButtonEnabledObserveValue, null, null);
		//
		IObservableValue link_1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(link_1);
		IObservableValue moodelProviderRemoveLinkEnableObserveValue = BeansObservables.observeValue(moodelProvider, "removeLinkEnable");
		bindingContext.bindValue(link_1ObserveEnabledObserveWidget, moodelProviderRemoveLinkEnableObserveValue, null, null);
		//
		IObservableList combo_1ObserveItemsObserveListWidget = SWTObservables.observeItems(combo_1);
		bindingContext.bindList(combo_1ObserveItemsObserveListWidget, moodelProvider.getRemoveBumperList(), null, null);
		//
		IObservableValue combo_1ObserveVisibleObserveWidget = SWTObservables.observeVisible(combo_1);
		IObservableValue moodelProviderRemovebumperListVisibleObserveValue = BeansObservables.observeValue(moodelProvider, "removebumperListVisible");
		bindingContext.bindValue(combo_1ObserveVisibleObserveWidget, moodelProviderRemovebumperListVisibleObserveValue, null, null);
		//
		IObservableValue btnCancelObserveVisibleObserveWidget = SWTObservables.observeVisible(btnCancel);
		IObservableValue moodelProviderCancelRemoveVisibleObserveValue = BeansObservables.observeValue(moodelProvider, "cancelRemoveVisible");
		bindingContext.bindValue(btnCancelObserveVisibleObserveWidget, moodelProviderCancelRemoveVisibleObserveValue, null, null);
		//
		IObservableValue btnAddObserveVisibleObserveWidget = SWTObservables.observeVisible(btnAdd);
		IObservableValue moodelProviderRemoveButtonVisibleObserveValue = BeansObservables.observeValue(moodelProvider, "removeButtonVisible");
		bindingContext.bindValue(btnAddObserveVisibleObserveWidget, moodelProviderRemoveButtonVisibleObserveValue, null, null);
		//
		IObservableList comboObserveItemsObserveListWidget = SWTObservables.observeItems(combo);
		bindingContext.bindList(comboObserveItemsObserveListWidget, moodelProvider.getList(), null, null);
		//
		IObservableValue comboObserveSelectionObserveWidget = SWTObservables.observeSelection(combo);
		IObservableValue moodelProviderSelectedBumperObserveValue = BeansObservables.observeValue(moodelProvider, "selectedBumper");
		bindingContext.bindValue(comboObserveSelectionObserveWidget, moodelProviderSelectedBumperObserveValue, null, null);
		//
		IObservableValue combo_1ObserveSelectionObserveWidget = SWTObservables.observeSelection(combo_1);
		IObservableValue moodelProviderSelectedRemoveBumperObserveValue = BeansObservables.observeValue(moodelProvider, "selectedRemoveBumper");
		bindingContext.bindValue(combo_1ObserveSelectionObserveWidget, moodelProviderSelectedRemoveBumperObserveValue, null, null);
		//
		IObservableValue textObserveTextObserveWidget_1 = SWTObservables.observeText(text, SWT.Modify);
		IObservableValue moodelProviderMessageObserveValue = BeansObservables.observeValue(moodelProvider, "message");
		bindingContext.bindValue(textObserveTextObserveWidget_1, moodelProviderMessageObserveValue, null, null);
		//
		return bindingContext;
	}
}
