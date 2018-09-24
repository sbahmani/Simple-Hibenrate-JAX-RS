package com.github.sbahmani.rest;

import com.github.sbahmani.StudentDAO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sjb
 */
public class StudentServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new RESTApp();

    }

    @Test
    public void testId() {
        Response get1 = target("student/id/565585").request().get();
        assertEquals(get1.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        assertEquals(get1.readEntity(String.class), "not found");

        Response get2 = target("student/id/" + StudentDAO.LoadAll().get(0).getdBId()).request().get();
        assertEquals(get2.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(get2.readEntity(String.class), StudentDAO.LoadAll().get(0).toString());
    }

    @Test
    public void testEmail() {
        Response get1 = target("student/email/ali2@mail.com").request().get();
        assertEquals(get1.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        assertEquals(get1.readEntity(String.class), "not found");

        Response get2 = target("student/email/ali@mail.com").request().get();
        assertEquals(get2.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(get2.readEntity(String.class), StudentDAO.LoadByEmail("ali@mail.com").toString());
    }

    @Before
    public void testAdd() {
        MultivaluedMap<String, String> data1 = new MultivaluedHashMap<>();
        data1.add("name", "ali");
        data1.add("email", "ali-mail");
        Response post1 = target("student/add").request().post(Entity.form(data1));
        assertEquals(post1.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        assertEquals(post1.readEntity(String.class), "not acceptable email: ali-mail");

        MultivaluedMap<String, String> data2 = new MultivaluedHashMap<>();
        data2.add("name", "ali");
        data2.add("email", "ali@mail.com");
        Response post2 = target("student/add").request().post(Entity.form(data2));
        assertEquals(post2.getStatus(), Response.Status.OK.getStatusCode());
        assertTrue(post2.readEntity(String.class).contains("persist with id:"));

        MultivaluedMap<String, String> data3 = new MultivaluedHashMap<>();
        data3.add("name", "ali");
        data3.add("email", "ali@mail.com");
        Response post3 = target("student/add").request().post(Entity.form(data3));
        assertEquals(post3.getStatus(), Response.Status.CONFLICT.getStatusCode());
        assertEquals(post3.readEntity(String.class), "exist with email: ali@mail.com");
    }

    @After
    public void erase() {
        StudentDAO.LoadAll().forEach(StudentDAO::Delete);
    }

}
