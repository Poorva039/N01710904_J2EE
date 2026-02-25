package com.example.courseregistrationsystem.resources;

import com.example.courseregistrationsystem.models.Course;
import com.example.courseregistrationsystem.services.CourseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Collection;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject
    private CourseService courseService;

    @GET
    public Collection<Course> getAllCourses() {
        return courseService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getCourse(@PathParam("id") int id) {
        Course c = courseService.getById(id);
        if (c == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(c).build();
    }

    @POST
    public Response addCourse(@Context SecurityContext sc, Course c) {
        if (!(sc.isUserInRole("admin") || sc.isUserInRole("instructor"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin or Instructor only").build();
        }
        Course created = courseService.create(c);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCourse(@PathParam("id") int id, @Context SecurityContext sc, Course c) {
        if (!(sc.isUserInRole("admin") || sc.isUserInRole("instructor"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin or Instructor only").build();
        }
        Course updated = courseService.update(id, c);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") int id, @Context SecurityContext sc) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        boolean deleted = courseService.delete(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}