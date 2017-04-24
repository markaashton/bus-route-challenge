package priv.mashton.goeuro;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import priv.mashton.goeuro.dtos.RouteAvailabilityResponse;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BusRouteChallengeApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private ApplicationContext ctx;

    @Before
    public void setup() throws Exception {
        CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        runner.run ("tests/bus-route-data-file.txt");
    }

    @Test
    public void restServiceReturnsStatusOkAndTrueForAcceptableRequest() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("dep_sid", 1)
                .queryParam("arr_sid", 2)
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        RouteAvailabilityResponse responseBody = response.getBody();
        assertTrue(responseBody.getRouteAvailable());
    }

    @Test
    public void restServiceReturnsStatusOkAndFalseForAcceptableRequest() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("dep_sid", 0)
                .queryParam("arr_sid", 9)
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        RouteAvailabilityResponse responseBody = response.getBody();
        assertFalse(responseBody.getRouteAvailable());
    }

    @Test
    public void restServiceReturnsBadRequestForMissingDepartureIdParam() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("dep_sid", 0)
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void restServiceReturnsBadRequestForMissingArrivalIdParam() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("arr_sid", 0)
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void restServiceReturnsBadRequestForInvalidDepartureIdParam() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("dep_sid", "berlin")
                .queryParam("arr_sid", 0)
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void restServiceReturnsBadRequestForInvalidArrivalIdParam() throws Exception {
        //ARRANGE
        final String BASE_URL = "http://localhost:8088/";

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/api/direct")
                .queryParam("dep_sid", 1)
                .queryParam("arr_sid", "berlin")
                .build()
                .toUri();

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = rest.getForEntity(targetUrl, RouteAvailabilityResponse.class);

        //ASSERT
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

}
