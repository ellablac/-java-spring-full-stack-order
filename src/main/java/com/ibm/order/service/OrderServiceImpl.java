package com.ibm.order.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.order.model.OrderInput;
import com.ibm.order.model.Order;
import com.ibm.order.model.OrderInputMenuItem;
import com.ibm.order.model.MenuItem;
import com.ibm.order.model.OrderMenuItem;

import com.ibm.order.repo.OrderRepo;
import com.ibm.order.endpoint.MenuEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private MenuEndpoint menuEndpoint;
	
	@Override
	public Order getOrder(String orderNumber) {
		
		logger.info("Entered OrderServiceImpl.getOrder().  orderNumber=" + orderNumber);
		
		Order order = null;
		
		order = this.orderRepo.findByOrderNumber(orderNumber);
		
		logger.info("Leaving OrderServiceImpl.getOrder().  order=" + order);
		
		return order;
	}
	
	@Override
	public List<Order> getOrders() {
		
		List<Order> orders = null;
		//List<Order> orders = new ArrayList<Order>();
		
		orders = this.orderRepo.findAll();
//		OrderMenuItem omi = new OrderMenuItem("123", 123, "My Menu", 100.00);
//		List<OrderMenuItem> lOmi = new ArrayList<OrderMenuItem>();
//		lOmi.add(omi);
//		orders.add(new Order("cust1", lOmi, 300.00));
		
		return orders;
	}
	
	@Override
	public Order addOrder(OrderInput orderInput) {
		
		Order order = new Order();
		List<OrderMenuItem> orderMenuItems = new ArrayList<OrderMenuItem>();
		order.setOrderMenuItems(orderMenuItems);
		
		order.setCustomerNumber(orderInput.getCustomerNumber());
		OrderInputMenuItem orderInputMenuItem = null;
		OrderMenuItem orderMenuItem = null;

		MenuItem menuItem = null;

		double orderPrice = 0.0;
		Iterator<OrderInputMenuItem> iter = orderInput.getOrderInputMenuItems().iterator();
		while (iter.hasNext()) {
			
			orderInputMenuItem = iter.next();
			menuItem = this.menuEndpoint.getMenuItem(orderInputMenuItem.getMenuItemNumber());
	
			orderMenuItem = new OrderMenuItem();
			orderMenuItem.setMenuItemNumber(orderInputMenuItem.getMenuItemNumber());
			orderMenuItem.setMenuName(menuItem.getName());
			orderMenuItem.setQuantityOfMenuItem(orderInputMenuItem.getQuantityOfMenuItem());
			orderMenuItem.setPrice(menuItem.getPrice());
			
			orderMenuItems.add(orderMenuItem);
			
			orderPrice = orderPrice + menuItem.getPrice() * (orderInputMenuItem.getQuantityOfMenuItem());
		}

		order.setOrderPrice(orderPrice);
				
		order.setOrderNumber(orderInput.getOrderNumber());
		
		order = this.orderRepo.insert(order);
		
		return order;
	}
}
