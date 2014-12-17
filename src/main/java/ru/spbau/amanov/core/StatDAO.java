package ru.spbau.amanov.core;

import org.hibernate.HibernateException;

import java.util.List;

public class StatDAO extends AbstractDAO {

    public void create(StatInfo statInfo) throws HibernateException {
        super.saveOrUpdate(statInfo);
    }

    public List findAll() throws HibernateException{
        return super.findAll(StatInfo.class);
    }

}
