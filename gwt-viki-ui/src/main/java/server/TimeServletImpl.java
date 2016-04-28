package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import client.rpc.TimeServlet;
import shared.MyObject;

public class TimeServletImpl extends RemoteServiceServlet implements TimeServlet {
    @Override
    public String getTime() {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now());
    }

    @Override
    public String getDate() {
        return "test";
    }


    @Override
    public MyObject getObject() {
        MyObject myObject = new MyObject();
        myObject.setTime(getTime());
        return myObject;
    }
}
