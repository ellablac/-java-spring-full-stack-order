package com.ibm.order.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.model.MenuItem;

@Component
public class MenuEndpointImpl implements MenuEndpoint {
	
	private RestTemplate restTemplate;
	
	@Value("${menuservice.endpoint}")
	private String menuServiceEndpoint;
	
	public MenuEndpointImpl() {
		this.restTemplate = new RestTemplate();
	}
	
	@Override
	public MenuItem getMenuItem(String menuItemNumber) {

		MenuItem menuItem = null;
		
		menuItem = new MenuItem("M123", "Entree", "Salmon Dinner", "Salmon with vegetables and rice", 8, 12.95);
		
		// TO DO FOR LAB:
		String menuServiceURL = "http://" +  menuServiceEndpoint + "menu/items";
		//menuItem = this.restTemplate.getForObject(menuServiceURL, MenuItem.class);
		
		return menuItem;
	}
}
