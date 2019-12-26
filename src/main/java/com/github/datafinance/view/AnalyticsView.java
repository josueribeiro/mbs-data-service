package com.github.datafinance.view;

import static com.github.datafinance.model.enums.MBSCountryEnum.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Grid;
import com.github.appreciated.apexcharts.config.TitleSubtitle;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.GridBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.subtitle.Style;
import com.github.appreciated.apexcharts.helper.Series;
import com.github.datafinance.model.entity.Country;
import com.github.datafinance.model.entity.DataSerie;
import com.github.datafinance.model.enums.MBSCountryEnum;
import com.github.datafinance.model.enums.MBSSerieEnum;
import com.github.datafinance.model.service.MBSDataSerieService;
import com.github.datafinance.util.SystemMBSSession;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Analytics", layout = MBSAppLayout.class)
@PageTitle("Analytics")
public class AnalyticsView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MBSDataSerieService financeService;
	
	@Inject
	private SystemMBSSession systemMBSSession;
	
	@PostConstruct
    public void init() {
		
        HorizontalLayout titleLayout = new HorizontalLayout();
		titleLayout.setSpacing(true);
		add(titleLayout);
		
		VerticalLayout chartsLayout = new VerticalLayout();
		chartsLayout.setSizeFull();
		chartsLayout.setJustifyContentMode(JustifyContentMode.START);
		chartsLayout.setAlignItems(Alignment.STRETCH);
		add(chartsLayout);
        
		ComboBox<MBSSerieEnum> comboBox = new ComboBox<>();
		comboBox.setPlaceholder("Select a Data Serie");
		comboBox.setItems(MBSSerieEnum.values());
		comboBox.setSizeFull();
		comboBox.addValueChangeListener(event -> {
			chartsLayout.removeAll();
			generateCharts(event.getValue(), chartsLayout);
		});
		comboBox.setItemLabelGenerator(item -> item.DESCRIPTION);
		titleLayout.add(new H5("Analytics"),  comboBox);
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.STRETCH);
    }

	private void generateCharts(MBSSerieEnum serie, VerticalLayout chartsLayout) {
		
		// BRICS
        generateChart(serie.DESCRIPTION + " BRICS", serie, chartsLayout, BRAZIL, RUSSIAN, INDIA, CHINA, SOUTH_AFRICA);
       
        // ASEAN
        generateChart(serie.DESCRIPTION + " ASEAN", serie, chartsLayout, INDONESIA, MALAYSIA, PHILIPPINES, SINGAPORE, THAILAND);
        
        // Nordics
        generateChart(serie.DESCRIPTION + " NORDICS", serie, chartsLayout, DENMARK, FINLAND, ICELAND, NORWAY, SWEDEN);
        
        // NAFTA
        generateChart(serie.DESCRIPTION + " NAFTA", serie, chartsLayout, CANADA, UNITED_STATES, MEXICO);
        
        // Mercosur
//        generateChart(serie.DESCRIPTION + " MERCOSUR", serie, chartsLayout, ARGENTINA, PARAGUAY, URUGUAY, VENEZUELA);
        
	}

	private void generateChart(String titleChart,
			MBSSerieEnum mbsSerie,
			VerticalLayout chartsLayout,
			MBSCountryEnum... countriesID) {
		
		Integer minYear = financeService.getMinYearBySerie(mbsSerie.CODE);
		Integer maxYear = financeService.getMaxYearBySerie(mbsSerie.CODE);
		
		// Global configurations
        List<String> categories = new ArrayList<String>();
        for (Integer year = minYear; year <= maxYear; year++) {
        	categories.add(year.toString());
        }
		
		String colorGrid = systemMBSSession.isDarkTheme() ? "#636363" : "#f3f3f3";
		
		HashMap<Integer, List<DataSerie>> map = new HashMap<>();
		for (MBSCountryEnum countryID : countriesID) {
			map.put(countryID.CODE, getDataSerie(mbsSerie.CODE, countryID.CODE, minYear, maxYear));
		}
        
		ApexCharts chartSerie = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.line).withZoom(ZoomBuilder.get().withEnabled(false).build()).build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.straight).withColors("#ff0000", "#bada55", "#5ac18e", "#f7347a", "#fa8072").build())
                .withTitle(getTitleChart(titleChart))
                .withGrid(getGrid(colorGrid))
                .withXaxis(XAxisBuilder.get().withCategories(categories).build())
                .withSeries(getAllSeries(map))
                .withColors("#ff0000", "#bada55", "#5ac18e", "#f7347a", "#fa8072")
                .build();
        chartSerie.setWidth("100%");
        chartSerie.setHeight("350px");
        chartsLayout.add(chartSerie);
	}
	
	private Series<?>[] getAllSeries(HashMap<Integer, List<DataSerie>> map) {
		int i = 0;
		Series<?>[] allSeries = new Series[map.size()];
        for (Entry<Integer, List<DataSerie>> entry : map.entrySet()) {
        	if (entry.getValue().size() > 0) {
        		allSeries[i] = new Series<>(entry.getValue().get(0).getCountry().getName(), entry.getValue().stream().map(m -> m.getData()).collect(Collectors.toList()).toArray());
        		i++;
        	}
		}
		return allSeries;
	}

	private List<DataSerie> getDataSerie(String serieId, int countryId, int yearFrom, int yearTo) {
		List<DataSerie> dataSeries = financeService.getAllByCountryAndSerie(serieId, countryId, yearFrom, yearTo);
		Collections.reverse(dataSeries);
		
		Country country = financeService.getCountryByID(countryId);
		
		// For zero data
		if (dataSeries.isEmpty()) { 
			dataSeries = new ArrayList<>();
			for (int i = yearFrom; i <= yearTo; i++) {
				DataSerie ds = new DataSerie();
				ds.setCountry(country);
				ds.setData(null);
				dataSeries.add(ds);
			}
			return dataSeries;
		}
		
		List<DataSerie> dataSeriesReturn = new ArrayList<DataSerie>(); 
		Double currentValue = null;
		for (Integer i = yearFrom; i <= yearTo; i++) {
			final int year = i;
			Optional<DataSerie> findFirst = dataSeries.stream().filter(p -> p.getPeriodYR().equals(year)).findFirst();
			if (findFirst.isPresent()) {
				dataSeriesReturn.add(findFirst.get());
				currentValue = findFirst.get().getData();
			} else {
				DataSerie ds = new DataSerie();
				ds.setCountry(country);
				ds.setPeriodYR(year);
				ds.setData(currentValue);
				dataSeriesReturn.add(ds);
			}
		}
		
		return dataSeriesReturn;
	}

	private Grid getGrid(String... colors) {
		return GridBuilder.get().withRow(RowBuilder.get().withColors(colors).withOpacity(0.5).build()).build();
	}

	private TitleSubtitle getTitleChart(String title) {
		Style styleTitle = new Style();
        styleTitle.setColor("#ff0000");
        styleTitle.setFontSize("20px");
        return TitleSubtitleBuilder.get().withText(title).withStyle(styleTitle).withAlign(Align.center).build();
	}
}
