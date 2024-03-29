package com.namyesol.tuesday;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class TuesdayApplication {
    public static void main(String[] args) throws ServletException, LifecycleException {
        String webappDirLocation = "src/main/webapp";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.valueOf(webPort));

        Connector connector = tomcat.getConnector();
        connector.setURIEncoding("UTF-8");

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClass = new File("target/classes");

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
            additionWebInfClass.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
