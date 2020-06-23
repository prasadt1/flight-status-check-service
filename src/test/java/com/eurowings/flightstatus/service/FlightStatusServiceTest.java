package com.eurowings.flightstatus.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.io.IOUtils;
import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FlightStatusServiceTest {
    @Mock
    private RestTemplate restTemplate = new RestTemplate();

    @InjectMocks
    private FlightStatusService flightStatusService = new FlightStatusService(restTemplate);

    private final String FLIGHT_PREFIX = "EW";
    private final String FLIGHT_NO = "9825";
    private final String TRAVEL_DATE = "2020-06-23";


    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(flightStatusService, "flightstatusBase", "https://www.flightstats.com/v2/api-next/flight-tracker/");
        ReflectionTestUtils.setField(flightStatusService, "flightstatusReqID", "i4cjheetoe");
    }

    @Test
    public void flightStatusTest() throws Exception {
        String originalAPIResponse = IOUtils.toString(this.getClass().getResourceAsStream("originalAPIResponse.json"), "UTF-8");

        String requestUrl = "https://www.flightstats.com/v2/api-next/flight-tracker/".concat(FLIGHT_PREFIX).concat("/")
                .concat(FLIGHT_NO).concat("/").concat(TRAVEL_DATE.replace('-', '/')).concat("/")
                .concat("?rqid=").concat("i4cjheetoe");

        Mockito.when(restTemplate.getForObject(requestUrl, String.class)).thenReturn(originalAPIResponse);

        String flightStatusResponse = flightStatusService.getFlightStatus(FLIGHT_PREFIX, FLIGHT_NO, TRAVEL_DATE);

        String expectedFlightStatusJSON = IOUtils.toString(this.getClass().getResourceAsStream("expectedFlightStatus.json"), "UTF-8");
        assertThatJson(flightStatusResponse).whenIgnoringPaths("id", "status").isEqualTo(expectedFlightStatusJSON);
    }
}