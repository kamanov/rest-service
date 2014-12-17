package ru.spbau.amanov.rest;

import org.hibernate.HibernateException;
import ru.spbau.amanov.core.StatDAO;
import ru.spbau.amanov.core.StatInfo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.core.*;
import java.util.Date;

public class ServiceUtils {
    private static final StatDAO statDAO = new StatDAO();
    public static WebApplicationException resourceNotFound() {
        return new WebApplicationException(404);
    }

    public static WebApplicationException internalServerError()  {
        return new WebApplicationException(500);
    }

    public static WebApplicationException badRequestError()  {
        return new WebApplicationException(400);
    }

    public static String list2HTML(List list){
        StringBuilder html = new StringBuilder(
                "<table border=\"1\">");
        for(Object obj : list){
            List l = (List)obj;
            html.append("<tr>");
            for(Object elem:l){
                html.append("<td>" + elem.toString() + "</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    public static String listHTML(List list, boolean isOneLine){
        StringBuilder html = new StringBuilder(
                "<table border=\"1\">");
        if (isOneLine) html.append("<tr>");
        for(Object obj : list){
            if (!isOneLine) html.append("<tr>");
            html.append("<td>" + obj.toString() + "</td>");
            if (!isOneLine) html.append("</tr>");
        }
        if (isOneLine) html.append("</tr>");
        html.append("</table>");
        return html.toString();
    }

    public static void log(UriInfo uriInfo, Request request) {
        try {
            StatInfo statInfo = new StatInfo();
            statInfo.setOperation(request.getMethod());
            statInfo.setResource(uriInfo.getPath());
            statInfo.setDate(new Date());
            statDAO.create(statInfo);
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
    }

}
