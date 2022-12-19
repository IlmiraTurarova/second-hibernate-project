package org.example.dao;

import org.example.entity.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class CityDAO extends GenericDAO<City>{
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query<City> query = getCurrentSession().createQuery("select c from City c where c.city=:cityName", City.class);
        query.setParameter("cityName", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
