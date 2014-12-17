package ru.spbau.amanov.rest;


import org.hibernate.HibernateException;
import org.omg.PortableInterceptor.Interceptor;
import ru.spbau.amanov.core.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("student")
public class StudentRequest {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    private void log() { ServiceUtils.log(uriInfo, request); }

    private final StudentDAO studentDAO = new StudentDAO();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        log();
        List<Student> students = studentDAO.findAll();
        return "<p>Students</p> " + ServiceUtils.listHTML(students, false);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Student> get() {
        log();
        try {
            return studentDAO.findAll();
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @GET
    @Path("/{student}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getByName(@PathParam("student") String studentName) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            if (student == null)
                throw ServiceUtils.resourceNotFound();
            return student;
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }

    }

    @GET
    @Path("/{student}")
    @Produces(MediaType.TEXT_HTML)
    public String getByNameHtml(@PathParam("student") String name) {
        log();
        Student student = getByName(name);
        StringBuilder html = new StringBuilder(String.format("<p>%s</p> ", student.getLastName()));
        List<Score> scores = student.getScores();
        List scoresHtml = new ArrayList();
        for (Score score : scores) {
            List l = new ArrayList();
            l.add(score.getCourse().getName());
            l.add(Integer.toString(score.getScore()));
            scoresHtml.add(l);
        }
        html.append(ServiceUtils.list2HTML(scoresHtml));
        return html.toString();
    }


    @PUT
    @Path("/{student}")
    public Response putStudent(@PathParam("student") String studentName) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            if (student == null) {
                Student s = new Student();
                s.setLastName(studentName);
                studentDAO.create(s);
                return Response.ok().build();
            } else {
                throw ServiceUtils.badRequestError();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @DELETE
    @Path("/{student}")
    public Response deleteStudent(@PathParam("student") String studentName) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            if (student == null) {
                throw ServiceUtils.resourceNotFound();
            } else {
                studentDAO.delete(student);
                return Response.ok().build();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

}
