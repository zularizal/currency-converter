package org.apilytic.currency.ingestion.rate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class YahooExchangeServiceIntegrationTest extends YahooExchangeService {

	@Autowired
	private YahooExchangeService rateIngestion;

	@Test
	public void testSyncInBatch() {
		// FIXME integration is not workikng.
		rateIngestion.sync();
	}

}
