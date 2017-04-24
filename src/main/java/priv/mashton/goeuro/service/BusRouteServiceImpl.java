package priv.mashton.goeuro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mashton.goeuro.model.BusRoutes;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    @Autowired
    private BusRoutes busRoutes;

    public boolean isRoute(int departureId, int arrivalId) {
        return busRoutes.isDirectConnection(departureId, arrivalId);
    }

}
