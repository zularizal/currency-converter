package org.apilytic.currency.ingestion.rate;

import org.apilytic.currency.ingestion.IngestionSync;
import org.apilytic.currency.ingestion.rate.parser.RateParser;
import org.apilytic.currency.persistence.domain.Currency;
import org.apilytic.currency.persistence.domain.Exchange;
import org.apilytic.currency.persistence.repository.CurrencyRepository;
import org.apilytic.currency.persistence.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RateSyncher implements IngestionSync {

	@Autowired
	private ExchangeRepository exchangeRepo;
	@Autowired
	private CurrencyRepository currencyRepo;
	@Autowired
	private RateFetch rateFecher;
	@Autowired
	private RateParser rateParser;

	@Autowired
	private Exchange exchange;

	@Override
	public void sync() {
		Stream<Currency> currencies = StreamSupport.stream(currencyRepo.findAll().spliterator(), false);

		currencies.peek(c -> {
			Map<String, String> h = c.getCodes().stream()
					.collect(Collectors.toMap(x -> x, x -> "0"));

			List<Exchange> exchanges = c.getCodes().stream().map(code -> {
				Map<String, String> filterCurrentCode = h.entrySet().stream()
						.filter(x -> !x.getKey().equals(code))
						.collect(Collectors.toMap(
								Map.Entry::getKey, entry -> rateParser.parse(
										rateFecher.fetch(code + entry.getKey()))
						));

				exchange.setId(code);
				exchange.setRates(filterCurrentCode);

				return exchange;
			}).collect(Collectors.toList());

			exchangeRepo.save(exchanges);
		}).findFirst();
	}
}
