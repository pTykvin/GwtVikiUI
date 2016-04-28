package shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class MyObject implements IsSerializable {
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
