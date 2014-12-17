package ru.spbau.amanov.core;

import org.hibernate.HibernateException;
import ru.spbau.amanov.rest.ServiceUtils;

import java.util.List;


public class CourseDAO extends AbstractDAO {

    public void create(Course course) throws HibernateException {
        super.saveOrUpdate(course);
    }

    public void delete(Course course) throws HibernateException {
        super.delete(course);
    }

    public Course findByName(String name) throws HibernateException {
        return (Course) super.findByField(Course.class, "name", name);
    }

    public void update(Course course) throws HibernateException {
        super.saveOrUpdate(course);
    }

    public List findAll() throws HibernateException{
        return super.findAll(Course.class);
    }

}
