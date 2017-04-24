package priv.mashton.goeuro.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteAvailabilityResponse {
    private Integer departureId;
    private Integer arrivalId;
    private Boolean routeAvailable;

    public RouteAvailabilityResponse() {

    }

    public RouteAvailabilityResponse(int departureId, int arrivalId, boolean routeAvailable) {
        this.departureId = departureId;
        this.arrivalId = arrivalId;
        this.routeAvailable = routeAvailable;
    }

    @JsonProperty("dep_sid")
    public Integer getDepartureId() {
        return departureId;
    }

    @JsonProperty("dep_sid")
    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    @JsonProperty("arr_sid")
    public Integer getArrivalId() {
        return arrivalId;
    }

    @JsonProperty("arr_sid")
    public void setArrivalId(Integer arrivalId) {
        this.arrivalId = arrivalId;
    }

    @JsonProperty("direct_bus_route")
    public Boolean getRouteAvailable() {
        return routeAvailable;
    }

    @JsonProperty("direct_bus_route")
    public void setRouteAvailable(Boolean routeAvailable) {
        this.routeAvailable = routeAvailable;
    }
}
