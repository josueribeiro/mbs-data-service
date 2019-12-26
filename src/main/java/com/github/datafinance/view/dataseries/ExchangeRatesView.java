package com.github.datafinance.view.dataseries;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.github.datafinance.model.entity.Country;
import com.github.datafinance.model.entity.DataSerie;
import com.github.datafinance.model.enums.MBSSerieEnum;
import com.github.datafinance.model.service.MBSDataSerieService;
import com.github.datafinance.view.MBSAppLayout;
import com.github.datafinance.view.dialogs.InfoDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ExchangeRates", layout = MBSAppLayout.class)
@PageTitle("Exchange Rates")
public class ExchangeRatesView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBSDataSerieService mbsDataSerieService;
	
	@PostConstruct
	public void init() {

		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		
		Button buttonInfo = new Button(VaadinIcon.INFO_CIRCLE.create());
		buttonInfo.addClickListener(event -> {
			final InfoDialog dialog = new InfoDialog("Information", "Foreign exchange rates are shown in units of national currency per US dollar. The exchange rates are classified into three broad categories, reflecting both the role of the authorities in the determination of the exchange and/or the multiplicity of exchange rates in a country. The market rate is used to describe exchange rates determined largely by market forces; the official rate is an exchange rate determined by the authorities, sometimes in a flexible manner. For countries maintaining multiple exchange arrangements, the rates are labeled principal rate, secondary rate, and tertiary rate. Unless otherwise stated, the table refers to end of period and period averages of market exchange rates or official exchange rates. For further information see IFS. ");
			dialog.open();
		});
		buttonInfo.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_SMALL);
		add(titleLayout);
		
		Grid<DataSerie> grid = new Grid<>();
		grid.addColumn(item -> item.getCountry().getName()).setHeader("Country");
		grid.addColumn(item -> item.getMeasure()).setHeader("Measure");
		grid.addColumn(item -> item.getPeriodYR()+"/"+item.getDataCode().getMonthShort()).setHeader("Period").setSortable(true);
		grid.addColumn(item -> item.getData()).setHeader("Data").setSortable(true);
		grid.setSizeFull();
		add(grid);
		
		ComboBox<Country> comboBox = new ComboBox<>();
		comboBox.setPlaceholder("Select a country");
		comboBox.setItems(mbsDataSerieService.getAllCountries());
		comboBox.addValueChangeListener(event -> {
			updateGrid(grid, event.getValue());
		});
		comboBox.setItemLabelGenerator(item -> item.getName());
		titleLayout.add(new H5("Exchange Rates"), buttonInfo, comboBox);
		
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
	}
	
	
	private void updateGrid(Grid<DataSerie> grid, Country country) {
		if (country == null) {
			grid.setItems(new ArrayList<>());
		} else {
			grid.setItems(mbsDataSerieService.getAllByCountryAndSerie(MBSSerieEnum.FINANCE_EXCHANGE_RATES.CODE, country.getCode()));
		}
	}
}
