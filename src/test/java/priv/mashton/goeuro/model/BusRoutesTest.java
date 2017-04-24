package priv.mashton.goeuro.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRoutesTest {

    private class BusRoutesWrapper extends BusRoutes {

        void setBusRoutes(List<BusRoute> busRoutes) {
            this.busRoutes = busRoutes;
        }

        boolean isEmpty() {
            return busRoutes.isEmpty();
        }
    }

    @Test
    public void busRoutesIsCreatedWithNoRoutes() {
        //ACT
        BusRoutesWrapper routes = new BusRoutesWrapper();

        //ASSERT
        assertTrue(routes.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRouteThrowsIllegalArgumentExceptionWhenStationIdSetIsNull() {
        //ARRANGE
        BusRoutes routes = new BusRoutes();

        //ACT
        routes.addRoute(1, null);
    }

    @Test
    public void addRouteSuccessfullyAddsRoute() {
        //ARRANGE
        BusRoutes routes = new BusRoutes();
        Set<Integer> stationIds = new HashSet<>();
        stationIds.add(1);
        stationIds.add(2);

        //ACT
        routes.addRoute(1, stationIds);

        //ASSERT
        assertTrue(routes.routeIdExists(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRouteThrowsIllegalArgumentExceptionWhenRouteIdAlreadyExists() {
        //ARRANGE
        BusRoutes routes = new BusRoutes();
        Set<Integer> stationIds = new HashSet<>();
        stationIds.add(1);
        stationIds.add(2);
        routes.addRoute(1, stationIds);

        //ACT
        routes.addRoute(1, stationIds);
    }

    @Test
    public void isDirectConnectReturnsTrueWhenFound() {
        //ARRANGE
        BusRoutesWrapper routes = new BusRoutesWrapper();
        List<BusRoute> busRoutes = new ArrayList<>();
        BusRoute mockedBusRoute = mock(BusRoute.class);
        when(mockedBusRoute.isDirectConnection(anyInt(), anyInt())).thenReturn(true);
        busRoutes.add(mockedBusRoute);
        routes.setBusRoutes(busRoutes);

        //ACT
        boolean directConnection = routes.isDirectConnection(1,2);

        //ASSERT
        assertTrue(directConnection);
    }

    @Test
    public void isDirectConnectReturnsFalseWhenNotFound() {
        //ARRANGE
        BusRoutesWrapper routes = new BusRoutesWrapper();
        List<BusRoute> busRoutes = new ArrayList<>();
        BusRoute mockedBusRoute = mock(BusRoute.class);
        when(mockedBusRoute.isDirectConnection(anyInt(), anyInt())).thenReturn(false);
        busRoutes.add(mockedBusRoute);
        routes.setBusRoutes(busRoutes);

        //ACT
        boolean directConnection = routes.isDirectConnection(1,2);

        //ASSERT
        assertFalse(directConnection);
    }

}
