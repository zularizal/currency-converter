package org.apilytic.currency.ingestion.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YahooFinanceFetcher {

	private static final String URL = "https://query1.finance.yahoo" +
			".com/v8/finance/chart/EURUSD=X?region=US&lang=en-US&range=1d&includePrePost=false&interval=1d";

	@Autowired
	private RestTemplate restTemplate;

	public void fetch() {
		restTemplate.getForEntity(URL, String.class);
	}
}
