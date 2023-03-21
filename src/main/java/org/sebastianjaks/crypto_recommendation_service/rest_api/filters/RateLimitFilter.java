package org.sebastianjaks.crypto_recommendation_service.rest_api.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter implements Filter{

	@Autowired
	private UserRequestCache userRequestCache;
	
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		
		if(userRequestCache.cacheAndLimitRequest(ip))
		{
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.setStatus(429);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

}
