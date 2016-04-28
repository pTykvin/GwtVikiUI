import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedGwt {

    public static void main(String[] args) throws Throwable {

        // Create an embedded Jetty server on port 8080
        Server server = new Server(8081);

        System.out.println(new File(".").getAbsolutePath());

        // Create a handler for processing our GWT app
        WebAppContext handler = new WebAppContext();
        handler.setContextPath("/");
        handler.setWar("./gwt-simple-draft.war");

        // If your app isn't packaged into a WAR, you can do this instead
//        WebAppContext altHandler = new WebAppContext();
//        altHandler.setResourceBase("./target/GwtApplication");
//        altHandler.setDescriptor("./apps/GwtApplication/WEB-INF/web.xml");
//        altHandler.setContextPath("/");
//        altHandler.setParentLoaderPriority(true);
        // Add it to the server
        server.setHandler(handler);
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStarted(LifeCycle event) {
                System.out.println("startted");
            }
        });
        // Other misc. options
        server.setThreadPool(new QueuedThreadPool(20));

        // And start it up
        server.start();
        server.join();
    }

}
