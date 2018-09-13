package com.github.sbahmani.rest;

import com.github.sbahmani.Student;
import com.github.sbahmani.StudentDAO;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author sjb
 */
@Path("/student")
public class StudentService {

    @GET
    @Path("/hi")
    public Response hi() {
        return Response.status(Response.Status.OK).entity(
                "hi"
        ).build();
    }

    @GET
    @Path("/id/{id}")
    public Response id(
            @PathParam("id") Long id) {
        try {
            return Response.status(Response.Status.OK).entity(
                    StudentDAO.LoadByID(id).toString()
            ).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    "not found"
            ).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response email(
            @PathParam("email") String email) {
        try {
            return Response.status(Response.Status.OK).entity(
                    StudentDAO.LoadByEmail(email).toString()
            ).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    "not found"
            ).build();
        }
    }

    @POST
    @Path("/add")
    public Response add(
            @FormParam("name") String name,
            @FormParam("email") String email
    ) {        
        Student student = new Student(name, email);
        try {
            StudentDAO.Persist(student);
            return Response.status(Response.Status.OK).entity(
                    "persist with id:" + student.getdBId()
            ).build();
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            return Response.status(Response.Status.CONFLICT).entity(
                    "exist with email: " + email
            ).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    "not acceptable email: " + email
            ).build();
        }
    }

}
