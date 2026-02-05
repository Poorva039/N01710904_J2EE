package com.example.employeemanagementsystem.recource;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.services.EmployeemanagementSystemService;
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
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getEmployee(@PathParam("id") int id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(employee).build();
    }

    @POST
    public Response addEmployee(Employee employee) {
        Employee created = employeeService.create(employee);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") int id, Employee employee) {
        Employee updated = employeeService.update(id, employee);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        boolean deleted = employeeService.delete(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}
