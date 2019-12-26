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

@Route(value = "InternationalReserves", layout = MBSAppLayout.class)
@PageTitle("International reserves minus gold by components")
public class InternationalReservesView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBSDataSerieService mbsDataSerieService;
	
	@PostConstruct
	public void init() {

		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		
		Button buttonInfo = new Button(VaadinIcon.INFO_CIRCLE.create());
		buttonInfo.addClickListener(event -> {
			final InfoDialog dialog = new InfoDialog("Information", 
					"The table reports the US dollar value of monetary authorities (central banks, currency boards, exchange stabilization funds and treasuries to the extent that they perform similar functions) holdings of Special drawings rights (SDRs), Reserve Position in the Fund, foreign exchange and the sum of these items which is Total Reserves minus gold." + 
					"Special Drawing Rights (SDR) are unconditional international reserve assets created by the Fund. Reserve Positions in the Fund are unconditional assets that arise from countries' gold subscriptions to the Fund, from the Fund's use of member currcies to finance drawings of others and from Fund Borrowings." + 
					"Foreign exchange is defined as holdings by monetary authorities (central banks, currency boards, exchange stabilization funds, and Treasuries to the extent that they perform similar functions) of claims on foreigners in the form of bank deposits." + 
					"Treasury bills, short and long-term government securities, and other claims usable in the event of a balance-of-payments deficit, including non-marketable claims arising from inter-central bank and intergovernmental arrangements without regard to whether the claim is denominated in the currency of the debtor or the creditor.");
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
		titleLayout.add(new H5("International reserves minus gold"), buttonInfo, comboBox);
		
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
	}
	
	
	private void updateGrid(Grid<DataSerie> grid, Country country) {
		if (country == null) {
			grid.setItems(new ArrayList<>());
		} else {
			grid.setItems(mbsDataSerieService.getAllByCountryAndSerie(MBSSerieEnum.FINANCE_INTERNATIONAL_RESERVES_MINUS_GOLD.CODE, country.getCode()));
		}
	}
}
