package com.eurowings.flightstatus.service;

import com.eurowings.flightstatus.model.FlightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightStatusService {
    private final RestTemplate restTemplate;

    @Value("${flightstatus.base}")
    private String flightstatusBase;

    @Value("${flightstatus.rqid}")
    private String flightstatusReqID;

    @Autowired
    public FlightStatusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFlightStatus(String flightPrefix, String flightNo, String travelDate) {
        FlightStatus flightStatus = new FlightStatus();
        Gson gson = new Gson();
        String flightStatusJsonString = "{}";

        if (travelDate.isEmpty()) {
            log.error("Empty response! travelDate is empty");
            return flightStatusJsonString;
        }
        String requestUrl = flightstatusBase.concat(flightPrefix)
                            .concat("/")
                            .concat(flightNo)
                            .concat("/")
                            .concat(travelDate.replace('-', '/'))
                            .concat("/")
                            .concat("?rqid=")
                            .concat(flightstatusReqID);

        log.info("Flight status search request url: " + requestUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        if (response == null) {
            log.error("Empty Response! No information found for the given data");
            return flightStatusJsonString;
        }

        try {
            JSONObject responseObj = new JSONObject(response.getBody());
            JSONObject responseObjData = responseObj.getJSONObject("data");

            flightStatus = flightStatus.fsMapper(responseObjData);
            flightStatusJsonString = gson.toJson(flightStatus);

            log.info("Flight status found!", flightStatusJsonString);
        } catch (Exception e) {
            log.error("Error Occurred while parsing the output data!", e);
        }

        return flightStatusJsonString;
    }
}