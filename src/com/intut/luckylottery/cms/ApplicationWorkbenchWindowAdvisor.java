package com.intut.luckylottery.cms;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.intut.luckylottery.cms.dialogs.Aboutdialog;
import com.intut.luckylottery.cms.dialogs.EnterLotteryDialog;
import com.intut.luckylottery.cms.util.LotteryLogger;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1200, 600));
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
		configurer.setTitle("RCP Application");
		LotteryLogger.getInstance().setInfo("Logger working");
	}
	@Override
	public void postWindowOpen() {
		 super.postWindowOpen();
		 EnterLotteryDialog dialog = new EnterLotteryDialog(new Shell());
			dialog.open();

		
	}
}
