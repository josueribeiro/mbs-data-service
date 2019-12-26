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

@Route(value = "TotalExport", layout = MBSAppLayout.class)
@PageTitle("Total exports F.O.B in US dollars")
public class TotalExportView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBSDataSerieService mbsDataSerieService;
	
	@PostConstruct
	public void init() {

		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		
		Button buttonInfo = new Button(VaadinIcon.INFO_CIRCLE.create());
		buttonInfo.addClickListener(event -> {
			final InfoDialog dialog = new InfoDialog("Information", "Exports: outward moving goods consist of: (a) national goods i.e. those wholly or partly produced in the country; (b) foreign goods, neither transformed nor declared for domestic consumption in the country, which move outward from customs storage; (c) nationalized goods, i.e. foreign goods, declared for domestic consumption, which move outward without having been transformed. General exports comprise all three categories and, in the general trade system, the sum of (b) and (c) may be tabulated as re exports. Special exports comprise categories (a) and (c). Direct transit trade, consisting of goods entering or leaving for transport purposes only, is excluded from both import and export statistics.");
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
		titleLayout.add(new H5("Total exports F.O.B in US dollars"), buttonInfo, comboBox);
		
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
	}
	
	
	private void updateGrid(Grid<DataSerie> grid, Country country) {
		if (country == null) {
			grid.setItems(new ArrayList<>());
		} else {
			grid.setItems(mbsDataSerieService.getAllByCountryAndSerie(MBSSerieEnum.IMT_TOTAL_EXPORT.CODE, country.getCode()));
		}
	}
}
