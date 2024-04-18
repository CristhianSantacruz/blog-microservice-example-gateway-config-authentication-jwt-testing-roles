package com.klashz;


import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title="POST-SERVICE API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Backend Developer @klashz",
                        url = "https://blog-frontend-black-one.vercel.app/home/about-me",
                        email = "crimant@espol.edu.ec")
                )
)
public class PostServiceApp extends Application { }
