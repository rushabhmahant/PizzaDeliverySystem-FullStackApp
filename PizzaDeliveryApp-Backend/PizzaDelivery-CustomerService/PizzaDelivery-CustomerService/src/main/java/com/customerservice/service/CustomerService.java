package com.customerservice.service;

import java.util.List;

import com.customerservice.entity.Customer;
import com.customerservice.valueobject.ResponseTemplateVO;

public interface CustomerService {
	
	public List<Customer> getAllCustomers();
	
	public Customer addCustomer(Customer customer);
	
	public ResponseTemplateVO getCustomerWithOrders(Long customerId);

	public Customer getCustomerById(Long customerId);

	public Customer updateCustomer(Long customerId, Customer customer);

	public void deleteCustomer(Long customerId);

	public Customer getCustomerByEmailId(String customerEmailId);

}
