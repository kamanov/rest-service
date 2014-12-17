package ru.spbau.amanov.rest;

import org.hibernate.HibernateException;
import ru.spbau.amanov.core.StatDAO;
import ru.spbau.amanov.core.StatInfo;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("stat")
public class StatRequest {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    private void log() { ServiceUtils.log(uriInfo, request); }

    private final StatDAO statDAO = new StatDAO();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        log();
        StringBuilder html = new StringBuilder("<p>Stat</p> ");
        List<StatInfo> stat = get();
        List statHtml = new ArrayList();
        for (StatInfo statInfo : stat) {
            List l = new ArrayList();
            l.add(statInfo.getOperation());
            l.add(statInfo.getResource());
            l.add(statInfo.getDate());
            statHtml.add(l);
        }
        html.append(ServiceUtils.list2HTML(statHtml));
        return html.toString();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<StatInfo> get() {
        log();
        try {
            return statDAO.findAll();
        } catch (HibernateException e) {
            throw ServiceUtils.internalServerError();
        }
    }
}
