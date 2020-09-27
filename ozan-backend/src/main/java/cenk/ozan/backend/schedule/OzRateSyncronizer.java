package cenk.ozan.backend.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cenk.ozan.backend.service.OzRateFetcher;

@Component
public class OzRateSyncronizer {
	private static final Logger logger = LoggerFactory.getLogger(OzRateSyncronizer.class);
	@Autowired
	private OzRateFetcher fetcher;
	
	// ECB publish exchange rate data around 16:00/CET
	private final static String CRON = "0 0 17 * * MON-FRI";
	private final static String CENTRAL_EUROPE_TIME_ZONE = "CET";

	@Scheduled(cron = CRON, zone = CENTRAL_EUROPE_TIME_ZONE)
	public void syncRates() {
		logger.debug("FX rates will be fetched from Rates API");
		fetcher.fetch();
		logger.debug("FX rates are fetched from Rates API");
	}

}
