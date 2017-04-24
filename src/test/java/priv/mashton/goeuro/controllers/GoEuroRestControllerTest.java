package priv.mashton.goeuro.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import priv.mashton.goeuro.dtos.RouteAvailabilityResponse;
import priv.mashton.goeuro.service.BusRouteService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoEuroRestControllerTest {

    @Mock
    private BusRouteService service;

    @InjectMocks
    private GoEuroRestController controller;

    @Test
    public void checkForDirectConnectionReturnsValidResponse() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 200;
        when(service.isRoute(anyInt(), anyInt())).thenReturn(true);

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("1", "2");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
        RouteAvailabilityResponse dto = response.getBody();
        assertThat(dto.getDepartureId(), is(equalTo(1)));
        assertThat(dto.getArrivalId(), is(equalTo(2)));
        assertThat(dto.getRouteAvailable(), is(equalTo(true)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForNullDepartureId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection(null, "2");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForEmptyDepartureId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("", "2");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForNullArrivalId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("1", null);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForEmptyArrivalId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("1", "");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForNullParams() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection(null, null);

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForEmptyParams() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("", "");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForInvalidDepartureId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("berlin", "2");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

    @Test
    public void checkForDirectConnectionReturnsBadRequestForInvalidArrivalId() {
        //ARRANGE
        final int expectedResponseStatusCodeValue = 400;

        //ACT
        ResponseEntity<RouteAvailabilityResponse> response = controller.checkForDirectConnection("1", "berlin");

        //ASSERT
        assertThat(response.getStatusCodeValue(), is(equalTo(expectedResponseStatusCodeValue)));
    }

}