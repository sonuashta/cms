package com.intut.luckylottery.cms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

public class View extends ViewPart {
	public View() {
		setTitleImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/branding/lottery_32.png"));
	}
	public static final String ID = "com.intut.luckylottery.cms.view";


	public void createPartControl(Composite parent) {
		parent.setBackgroundImage(ResourceManager.getPluginImage("com.intut.luckylottery.cms", "icons/appIcons/background.png"));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}
}