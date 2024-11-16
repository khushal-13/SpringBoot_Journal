package com.khushal.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeatherResponse {

    public Current current;

    @Setter
    @Getter
    public class Current{

        @JsonProperty("temp_c")
        public int tempC;

        public int humidity;

        @JsonProperty("feelslike_c")
        public double feelslikeC;

    }


}
