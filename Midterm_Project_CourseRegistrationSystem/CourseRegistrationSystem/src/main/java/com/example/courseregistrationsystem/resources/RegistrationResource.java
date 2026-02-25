package com.example.courseregistrationsystem.resources;

import com.example.courseregistrationsystem.models.Course;
import com.example.courseregistrationsystem.models.Student;
import com.example.courseregistrationsystem.services.CourseService;
import com.example.courseregistrationsystem.services.StudentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @Inject
    private StudentService studentService;

    @Inject
    private CourseService courseService;

    @POST
    @Path("/enroll")
    public Response enroll(@Context SecurityContext sc,
                           @QueryParam("studentId") int studentId,
                           @QueryParam("courseId") int courseId) {

        if (!(sc.isUserInRole("student") || sc.isUserInRole("admin"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Student or Admin only").build();
        }

        Student s = studentService.getById(studentId);
        if (s == null) return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        Course c = courseService.getById(courseId);
        if (c == null) return Response.status(Response.Status.NOT_FOUND).entity("Course not found").build();

        boolean ok = studentService.enroll(studentId, courseId);
        if (!ok) return Response.status(Response.Status.BAD_REQUEST).entity("Could not enroll").build();

        return Response.ok(s).build();
    }

    @POST
    @Path("/drop")
    public Response drop(@Context SecurityContext sc,
                         @QueryParam("studentId") int studentId,
                         @QueryParam("courseId") int courseId) {

        // allow admin OR student
        if (!(sc.isUserInRole("student") || sc.isUserInRole("admin"))) {
            return Response.status(Response.Status.FORBIDDEN).entity("Student or Admin only").build();
        }

        Student s = studentService.getById(studentId);
        if (s == null) return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();

        boolean ok = studentService.drop(studentId, courseId);
        if (!ok) return Response.status(Response.Status.BAD_REQUEST).entity("Not enrolled / Could not drop").build();

        return Response.ok(s).build();
    }
}