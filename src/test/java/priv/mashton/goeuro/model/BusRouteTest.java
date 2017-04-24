package priv.mashton.goeuro.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteTest {

    private class BusRouteWrapper extends BusRoute {
        BusRouteWrapper(int routeId, Set<Integer> busRoute) {
            super(routeId, busRoute);
        }

        boolean containsStationId(int stationId) {
            return busRoute.contains(stationId);
        }

        boolean routeIsEmpty() {
            return busRoute.isEmpty();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingWithNullSetThrowsIllegalArgumentException() {
        BusRoute route = new BusRoute(1, null);
    }

    @Test
    public void creatingWithValidStationIdAndEmptySetIsSuccessful() {
        //ARRANGE
        Set<Integer> routes = new HashSet<>();

        //ACT
        BusRouteWrapper route = new BusRouteWrapper(1, routes);

        //ASSERT
        assertThat(route.getRouteId(), is(equalTo(1)));
        assertTrue(route.routeIsEmpty());
    }

    @Test
    public void creatingWithValidStationIdAndPopulatedListIsSuccessful() {
        //ARRANGE
        Set<Integer> routes = new HashSet<>();
        routes.add(2);
        routes.add(3);
        routes.add(4);

        //ACT
        BusRouteWrapper route = new BusRouteWrapper(1, routes);

        //ASSERT
        assertThat(route.getRouteId(), is(equalTo(1)));
        assertTrue(!route.routeIsEmpty());
        assertTrue(route.containsStationId(2));
        assertTrue(route.containsStationId(3));
        assertTrue(route.containsStationId(4));
    }

    @Test
    public void isDirectConnectionReturnsTrueWhenFound() {
        //ARRANGE
        Set<Integer> routes = new HashSet<>();
        routes.add(2);
        routes.add(3);
        routes.add(4);

        //ACT
        BusRoute route = new BusRoute(1, routes);

        //ASSERT
        assertTrue(route.isDirectConnection(2,3));
        assertTrue(route.isDirectConnection(2,4));
        assertTrue(route.isDirectConnection(3,4));
        assertTrue(route.isDirectConnection(2,2));
        assertTrue(route.isDirectConnection(3,3));
        assertTrue(route.isDirectConnection(4,4));
    }

    @Test
    public void isDirectConnectionReturnsFalseWhenNoDirectConnection() {
        //ARRANGE
        Set<Integer> routes = new HashSet<>();
        routes.add(2);
        routes.add(3);
        routes.add(4);

        //ACT
        BusRoute route = new BusRoute(1, routes);

        //ASSERT
        assertFalse(route.isDirectConnection(2,5));
        assertFalse(route.isDirectConnection(5,5));
    }



}