package com.example.employeemanagementsystem.auth;

import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final Map<String, String> USERS = new HashMap<>();
    private static final Map<String, String> ROLES = new HashMap<>();

    static {
        USERS.put("admin", "adminpass");
        USERS.put("user", "userpass");

        ROLES.put("admin", "admin");
        ROLES.put("user", "user");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println("AUTH: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abort(requestContext);
            return;
        }

        String base64 = authHeader.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64));
        String[] parts = decoded.split(":", 2);

        System.out.println("DECODED: " + decoded);

        if (parts.length != 2 || !USERS.containsKey(parts[0]) || !USERS.get(parts[0]).equals(parts[1])) {
            abort(requestContext);
            return;
        }

        String username = parts[0];
        String role = ROLES.get(username);

        System.out.println("USER: " + username + ", ROLE: " + role);

        SecurityContext originalContext = requestContext.getSecurityContext();

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            @Override
            public boolean isUserInRole(String roleName) {
                String userRole = ROLES.get(username);
                return userRole != null && userRole.equals(roleName);
            }

            @Override
            public boolean isSecure() {
                return requestContext.getUriInfo().getAbsolutePath().getScheme().equals("https");
            }

            @Override
            public String getAuthenticationScheme() {
                return SecurityContext.BASIC_AUTH;
            }
        });
    }

    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"EmployeeManagementSystem\"")
                .build());
    }
}