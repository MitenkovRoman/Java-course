package server.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import server.auth.AuthenticationFilter;

/**
 * Created by valentin on 23.10.16.
 */
public class ApiServlet {
    @NotNull
    private static final Logger log = LogManager.getLogger(ApiServlet.class);

    public static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "server"
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthenticationFilter.class.getCanonicalName()
        );

        try {
            jettyServer.start();
            log.info("jettyServer start");
            jettyServer.join();
            log.info("jettyServer join");

        } finally {
            log.info("jettyServer destroyed");
            jettyServer.destroy();
        }
    }

    private ApiServlet() {
    }
}
