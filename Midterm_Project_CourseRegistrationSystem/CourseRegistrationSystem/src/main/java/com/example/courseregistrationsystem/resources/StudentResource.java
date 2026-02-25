package com.example.courseregistrationsystem.resources;

import com.example.courseregistrationsystem.models.Student;
import com.example.courseregistrationsystem.services.StudentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Collection;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Inject
    private StudentService studentService;

    @GET
    public Collection<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") int id) {
        Student s = studentService.getById(id);
        if (s == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(s).build();
    }

    @POST
    public Response addStudent(@Context SecurityContext sc, Student s) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        Student created = studentService.create(s);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") int id, @Context SecurityContext sc, Student s) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        Student updated = studentService.update(id, s);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") int id, @Context SecurityContext sc) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        boolean deleted = studentService.delete(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}