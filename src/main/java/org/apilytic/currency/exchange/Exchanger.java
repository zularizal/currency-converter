package org.apilytic.currency.exchange;

import org.apilytic.currency.persistence.domain.CurrencyPair;
import org.apilytic.currency.persistence.domain.Exchange;
import org.apilytic.currency.persistence.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class Exchanger implements RateExchange {

	@Autowired
	private ExchangeRepository exchangeRepository;

	@Override
	public String exchange(CurrencyPair pair) {

		Exchange exchange = exchangeRepository.findOne(pair.from());

		Optional<Map.Entry<String, String>> first = exchange.getRates().entrySet().stream()
				.filter(x -> x.getKey().equals(pair.to()))
				.findFirst();

		return first.get().getValue();
	}
}
