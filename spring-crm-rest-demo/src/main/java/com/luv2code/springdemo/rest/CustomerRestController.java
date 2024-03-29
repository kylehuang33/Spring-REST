 package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// add mapping for Get /customers
	
	@GetMapping("/customers")
	public List<Customer> getCustomers()
	{
		return customerService.getCustomers();
	}
	
	// add mapping for Get /customers/{customerId}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId)
	{
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer == null)
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		
		return theCustomer;
	}
	
	// add mapping for POST /customers - add new customer
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer)
	{
		// also just in case the pass an id in json ... set id to 0
		// this is force to save of new item ... instead of update
		// https://www.udemy.com/spring-hibernate-tutorial/learn/lecture/10646474?start=203#bookmarks
		
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// add mapping for PUT /customers - update existing customer
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer)
	{
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// add mapping for DELETE /customers/{customerId} - delete customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId)
	{
		// throw exception if null
		if(customerService.getCustomer(customerId) == null)
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		
		customerService.deleteCustomer(customerId);
		
		return "Delete customer id - " + customerId;
	}
	
}





