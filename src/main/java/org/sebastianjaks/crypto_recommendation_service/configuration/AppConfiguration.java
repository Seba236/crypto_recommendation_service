package org.sebastianjaks.crypto_recommendation_service.configuration;

import org.sebastianjaks.crypto_recommendation_service.rest_api.filters.UserRequestCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfiguration {
	
	@Bean
	@Scope("singleton")
	public UserRequestCache userRequestCacheSingleton() {
		return new UserRequestCache();
	}
	
}
