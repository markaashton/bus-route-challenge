package priv.mashton.goeuro.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.mashton.goeuro.busrouteimporter.BusRouteDataImporter;
import priv.mashton.goeuro.busrouteimporter.BusRouteImportException;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BusRouteServiceIntegrationTest {

    @Autowired
    private BusRouteDataImporter importer;

    @Autowired
    private BusRouteServiceImpl busRouteService;

    @Before
    public void setup() throws Exception {
        importer.extractDataFromFile("tests/bus-route-data-file.txt");
    }

    @Test
    public void isRouteReturnsTrueForValidDirectConnections() throws Exception {
        //ACT & ASSERT
        assertTrue(busRouteService.isRoute(1,2));
        assertTrue(busRouteService.isRoute(3,6));
        assertTrue(busRouteService.isRoute(2,4));
    }

    @Test
    public void isRouteReturnsFalseForInvalidDirectConnections() throws Exception {
        //ACT & ASSERT
        assertFalse(busRouteService.isRoute(3,7));
    }

    @Test(expected = FileNotFoundException.class)
    public void importerThrowsFileNotFoundForMissingFile() throws Exception {
        importer.extractDataFromFile("tests/this-file-does-not-exist.txt");
    }

    @Test(expected = BusRouteImportException.class)
    public void importerThrowsBusRouteExceptionForEmptyFile() throws Exception {
        importer.extractDataFromFile("tests/bus-route-empty-data-file.txt");
    }

}
