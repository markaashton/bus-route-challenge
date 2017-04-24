package priv.mashton.goeuro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.mashton.goeuro.dtos.RouteAvailabilityResponse;
import priv.mashton.goeuro.service.BusRouteService;

import javax.validation.constraints.NotNull;

@Controller
public class GoEuroRestController {

    @Autowired
    private BusRouteService service;

    @RequestMapping(value = "/api/direct", method = RequestMethod.GET, produces="application/json" )
    @ResponseBody
    public ResponseEntity<RouteAvailabilityResponse> checkForDirectConnection(@NotNull @RequestParam(value = "dep_sid") String departureId,
                                                                              @NotNull @RequestParam(value = "arr_sid") String arrivalId) {
        if (isNullOrEmpty(departureId, arrivalId)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            int departureIdInt = Integer.valueOf(departureId);
            int arrivalIdInt = Integer.valueOf(arrivalId);
            boolean isRouteAvailable = service.isRoute(departureIdInt, arrivalIdInt);
            return ResponseEntity.ok(new RouteAvailabilityResponse(departureIdInt, arrivalIdInt, isRouteAvailable));
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean isNullOrEmpty(String departureId, String arrivalId) {
        return departureId == null || departureId.isEmpty() || arrivalId == null || departureId.isEmpty();
    }
}
