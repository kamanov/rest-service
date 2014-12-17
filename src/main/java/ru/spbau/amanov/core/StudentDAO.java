package ru.spbau.amanov.core;

import org.hibernate.HibernateException;

import java.util.List;

public class StudentDAO extends AbstractDAO {

    public void create(Student student) throws HibernateException {
        super.saveOrUpdate(student);
    }

    public void delete(Student student) throws HibernateException {
        super.delete(student);
    }

    public Student findByName(String name) throws HibernateException {
        return (Student) super.findByField(Student.class, "last_name" ,name);
    }

    public void update(Student student) throws HibernateException {
        super.saveOrUpdate(student);
    }

    public List findAll() throws HibernateException{
        return super.findAll(Student.class);
    }

}
