package cenk.ozan.backend.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cenk.ozan.backend.exception.OzCurrencyPairNotFetchedException;
import cenk.ozan.backend.exception.OzDateParsingException;
import cenk.ozan.backend.exception.OzNoRateForGivenDateException;
import cenk.ozan.backend.exception.OzUnknownCurrencyCodeExpectation;
import cenk.ozan.backend.exception.OzUnknownCurrencyCodesException;
import cenk.ozan.backend.pojo.OzConvertionResponse;
import cenk.ozan.backend.pojo.RatesApiRateResponse;
import cenk.ozan.jpa.entity.convertion.OzConvertion;
import cenk.ozan.jpa.entity.rate.OzRate;

@Service
public class OzRateService extends OzRateServiceBasic{
	private static final Logger logger = LoggerFactory.getLogger(OzRateService.class);
	
	public void saveResponse(RatesApiRateResponse response) throws OzUnknownCurrencyCodesException, OzDateParsingException {
		String dateString = response.getDate();
		LocalDate date = null;
		try {
			date = LocalDate.parse(dateString, RATES_API_DATE_FORMAT);
			mapRate(response.getBase(), date, response.getRates());
		} catch (Exception e){
			throw new OzDateParsingException(response.getBase(), e);
		}
		logger.debug("Exchange rates for currency {} is fetched from rates-api", response.getBase());
	}

	public OzRate getRate(String baseCode, String measuringCode) throws OzCurrencyPairNotFetchedException, OzNoRateForGivenDateException, OzUnknownCurrencyCodeExpectation {
		return getLatestRate(baseCode, measuringCode);
	}

	public OzConvertionResponse convert(String from, String to, float amount) throws Exception {
		OzRate ozRate = getLatestRate(from, to);
		OzConvertion conv = saveConvertion(ozRate, amount);
		float targetAmount = amount * ozRate.getRate();
		OzConvertionResponse resp = new OzConvertionResponse();
		resp.setTransactionId(conv.getId());
		resp.setTargetAmount(targetAmount);
		return resp;
	}

	public void prepare() {
		super.prepare();
	}
	
}