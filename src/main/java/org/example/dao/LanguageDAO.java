package org.example.dao;

import org.example.entity.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<Language>{
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
