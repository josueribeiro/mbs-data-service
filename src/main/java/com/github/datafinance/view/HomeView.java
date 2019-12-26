package com.github.datafinance.view;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route(value = "", layout = MBSAppLayout.class)
@PWA(name = "MBS Data Service", 
     shortName = "MBS Data Service", 
     description = "The purpose of this app is to show datas from MBS (Monthly Bulletin of Statistics - United Nations)")
public class HomeView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	
    public HomeView() {
        add(new H3("Purpose"));
        add(new Span("The purpose of this app is to show datas from MBS (Monthly Bulletin of Statistics - United Nations)"));
        add(new Span("The Monthly Bulletin of Statistics Online presents current economic and social statistics for more than 200 countries and territories of the world. It contains 55 tables, comprising over 100 indicators, of monthly, quarterly and annual data on a variety of subjects illustrating important economic trends and developments, including population, industrial production indices, price indices, employment and earnings, energy, manufacturing, transport, construction, international merchandise trade, finance and national accounts."));
        add(new Span("For more than 80 years, the Monthly Bulletin of Statistics has been published by the United Nations Statistics Division and previously by the League of Nations."));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
    }
}
