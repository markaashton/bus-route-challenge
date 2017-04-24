package priv.mashton.goeuro.busrouteimporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.mashton.goeuro.model.BusRoutes;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

@Component
public class BusRouteDataImporter {

    @Autowired
    private BusRoutes busRoutes;

    private final int MINIMUM_ROUTE_LINE_LENGTH = 3;

    public void extractDataFromFile(@NotNull String file) throws FileNotFoundException, BusRouteImportException {
        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }

        busRoutes.clearAllExistingRoutes();
        try {
            populateBusRoutesFromDataFile(file);
        } catch (BusRouteImportException ex) {
            busRoutes.clearAllExistingRoutes();
            throw ex;
        }
    }

    void populateBusRoutesFromDataFile(@NotNull String filename) throws BusRouteImportException, FileNotFoundException {
        try (Reader reader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            int expectedRouteCount = extractRouteCountFromHeader(bufferedReader);
            extractAllBusRoutes(bufferedReader, expectedRouteCount);
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new BusRouteImportException("IOException while processing file");
        }
    }

    int extractRouteCountFromHeader(BufferedReader reader) throws BusRouteImportException, IOException {
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new BusRouteImportException("Invalid data file. No header row found.");
            }
            return Integer.valueOf(line);
        } catch (NumberFormatException ex) {
            throw new BusRouteImportException("Invalid data file. Header row is invalid.");
        }
    }

    void extractAllBusRoutes(BufferedReader reader, int expectedRouteCount) throws BusRouteImportException, IOException {
        int linesRead = 0;

        String line = reader.readLine();
        while (line != null) {
            linesRead++;
            if (linesRead > expectedRouteCount) {
                throw new BusRouteImportException("Invalid data file. More routes than specified in header.");
            }

            extractSingleBusRoute(line);
            line = reader.readLine();
        }

        if (linesRead < expectedRouteCount) {
            throw new BusRouteImportException("Invalid data file. Less routes than specified in header.");
        }
    }

    void extractSingleBusRoute(String line) throws BusRouteImportException {
        String lineArray[] = line.split(" ");

        if (lineArray.length < MINIMUM_ROUTE_LINE_LENGTH) {
            throw new BusRouteImportException("Invalid data file. A route line has less than " + MINIMUM_ROUTE_LINE_LENGTH + " elements.");
        }

        try {
            int routeId = Integer.valueOf(lineArray[0]);
            Set<Integer> stationIds = new HashSet<>();

            for (int i = 1; i < lineArray.length; i++) {
                int stationId = Integer.valueOf(lineArray[i]);
                stationIds.add(stationId);
            }

            busRoutes.addRoute(routeId, stationIds);
        } catch (NumberFormatException ex) {
            throw new BusRouteImportException("Invalid station id.");
        }

    }

}
