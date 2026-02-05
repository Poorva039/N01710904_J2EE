package com.example.employeemanagementsystem.resources;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.services.EmployeemanagementSystemService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeemanagementSystemResource {

    @Inject
    private EmployeemanagementSystemService employeeService;

    @GET
    @RolesAllowed({"user", "admin"})
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "admin"})
    public Response getEmployee(@PathParam("id") int id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(employee).build();
    }


    @POST
    @RolesAllowed("admin")
    public Response addCoffee(@Context SecurityContext sc, Employee employee) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        Employee created = employeeService.create(employee);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateEmployee(@Context SecurityContext sc, @PathParam("id") int id, Employee employee) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        Employee updated = employeeService.update(id, employee);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }



    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteEmployee(@Context SecurityContext sc, @PathParam("id") int id) {
        boolean deleted = employeeService.delete(id);
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admin only").build();
        }
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}