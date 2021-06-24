package com.ibm.order.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

import com.ibm.order.endpoint.MenuEndpoint;
import com.ibm.order.model.OrderMenuItem;
import com.ibm.order.model.Order;
import com.ibm.order.repo.OrderRepo;


class OrderServiceImplTest {
	// A mock MenuEndpoint object will be created and
	// injected so that OrderService can be unit-tested
	// in isolation.
	//@Mock 
	//private MenuEndpoint menuEndpoint;
	
	@Mock
	private OrderRepo orderRepo;
	
	@InjectMocks
	private OrderServiceImpl orderService;
	
	// Scan this class and process the Mockito annotations
    @BeforeEach
    public void init() {
       MockitoAnnotations.initMocks(this);
    }
    
    @DisplayName("Test Get Order with an existing order number.")
	@Test // Tells Junit Runner to run this
	void testGetOrderExisting() {
		// Given - set the parameter values and mock the methods for this test case
        String orderNum = "ORDER-1111";
        
        List<OrderMenuItem> menuItems = new ArrayList<OrderMenuItem>();
        menuItems.add(new OrderMenuItem("MNU-1", 2, "Bagel", 1.25));
        Order orderMocked = new Order(orderNum, menuItems, 2.50);
        when(orderRepo.findByOrderNumber(orderNum)).thenReturn(orderMocked);
        
        // When - call the method being tested and save the response
        Order order = orderService.getOrder(orderNum);
        
        // Then - check that the results are valid (and that the expected mocked methods were called)
        assertNotNull(order, "order should not be null");
        assertEquals(order, orderMocked, "bookData should be the same as: " + orderMocked);
     
        verify(orderRepo).findByOrderNumber(orderNum);

	}

}
