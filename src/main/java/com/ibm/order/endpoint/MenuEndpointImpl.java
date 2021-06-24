package com.ibm.order.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ibm.order.rest.OrderController;
import com.ibm.order.model.MenuItem;

@Component
public class MenuEndpointImpl implements MenuEndpoint {
	private final Logger logger = LoggerFactory.getLogger(MenuEndpointImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${menuservice.endpoint}")
	private String menuServiceEndpoint;
	
	public MenuEndpointImpl() {
		this.restTemplate = new RestTemplate();
	}
	
	@Override
    @HystrixCommand(fallbackMethod = "getMenuItem_fallBack", commandKey = "endpointGetMenuItem", commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") })
	public MenuItem getMenuItem(String menuItemNumber) {
		logger.info("Entered MenuEndpointImpl.getMenuItem().  menuItemNumber = {}", 
				menuItemNumber);

		MenuItem menuItem = null;
		
		//menuItem = new MenuItem("M123", "Entree", "Salmon Dinner", "Salmon with vegetables and rice", 8, 12.95);
		
		// TO DO FOR LAB:
		String menuServiceURL = "http://" + menuServiceEndpoint + "/menu/menuitem/"+ menuItemNumber;
		menuItem=this.restTemplate.getForObject(menuServiceURL, MenuItem.class);
		
		return menuItem;
	}
	
	public MenuItem getMenuItem_fallBack(String menuItemNumber) {

		logger.warn("!!!!!!!!!! IN FALLBACK.  getMenuItem_fallBack menuItemNumber=" + menuItemNumber);
		// We are pretending to return cached data
		MenuItem menuItem = new MenuItem(menuItemNumber, "Drinks", "Orange Juice", "A drink made from oranges", 5, 2.00);
		return menuItem;		
	}
}
