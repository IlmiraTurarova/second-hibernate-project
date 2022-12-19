package org.example.dao;

import org.example.entity.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<Country>{
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
