package priv.mashton.goeuro.busrouteimporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import priv.mashton.goeuro.model.BusRoutes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteDataImporterTest {

    @Mock
    private BusRoutes mockedRoutes;

    @InjectMocks
    private BusRouteDataImporter dataImporter = new BusRouteDataImporter();

     @Test(expected = IOException.class)
    public void extractRouteCountFromHeaderThrowsIOExceptionWhenIOException() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        doThrow(new IOException()).when(mockedReader).readLine();

        //ACT
        int value = dataImporter.extractRouteCountFromHeader(mockedReader);
    }

    @Test(expected = BusRouteImportException.class)
    public void extractRouteCountFromHeaderThrowsBusRouteExceptionWhenLineIsNull() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        when(mockedReader.readLine()).thenReturn(null);

        //ACT
        int value = dataImporter.extractRouteCountFromHeader(mockedReader);
    }

    @Test(expected = BusRouteImportException.class)
    public void extractRouteCountFromHeaderThrowsBusRouteExceptionWhenLineIsNotANumber() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        when(mockedReader.readLine()).thenReturn("i am not a number");

        //ACT
        dataImporter.extractRouteCountFromHeader(mockedReader);
    }

    @Test()
    public void extractRouteCountFromHeaderReturnsValidNumberForValidString() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        when(mockedReader.readLine()).thenReturn("3");

        //ACT
        int value = dataImporter.extractRouteCountFromHeader(mockedReader);

        //ASSERT
        assertThat(value, is(equalTo(3)));
    }

    @Test(expected = IOException.class)
    public void extractAllBusRouteThrowsIOExceptionWhenIOException() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        doThrow(new IOException()).when(mockedReader).readLine();

        //ACT
        dataImporter.extractAllBusRoutes(mockedReader, 1);
    }

    @Test(expected = BusRouteImportException.class)
    public void extractAllBusRoutesThrowsBusRouteExceptionWhenLessLinesThanExpected() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        when(mockedReader.readLine()).thenReturn(null);

        //ACT
        dataImporter.extractAllBusRoutes(mockedReader, 1);
    }

    @Test(expected = BusRouteImportException.class)
    public void extractAllBusRoutesThrowsBusRouteExceptionWhenMoreLinesThanExpected() throws Exception {
        //ARRANGE
        BufferedReader mockedReader = mock(BufferedReader.class);
        when(mockedReader.readLine()).thenReturn("some string");

        //ACT
        dataImporter.extractAllBusRoutes(mockedReader, 1);
    }

    @Test(expected = BusRouteImportException.class)
    public void extractSingleBusRouteThrowsBusRouteExceptionWhenLineContainsLessThanThreeElements() throws Exception {
        dataImporter.extractSingleBusRoute("0 1");
    }

    @Test(expected = BusRouteImportException.class)
    public void extractSingleBusRouteThrowsBusRouteExceptionWhenBusRouteIdIsInvalid() throws Exception {
        dataImporter.extractSingleBusRoute("berlin 1 2 3 4");
    }

    @Test(expected = BusRouteImportException.class)
    public void extractSingleBusRouteThrowsBusRouteExceptionWhenStationIdIsInvalid() throws Exception {
        dataImporter.extractSingleBusRoute("3 1 2 berlin 4");
    }

    @Test
    public void extractSinglebusRouteCorrectlyCreatesBusRoute() throws Exception {
        //ARRANGE
        ArgumentCaptor<Integer> routeIdCaptor = ArgumentCaptor.forClass(Integer.class);
        Class<HashSet<Integer>> setClass = (Class<HashSet<Integer>>)(Class)HashSet.class;
        ArgumentCaptor<HashSet<Integer>> stationIdsCaptor = ArgumentCaptor.forClass(setClass);


        //ACT
        dataImporter.extractSingleBusRoute("1 2 3 4");

        //ASSERT
        verify(mockedRoutes).addRoute(routeIdCaptor.capture(), stationIdsCaptor.capture());
        int routeId = routeIdCaptor.getValue();
        Set<Integer> stationIds = stationIdsCaptor.getValue();

        assertThat(routeId, is(equalTo(1)));
        assertTrue(stationIds.contains(2));
        assertTrue(stationIds.contains(3));
        assertTrue(stationIds.contains(4));
    }

}
