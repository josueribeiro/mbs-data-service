package com.github.datafinance.util;

import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class SystemMBSSession {

	private boolean isDarkTheme = true;

	public boolean isDarkTheme() {
		return isDarkTheme;
	}

	public void setDarkTheme(boolean isDarkTheme) {
		this.isDarkTheme = isDarkTheme;
	}

}
