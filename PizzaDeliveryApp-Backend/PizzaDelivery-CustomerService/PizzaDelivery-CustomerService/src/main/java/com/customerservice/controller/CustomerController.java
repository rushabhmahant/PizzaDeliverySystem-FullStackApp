package com.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerservice.entity.Customer;
import com.customerservice.service.CustomerService;
import com.customerservice.valueobject.ResponseTemplateVO;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/getCustomers")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/getCustomer/{customerId}")
	public Customer getCustomerById(@PathVariable Long customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	@GetMapping("/getCustomerWithOrders/{customerId}")
	public ResponseTemplateVO getByCustomerWithOrders(@PathVariable Long customerId) {
		return customerService.getCustomerWithOrders(customerId);
	}
	
	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestBody Customer customer) {
		return customerService.addCustomer(customer);
	}
	
	@PutMapping("/updateCustomer/{customerId}")
	public Customer updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
		return customerService.updateCustomer(customerId, customer);
	}
	
	@DeleteMapping("/deleteCustomer/{customerId}")
	public void deleteCustomer(@PathVariable Long customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Customer customer){
		Customer loggingCustomer = customerService.getCustomerByEmailId(customer.getCustomerEmailId());
		if(loggingCustomer == null) {
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
		}
		if(!loggingCustomer.getCustomerPassword()
				.equals(customer.getCustomerPassword())) {
			return new ResponseEntity("Customer Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Customer>(loggingCustomer, HttpStatus.OK);
		
	}

}
