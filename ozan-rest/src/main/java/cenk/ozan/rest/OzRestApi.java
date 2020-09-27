package cenk.ozan.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import cenk.ozan.backend.service.OzRateFetcher;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"cenk.ozan"})
@EnableJpaRepositories(basePackages = {"cenk.ozan.jpa.repo"})
@EntityScan(basePackages = "cenk.ozan.jpa.entity")
public class OzRestApi {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OzRateFetcher fetcher;

	public static void main(String[] args) {
		SpringApplication.run(OzRestApi.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		fetcher.prepare();
		fetcher.fetch();
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(false);
		filter.setAfterMessagePrefix("Request:\n");
		return filter;
	}
}
