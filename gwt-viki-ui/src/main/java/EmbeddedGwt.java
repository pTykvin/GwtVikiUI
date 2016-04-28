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

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
