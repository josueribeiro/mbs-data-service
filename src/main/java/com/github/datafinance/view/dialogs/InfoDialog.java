package com.github.datafinance.view.dialogs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	public InfoDialog(String caption, String text) {

		final VerticalLayout content = new VerticalLayout();
		content.setPadding(false);
		add(content);

		Html title = new Html(String.format("<h4>%s</h4>", caption));
		title.getElement().getStyle().set("margin-block-start", "0px");
		title.getElement().getStyle().set("margin-block-end", "10px");
		add(title);

		Span labelText = new Span(text);
		labelText.getElement().getStyle().set("text-align", "justify");
		labelText.getElement().getStyle().set("display", "block");
		add(labelText);

		final HorizontalLayout buttons = new HorizontalLayout();
		buttons.setPadding(false);
		buttons.setJustifyContentMode(JustifyContentMode.END);
		add(buttons);

		final Button cancel = new Button("Close", VaadinIcon.CLOSE.create(), e -> close());
		buttons.add(cancel);

		content.setHorizontalComponentAlignment(Alignment.END, buttons);
		setWidth("400px");
	}

}
