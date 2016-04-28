package client.application.places;

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
