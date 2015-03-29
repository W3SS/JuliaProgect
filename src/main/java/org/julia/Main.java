package org.julia;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLClassLoader;

//import org.julia.service.PurchaseService;

/**
 * Created with IntelliJ IDEA.
 * User: Миша
 * Date: 18.09.14
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class Main {
        private static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static void printClassPath(boolean withJars){
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader) cl).getURLs();
        for (URL url : urls) {
            String file = url.getFile();
            if (!withJars && file.endsWith(".jar")) {
                continue;
            }
            System.out.println(file);
        }
    }

    public static void main(String[] args) throws Exception {
         printClassPath(false);
         startJetty(8080);
    }

    private static void startJetty(int port) throws Exception {
        Server server = new Server(port);
//        server.setHandler(getServletContextHandler(getContext()));
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("C:/Users/Миша/IdeaProjects/Julia_Project/target/Julia-1.war");
        server.start();
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        contextHandler.addEventListener(new ContextLoaderListener(context));
//        contextHandler.setResourceBase(new ClassPathResource("target").getURI().toString());
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath*:webapp/WEB-INF/spring/web.xml");
        return context;
    }
}
