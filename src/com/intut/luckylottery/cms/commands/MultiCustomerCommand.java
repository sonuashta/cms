package com.intut.luckylottery.cms.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.Shell;

import com.intut.luckylottery.cms.dialogs.DataLoaderDialog;
import com.intut.luckylottery.cms.dialogs.ProcessesDialog;
import com.intut.luckylottery.cms.dialogs.ProgressDialog;

public class MultiCustomerCommand implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// DataLoaderDialog dialog = new DataLoaderDialog(new Shell());
		ProcessesDialog dialog = new ProcessesDialog(new Shell());
		dialog.open();
		// MultiCustomerDialog dialog = new MultiCustomerDialog(new Shell());
		// dialog.open();
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
