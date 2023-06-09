package org.sebastianjaks.crypto_recommendation_service.rest_api.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class UserRequestCache {
	
	private Map<String, List<Date>> cacheHashmap = new HashMap<>();
	
	@Autowired
	private Environment env;
	
	
	/**
	 * logs the user request and check if rate limit is not exceeded
	 * @param ip
	 * @return true if the limit has been exceeded, false otherwise
	 */
	public synchronized boolean cacheAndLimitRequest(String ip) {
		//is it the first request of the user?
		if(!cacheHashmap.containsKey(ip))
		{
			List<Date> records = new ArrayList<>();
			records.add(new Date());
			cacheHashmap.put(ip, records);
			return false;
		}
		
		//load records of previous requests by the user
		List<Date> records = cacheHashmap.get(ip);
		
		//remove old non relevant records from outside the monitoring period
		removeOldRecords(records);
		
		//add record of this request
		records.add(new Date());
		
		//check rate limit
		return records.size()>Integer.parseInt(env.getProperty("user-request-limit"));
	}


	
	/**
	 * 
	 * @param records
	 * @return
	 */
	private void removeOldRecords(List<Date> records) {
		long now = new Date().getTime();
		long intervalStart = now-1000*Integer.parseInt(env.getProperty("user-request-limit-interval"));
		
		records.removeIf(r-> r.getTime()<intervalStart);
	}
}
