package priv.mashton.goeuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import priv.mashton.goeuro.busrouteimporter.BusRouteDataImporter;

@Component
public class BusRouteCliRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusRouteDataImporter importer;

    @Override
    public void run(String... strings) throws Exception {
        if (strings.length > 0) {
            String filename= strings[0];
            try {
                importer.extractDataFromFile(filename);
                logger.info("Bus Route Data imported");
            } catch (Exception ex) {
                logger.error("Failed to import Bus Route Data. " + ex.getMessage());
            }
        }
    }
}
