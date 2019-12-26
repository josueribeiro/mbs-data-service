package com.github.datafinance.model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.github.datafinance.model.entity.DataSerie;

public class DataSerieDAO extends AbstractDAO<DataSerie> {

	private static final long serialVersionUID = 1L;

	public List<DataSerie> getAllBYCountryAndSerie(String serieCode, Integer countryCode, Integer yearFrom,
			Integer yearTo) {
		
		String dql = "select o from " + getParameterizedClass().getSimpleName()
				+ " o where o.serie.code = :pCodeSerie and o.country.code = :pCodeCountry "
				+ "and o.periodYR >= :pYearFrom and o.periodYR <= :pYearTO "
				+ "order by o.periodYR desc, o.dataCode.order desc";
		
		TypedQuery<DataSerie> query = getEntityManager().createQuery(dql, DataSerie.class);
		query.setParameter("pCodeSerie", serieCode);
		query.setParameter("pCodeCountry", countryCode);
		query.setParameter("pYearFrom", yearFrom);
		query.setParameter("pYearTO", yearTo);
		return query.getResultList();

	}
	
	public List<DataSerie> getAllBYCountryAndSerie(String serieCode, Integer countryCode) {
		
		String dql = "select o from " + getParameterizedClass().getSimpleName()
				+ " o where o.serie.code = :pCodeSerie and o.country.code = :pCodeCountry "
				+ "order by o.periodYR desc, o.dataCode.order desc";
		
		TypedQuery<DataSerie> query = getEntityManager().createQuery(dql, DataSerie.class);
		query.setParameter("pCodeSerie", serieCode);
		query.setParameter("pCodeCountry", countryCode);
		return query.getResultList();

	}

	public Integer getMinYearBySerie(String codeSerie) {
	    Query q = getEntityManager().createQuery("select min(periodYR) from " + getParameterizedClass().getSimpleName() + " n where n.serie.code like :pCode");
	    q.setParameter("pCode", codeSerie);
	    return (Integer) q.getSingleResult();
	}

	public Integer getMaxYearBySerie(String codeSerie) {
		Query q = getEntityManager().createQuery("select max(periodYR) from " + getParameterizedClass().getSimpleName() + " n where n.serie.code like :pCode");
	    q.setParameter("pCode", codeSerie);
	    return (Integer) q.getSingleResult();
	}

}
