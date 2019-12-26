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

@Route(value = "TotalImport", layout = MBSAppLayout.class)
@PageTitle("Total imports C.I.F in US dollars")
public class TotalImportView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Inject
	private MBSDataSerieService mbsDataSerieService;
	
	@PostConstruct
	public void init() {

		HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		
		Button buttonInfo = new Button(VaadinIcon.INFO_CIRCLE.create());
		buttonInfo.addClickListener(event -> {
			final InfoDialog dialog = new InfoDialog("Information", "Imports: under the general trade system, both goods entering directly for domestic consumption and goods entered into customs storage are recorded, at the time of their first arrival, as imports; under the special trade system, goods are recorded as imports when declared for domestic consumption whether at time of entry or on withdrawal from customs storage.");
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
		titleLayout.add(new H5("Total imports C.I.F in US dollars"), buttonInfo, comboBox);
		
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
	}
	
	
	private void updateGrid(Grid<DataSerie> grid, Country country) {
		if (country == null) {
			grid.setItems(new ArrayList<>());
		} else {
			grid.setItems(mbsDataSerieService.getAllByCountryAndSerie(MBSSerieEnum.IMT_TOTAL_IMPORT.CODE, country.getCode()));
		}
	}
}
