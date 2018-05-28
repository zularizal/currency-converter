currency-converter
===================

[![Build Status](https://travis-ci.org/Apilytic/currency-converter.svg?branch=master)](https://travis-ci.org/Apilytic/currency-converter)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7633098308134afeb2a0a7c15050528f?branch=master)](https://www.codacy.com/app/gogoluxecs/currency-converter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=apilytic/currency-converter&amp;utm_campaign=Badge_Grade)

# Introduction

Application to fetch currency rates from Yahoo Finance service, cache to database and provide API to accesss corss-rates.
There is an option to fetch rates directly from Yanoo Fianance services or Google Currency calculator and use this library as a adaper.

You can find brainstorming, stories, ideas and specifications in project's [Apilytic Wiki][].

# Operations

* Download currency iso codes to Redis
* Ingestion rate exchanges

# Version history

## 0.2.0.M1

* Complete rewriting
* Integrate new rate exchang services
  * yahoo finance
  * duckduckgo
* RxJava instead of Java8 streams


[Apilytic Wiki]: https://github.com/Apilytic/currency-converter/wiki
