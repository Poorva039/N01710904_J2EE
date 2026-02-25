package com.example.courseregistrationsystem.app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class CourseRegistrationApp extends Application {
    // Scans for @Path-annotated classes in the package
}
