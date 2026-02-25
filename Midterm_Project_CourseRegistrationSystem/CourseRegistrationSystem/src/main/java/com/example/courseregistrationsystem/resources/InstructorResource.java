package com.example.courseregistrationsystem.resources;

import com.example.courseregistrationsystem.models.Instructor;
import com.example.courseregistrationsystem.services.InstructorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Collection;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstructorResource {

    @Inject
    private InstructorService instructorService;

    @GET
    public Collection<Instructor> getAllInstructors() {
        return instructorService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getInstructor(@PathParam("id") int id) {
        Instructor i = instructorService.getById(id);
        if (i == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(i).build();
    }

    @POST
    public Response addInstructor(@Context SecurityContext sc, Instructor i) {
        if (!(sc.isUserInRole("admin") || sc.isUserInRole("instructor"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin or Instructor only").build();
        }
        Instructor created = instructorService.create(i);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateInstructor(@PathParam("id") int id, @Context SecurityContext sc, Instructor i) {
        if (!(sc.isUserInRole("admin") || sc.isUserInRole("instructor"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin or Instructor only").build();
        }
        Instructor updated = instructorService.update(id, i);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteInstructor(@PathParam("id") int id, @Context SecurityContext sc) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        boolean deleted = instructorService.delete(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}