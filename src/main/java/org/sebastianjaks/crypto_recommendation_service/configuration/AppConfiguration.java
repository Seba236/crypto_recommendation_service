package org.sebastianjaks.crypto_recommendation_service.configuration;

import org.sebastianjaks.crypto_recommendation_service.rest_api.filters.UserRequestCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("file:${user.dir}/config/application.properties")
public class AppConfiguration {
	
	@Bean
	@Scope("singleton")
	public UserRequestCache userRequestCacheSingleton() {
		return new UserRequestCache();
	}
	
}
