package priv.mashton.goeuro.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class BusRoutes {
    List<BusRoute> busRoutes = new ArrayList<>();

    public void clearAllExistingRoutes() {
        busRoutes.clear();
    }

    public void addRoute(int routeId, Set<Integer> stationIds) {
        if (routeIdExists(routeId)) {
            throw new IllegalArgumentException("Route id already used");
        }
        BusRoute route = new BusRoute(routeId, stationIds);
        busRoutes.add(route);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (BusRoute route : busRoutes) {
            builder.append(route.toString()).append("\n");
        }

        return builder.toString();
    }

    public boolean isDirectConnection(int departureId, int arrivalId) {
        for (BusRoute route : busRoutes) {
            if (route.isDirectConnection(departureId, arrivalId)) {
                return true;
            }
        }
        return false;
    }

    boolean routeIdExists(int routeId) {
        for (BusRoute route : busRoutes) {
            if (route.getRouteId() == routeId) {
                return true;
            }
        }
        return false;
    }


}
