package ru.spbau.amanov.rest;

import org.hibernate.HibernateException;
import ru.spbau.amanov.core.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.util.*;

@Path("course")
public class CourseRequest {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    private void log() { ServiceUtils.log(uriInfo, request); }

    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final ScoreDAO scoreDAO = new ScoreDAO();


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        log();
        try {
            List<Course> courses = courseDAO.findAll();
            return "<p>Courses</p> " + ServiceUtils.listHTML(courses, false);
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Course> get() {
        log();
        try {
            return courseDAO.findAll();
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @GET
    @Path("/{course}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Course getByName(@PathParam("course") String courseName) {
        log();
        try {
            Course course = courseDAO.findByName(courseName);
            if (course == null)
                throw ServiceUtils.resourceNotFound();
            return course;
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }

    }

    @GET
    @Path("/{course}")
    @Produces(MediaType.TEXT_HTML)
    public String getByNameHtml(@PathParam("course") String name) {
        log();
        List<String> info = new ArrayList<>();
        info.add("Course");
        info.add(getByName(name).getName());
        return ServiceUtils.listHTML(info, true);
    }

    @GET
    @Path("/{course}/{student}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Score getScore(@PathParam("course") String courseName, @PathParam("student") String studentName) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            Course course = courseDAO.findByName(courseName);
            Score score = scoreDAO.getScore(student, course);
            if (score == null)
                throw  ServiceUtils.resourceNotFound();
            return score;
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @GET
    @Path("/{course}/{student}")
    @Produces(MediaType.TEXT_HTML)
    public String getScoreHtml(@PathParam("course") String courseName, @PathParam("student") String studentName) {
        log();
        Score score = getScore(courseName, studentName);
        List<String> info = new ArrayList<String>();
        info.add(score.getCourse().getName());
        info.add(score.getStudent().getLastName());
        info.add(Integer.toString(score.getScore()));
        return ServiceUtils.listHTML(info, true);
    }

    @PUT
    @Path("/{course}")
    public Response putCourse(@PathParam("course") String courseName) {
        log();
        try {
            Course course = courseDAO.findByName(courseName);
            if (course == null) {
                Course c = new Course();
                c.setName(courseName);
                courseDAO.create(c);
                return Response.ok().build();
            } else {
                throw ServiceUtils.badRequestError();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @PUT
    @Path("/{course}/{student}/{score}")
    public Response putScore(@PathParam("course") String courseName, @PathParam("student") String studentName, @PathParam("score") String score) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            Course course = courseDAO.findByName(courseName);
            if (student == null || course == null)
                throw ServiceUtils.resourceNotFound();
            Score storedScore = scoreDAO.getScore(student, course);
            if (storedScore == null) {
                storedScore = new Score();
                storedScore.setCourse(course);
                storedScore.setStudent(student);
                int scoreVal = Integer.parseInt(score);
                validateScore(scoreVal);
                storedScore.setScore(scoreVal);
                scoreDAO.create(storedScore);
                return Response.ok().build();
            } else {
                throw  ServiceUtils.badRequestError();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @POST
    @Path("/{course}/{student}/{score}")
    public Response updateScore(@PathParam("course") String courseName, @PathParam("student") String studentName, @PathParam("score") String score) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            Course course = courseDAO.findByName(courseName);
            Score storedScore = scoreDAO.getScore(student, course);
            if (storedScore != null) {
                int scoreVal = Integer.parseInt(score);
                validateScore(scoreVal);
                storedScore.setScore(scoreVal);
                scoreDAO.update(storedScore);
                return Response.ok().build();
            }
            throw ServiceUtils.resourceNotFound();
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @DELETE
    @Path("/{course}")
    public Response deleteCourse(@PathParam("course") String courseName) {
        log();
        try {
            Course course = courseDAO.findByName(courseName);
            if (course == null) {
                throw ServiceUtils.resourceNotFound();
            } else {
                courseDAO.delete(course);
                return Response.ok().build();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }

    @DELETE
    @Path("/{course}/{student}")
    public Response deleteScore(@PathParam("course") String courseName, @PathParam("student") String studentName) {
        log();
        try {
            Student student = studentDAO.findByName(studentName);
            Course course = courseDAO.findByName(courseName);
            Score storedScore = scoreDAO.getScore(student, course);
            if (storedScore == null) {
                throw ServiceUtils.resourceNotFound();
            } else {
                scoreDAO.delete(storedScore);
                return Response.ok().build();
            }
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }


    void validateScore(int scoreVal) throws WebServiceException {
        if (scoreVal < 2 || scoreVal > 5)
            throw ServiceUtils.badRequestError();
    }
}


