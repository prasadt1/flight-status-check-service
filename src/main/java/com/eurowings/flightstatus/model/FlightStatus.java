package com.eurowings.flightstatus.model;

import lombok.Data;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class FlightStatus {
    private String id;
    private String flightPrefix;
    private String flightNumber;
    private String flightId;
    private String aircraft;
    private JSONObject status;
    private String departureAirport;
    private String departureAirportCode;
    private String estimatedDepartureDateTime;
    private String arrivalAirport;
    private String arrivalAirportCode;
    private String estimatedArrivalDateTime;
    private String airlineLogo;

    public FlightStatus fsMapper(JSONObject responseData) {
        try {
            this.setId(Long.toString(System.nanoTime()));
            this.setFlightPrefix(responseData.getJSONObject("resultHeader").getJSONObject("carrier").getString("fs"));
            this.setFlightNumber(responseData.getJSONObject("resultHeader").getString("flightNumber"));
            this.setFlightId(responseData.getString("flightId"));
            this.setAircraft(responseData.getJSONObject("additionalFlightInfo").getJSONObject("equipment").getString("name"));

            JSONObject responseStatusObject = responseData.getJSONObject("status");
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", responseStatusObject.getString("status"));
            statusObject.put("statusCode", responseStatusObject.getString("statusCode"));
            statusObject.put("statusDescription", responseStatusObject.getString("statusDescription"));
            statusObject.put("statusColor", responseStatusObject.getString("color"));
            statusObject.put("lastUpdatedText", responseStatusObject.getString("lastUpdatedText"));
            this.setStatus(statusObject);
            log.info("flight status for flight: " + this.getFlightPrefix() + this.getFlightNumber() + " is: " + statusObject.getString("statusDescription"));

            this.setDepartureAirport(responseData.getJSONObject("departureAirport").getString("name"));
            this.setDepartureAirportCode(responseData.getJSONObject("departureAirport").getString("fs"));
            this.setEstimatedDepartureDateTime(responseData.getJSONObject("schedule").getString("scheduledDepartureUTC"));

            this.setArrivalAirport(responseData.getJSONObject("arrivalAirport").getString("name"));
            this.setArrivalAirportCode(responseData.getJSONObject("arrivalAirport").getString("fs"));
            this.setEstimatedArrivalDateTime(responseData.getJSONObject("schedule").getString("scheduledArrivalUTC"));

            this.setAirlineLogo(responseData.getJSONObject("ticketHeader").getString("iconURL"));

        } catch (Exception e) {
            log.error("Error while mapping response data to model! ", e);
        }

        return this;
    }
}