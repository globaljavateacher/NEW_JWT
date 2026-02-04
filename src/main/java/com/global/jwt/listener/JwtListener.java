package com.global.jwt.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JwtListener implements ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("JwtListener.contextInitialized");
        ServletContext context = sce.getServletContext();
        try (InputStream is = context.getResourceAsStream("/WEB-INF/config/config.properties")) {
            if (is != null) {
            	Properties props = new Properties();
                props.load(is);
                context.setAttribute("appConfig", props);
            } else {
                System.err.println("ERROR: File not found. config.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
