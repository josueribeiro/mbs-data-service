package com.github.datafinance.view;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.shrinkwrap.VaadinCoreShrinkWrap;

@Route(value = "AboutC", layout = MBSAppLayout.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public AboutView() {
        add(new H3("About"));
        add(new Span(" This application is using: "));
        add(new Span("- Vaadin version is "+ VaadinCoreShrinkWrap.class.getAnnotation(NpmPackage.class).version()+ "."));
        add(new Span("- CDI with Weld version is 3.1.3."));
        add(new Span("- JPA with Hibernate version is 5.3.7.Final."));
        add(new Span("- Layout is app-layout-addon and version is 4.0.0.rc4"));
        add(new Span("- Charts is apexcharts and version is 4.0.0.rc4"));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);

    }
}
