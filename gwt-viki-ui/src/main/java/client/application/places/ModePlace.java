package client.application.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ModePlace extends Place {
    private String helloName;

    public ModePlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    public static class Tokenizer implements PlaceTokenizer<ModePlace> {
        @Override
        public String getToken(ModePlace place) {
            return place.getHelloName();
        }

        @Override
        public ModePlace getPlace(String token) {
            return new ModePlace(token);
        }
    }
}
