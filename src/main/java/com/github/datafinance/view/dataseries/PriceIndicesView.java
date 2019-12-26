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

@Route(value = "PriceIndices", layout = MBSAppLayout.class)
@PageTitle("Price Indices")
public class PriceIndicesView extends VerticalLayout  {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBSDataSerieService mbsDataSerieService;
	
	@PostConstruct
	public void init() {

		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		
		Button buttonInfo = new Button(VaadinIcon.INFO_CIRCLE.create());
		buttonInfo.addClickListener(event -> {
			final InfoDialog dialog = new InfoDialog("Information", "The original base periods for the national series presented in the Consumer Price Indices table of the Monthly Bulletin of Statistics (MBS) can vary widely. Therefore, for presentation of these data, the MBS has adopted a uniform base period, which is currently 2010=100. When a country does not compile or provide its Consumer Price Indices’ data in the base published in the MBS (2010=100), the data in the original base periods are recalculated by the United Nations Statistics Division by dividing the index for each date shown by the index for the year 2010 (or a more recent year for which data are available in the same series) and multiplying the quotient by 100. This operation does not involve any change in the weighting systems used by the countries.");
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
		titleLayout.add(new H5("Consumer price indices"), buttonInfo, comboBox);
		
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
	}
	
	
	private void updateGrid(Grid<DataSerie> grid, Country country) {
		if (country == null) {
			grid.setItems(new ArrayList<>());
		} else {
			grid.setItems(mbsDataSerieService.getAllByCountryAndSerie(MBSSerieEnum.PRICE_INDICES.CODE, country.getCode()));
		}
	}
}
