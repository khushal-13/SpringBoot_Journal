package com.khushal.journalApp.service;

import com.khushal.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String apikey = "bd63dde32ed7429ca98172844240711";

    private static final String API = "http://api.weatherapi.com/v1/current.json?key=<apikey>&q=<city>";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI =  appCache.APP_CACHE.get("weather_api").replace("<apikey>", apikey).replace("<city>", city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;

    }


}
