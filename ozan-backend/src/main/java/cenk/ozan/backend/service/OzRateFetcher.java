package cenk.ozan.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cenk.ozan.backend.pojo.RatesApiRateResponse;
import cenk.ozan.jpa.common.OzConstants;
import lombok.Getter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class OzRateFetcher implements OzConstants {
	private static final Logger logger = LoggerFactory.getLogger(OzRateFetcher.class);
	private static final OkHttpClient client;
	@Autowired
	private OzRateService rateService;
	@Value("${ozan.source.api.url:https://api.ratesapi.io/api/latest}")
	private String url;
	public final List<String> fetchFailures = new ArrayList<>();
	static {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(30, TimeUnit.SECONDS);
		builder.readTimeout(30, TimeUnit.SECONDS);
		builder.writeTimeout(30, TimeUnit.SECONDS);
		builder.retryOnConnectionFailure(true);
		client = builder.build();
	}
	
	public void prepare() {
		rateService.prepare();
	}
	
	public List<String> fetch() {
		fetchFailures.clear();
		for(String baseCurrencyCode:CURRENCY_CODES) {
			fetchData(url, baseCurrencyCode);
		}
		return fetchFailures;
	}
	
	@Getter
	private class RateApiFetchCallback implements Callback{
		private final String currencyCode;
		
		RateApiFetchCallback(String currencyCode){
			this.currencyCode = currencyCode;
		}

		@Override
		public void onFailure(Call call, IOException e) {
			fetchFailures.add(currencyCode);
		}

		@Override
		public void onResponse(Call call, Response resp) throws IOException {
			if(resp!=null) {
				ResponseBody body = resp.body();
				if(body!=null) {
					String bodyString = body.string();
					if(bodyString!=null) {
						RatesApiRateResponse fxRateResponse = JACKSON_MAPPER.readValue(bodyString, RatesApiRateResponse.class);
						try {
							rateService.saveResponse(fxRateResponse);
							return;
						} catch (Exception e) {
							logger.error(e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
			fetchFailures.add(currencyCode);
		}
	}

	private void fetchData(String url, String currencyCode) {
		HttpUrl finalUrl = HttpUrl.parse(url).newBuilder()
				.addQueryParameter("base", currencyCode)
				.build();
		logger.debug("url: {}", finalUrl);
		Request request = new Request.Builder().url(finalUrl)
				.addHeader("charset", "utf-8")
				.build();
		client.newCall(request).enqueue(new RateApiFetchCallback(currencyCode));
	}

}
