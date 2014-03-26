package org.apilytic.currency.ingestion.rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apilytic.currency.ingestion.rate.provider.ExchangeRate;
import org.apilytic.currency.ingestion.rate.provider.YahooFinanceManager;
import org.apilytic.currency.ingestion.rate.provider.YahooQueryRateBuilder;
import org.apilytic.currency.ingestion.rate.provider.YahooQueryRateParser;
import org.apilytic.currency.persistence.domain.Rate;
import org.apilytic.currency.persistence.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sync currencies to database.
 * 
 * @author Georgi Lambov
 * 
 */
@Service
public class YahooExchangeService implements RateIngestion {

	@Autowired
	private YahooFinanceManager yahooFinanaceManager;

	@Autowired
	private YahooQueryRateBuilder queryRateBuilder;

	@Autowired
	private RateRepository rateRepo;

	@Autowired
	private YahooQueryRateParser rateParser;

	private List<Rate> rates;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apilytic.currency.ingestion.rate.RateIngestion#sync()
	 */
	@Override
	public void sync() {

		System.out.println(queryRateBuilder.createQueryRate());

		rateParser.setQueryRate(queryRateBuilder.createQueryRate());
		Set<String> rateQueryChunks = rateParser.splitInChunks();

		rates = new ArrayList<Rate>();

		for (String rateQuery : rateQueryChunks) {
			yahooFinanaceManager.setExchangeQuery(rateQuery);
			List<? extends ExchangeRate> providedRates = yahooFinanaceManager
					.provideRate();

			for (ExchangeRate exchangeRate : providedRates) {
				Map<String, String> values = new HashMap<String, String>();
				values.put(exchangeRate.toCurrency(), exchangeRate.rate());

				Rate r = new Rate();
				r.setKey(Rate.key(exchangeRate.fromCurrency()));
				r.setValue(values);

				rates.add(r);
			}
		}

		// FIXME save rates implementation
		rateRepo.save(rates);
	}
}
