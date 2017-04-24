package priv.mashton.goeuro.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class BusRoute {
    private int routeId;

    Set<Integer> busRoute = new HashSet<>();

    public BusRoute(int routeId, Set<Integer> busRoute) {
        if (busRoute == null) {
            throw new IllegalArgumentException("busRoute set can not be null");
        }

        this.routeId = routeId;
        this.busRoute = busRoute;
    }

    public int getRouteId() {
        return routeId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Iterator<Integer> iterator = busRoute.iterator();

        while (iterator.hasNext()) {
            Integer stationId = iterator.next();
            builder.append(stationId.toString());
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }

        return "[stationId:" + routeId + ", stops: " + builder.toString() + "]";

    }

    public boolean isDirectConnection(int id1, int id2) {
        return (busRoute.contains(id1) && busRoute.contains(id2));
    }

}
