package com.github.datafinance.view;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.LeftSubmenu;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.datafinance.util.SystemMBSSession;
import com.github.datafinance.view.dataseries.ExchangeRatesView;
import com.github.datafinance.view.dataseries.InternationalReservesView;
import com.github.datafinance.view.dataseries.PopulationView;
import com.github.datafinance.view.dataseries.PriceIndicesView;
import com.github.datafinance.view.dataseries.TotalExportView;
import com.github.datafinance.view.dataseries.TotalImportView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.VaadinService;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MBSAppLayout extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {

	private static final long serialVersionUID = 1L;
	
	private Button buttonThemeDarkLight;
	
	private Component titleComponent;
	private String contextPath;
	
	@Inject
	private SystemMBSSession systemMBSSession;
	
	public MBSAppLayout() {
	}

	@PostConstruct
	public void init() {
		
		if (contextPath == null) {
			contextPath = VaadinService.getCurrentRequest().getContextPath();
		}
		
		// Initial theme is Light
		buttonThemeDarkLight = new Button();
		buttonThemeDarkLight.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		buttonThemeDarkLight.setIcon(VaadinIcon.MOON_O.create());
		buttonThemeDarkLight.addClickListener(event -> {
			changeThemeThemeDarkLight();
			buttonThemeDarkLight.getUI().ifPresent(ui -> ui.navigate(HomeView.class));
		});
		systemMBSSession.setDarkTheme(false);
		
		// Finance Module 
		LeftSubmenu financeMenu = LeftSubMenuBuilder.get("Finance", VaadinIcon.COIN_PILES.create())
				.add(new LeftNavigationItem("Exchange rates", VaadinIcon.MONEY_EXCHANGE.create(), ExchangeRatesView.class))
				.add(new LeftNavigationItem("International reserves", VaadinIcon.PIGGY_BANK_COIN.create(), InternationalReservesView.class))
				.build();
		
		// Merchandise Trade
		LeftSubmenu merchandiseTrade = LeftSubMenuBuilder.get("Merchandise Trade", VaadinIcon.GLOBE.create())
				.add(new LeftNavigationItem("Total imports", VaadinIcon.INBOX.create(), TotalImportView.class))
				.add(new LeftNavigationItem("Total exports", VaadinIcon.OUTBOX.create(), TotalExportView.class))
				.build();
				
		init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
				.withTitle(getTitleComponent())
				.withAppBar(AppBarBuilder.get().add(buttonThemeDarkLight).build())
				.withAppMenu(LeftAppMenuBuilder.get()
						       .add(new LeftNavigationItem("Home", VaadinIcon.HOME.create(), HomeView.class),
						    		new LeftNavigationItem("Analytics", VaadinIcon.LINE_BAR_CHART.create(), AnalyticsView.class),
						    		new LeftNavigationItem("Population", VaadinIcon.USERS.create(), PopulationView.class),
						    		new LeftNavigationItem("Price Indices", VaadinIcon.CHART_LINE.create(), PriceIndicesView.class),
						    	    financeMenu, merchandiseTrade)
						       .withStickyFooter()
						       .addToSection(FOOTER, new LeftClickableItem("About", 
						    		                                       VaadinIcon.INFO_CIRCLE.create(), 
						    		                                       clickEvent -> {
						    		                                    	   if (getUI().isPresent()) {
						    		                                    		   getUI().ifPresent(ui -> ui.navigate(AboutView.class));
						    		                                    	   }
						    		                                       }))
						       .build())
				.build());
	}

	private void changeThemeThemeDarkLight() {
		if (systemMBSSession.isDarkTheme()) {
			getElement().executeJs("document.querySelector('html').setAttribute('theme', 'ligth');");
			buttonThemeDarkLight.setIcon(VaadinIcon.MOON_O.create());
			systemMBSSession.setDarkTheme(false);
		}
		else {
			buttonThemeDarkLight.setIcon(VaadinIcon.SUN_O.create());
			getElement().executeJs("document.querySelector('html').setAttribute('theme', 'dark');");
			systemMBSSession.setDarkTheme(true);
		}
		((Image)titleComponent).setSrc(systemMBSSession.isDarkTheme() ? getSrcWithContext("/images/un-logo-wite.png") : getSrcWithContext("/images/un-logo-black.png"));
	}

	private Component getTitleComponent() {
		titleComponent = new Image();
		((Image)titleComponent).setSrc(systemMBSSession.isDarkTheme() ? getSrcWithContext("/images/un-logo-wite.png") : getSrcWithContext("/images/un-logo-black.png"));
		((Image)titleComponent).setWidth("60px");
		((Image)titleComponent).setHeight("50px");
		((Image)titleComponent).getElement().getStyle().set("vertical-align", "middle");
		
		Div divTitleComponent = new Div();
		divTitleComponent.add(titleComponent);
		divTitleComponent.add(new Span(" .: MBS - UN :."));
		divTitleComponent.getElement().getStyle().set("font-size", "25px");
		return divTitleComponent;
	}
	
	private String getSrcWithContext(String src) {
		return contextPath + src;
	}
	
}
