package com.customerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customerservice.entity.Customer;
import com.customerservice.repository.CustomerRepository;
import com.customerservice.valueobject.Orders;
import com.customerservice.valueobject.ResponseTemplateVO;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll(); 
	}

	@Override
	public Customer addCustomer(Customer customer) {
		if(customer.getCustomerId() != null && customer.getCustomerId().toString().length()>0) {
			System.out.println("Sorry, cannot add customer details. Please do not send customerId "
					+ "in POST request");
			return null;
		}
		return customerRepository.save(customer);
	}

	@Override
	public ResponseTemplateVO getCustomerWithOrders(Long customerId) {
		ResponseTemplateVO vo = new ResponseTemplateVO();
		Customer customer = customerRepository.findByCustomerId(customerId);
		List<Orders> orders = restTemplate.getForObject("http://localhost:9191/order/customer/" + customerId, List.class);
		vo.setCustomer(customer);
		vo.setOrders(orders);
		return vo;
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return customerRepository.findByCustomerId(customerId);
	}

	@Override
	public Customer updateCustomer(Long customerId, Customer customer) {
		if(customer.getCustomerId() == null || customer.getCustomerId().toString().length()==0) {
			System.out.println("Sorry, cannot update customer details. Please provide customerId "
					+ "in PUT request");
			return null;
		}
		Customer checkCustomer = customerRepository.findByCustomerId(customerId);
		if(checkCustomer == null) {
			System.out.println("No such customer exists!");
			return null;
		}
		customer.setCustomerId(checkCustomer.getCustomerId());
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	@Override
	public Customer getCustomerByEmailId(String customerEmailId) {
		return customerRepository.findByCustomerEmailId(customerEmailId);
	}

}
