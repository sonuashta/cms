package com.intut.luckylottery.cms;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

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
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		configurer.setInitialSize(new Point(dim.width, dim.height));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("Lucky Lottery");
		LotteryLogger.getInstance().setInfo("Logger working");
	}
	@Override
	public void postWindowOpen() {
		 super.postWindowOpen();
		

		
	}
}
