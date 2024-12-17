package com.water.serviceimpl;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.water.model.Order;
import com.water.repository.OrderRepository;
import com.water.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public void saveOrder(Order order) {

		orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	public List<Order> getOrdersForCustomerByMonth(Long customerId, int year, int month) {
		return orderRepository.findByCustomerIdAndOrderDateBetween(customerId, LocalDate.of(year, month, 1),
				LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()));
	}

	@Override
	public List<Order> getFilteredOrders(Long customerId, String month) {
		if (customerId != null && month != null && !month.isEmpty()) {
			return orderRepository.findByCustomerIdAndMonth(customerId, month);
		} else if (customerId != null) {
			return orderRepository.findByCustomerId(customerId);
		} else if (month != null && !month.isEmpty()) {
			return orderRepository.findByMonth(month);
		} else {
			return orderRepository.findAll();
		}
	}

	@Override
	public List<Order> findOrdersByVehicleAndDateRange(Long vehicleId, LocalDate startDate, LocalDate endDate) {
		return orderRepository.findOrdersByVehicleAndDateRange(vehicleId, startDate, endDate);
	}

	@Override
	public List<Order> getMonthlyOrdersForCustomer(Long customerId, int month, int year) {
		return orderRepository.findByCustomerAndMonth(customerId, month, year);
	}

	@Override
	public Long calculateTotalJarsForMonth(List<Order> orders) {
		return orders.stream().mapToLong(Order::getJarQuantity).sum();
	}

	@Override
	public List<Order> getOrdersForMonth(Long customerId, int month, int year) {

		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		return orderRepository.findByCustomerIdAndOrderDateBetween(customerId, startDate, endDate);
	}

	@Override
	public List<Order> findOrdersByMonthAndYear(int month, int year) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		return orderRepository.findByOrderDateBetween(startDate, endDate);
	}

	@Override
	public void deleteOrderById(long id) {

		orderRepository.deleteById(id);
	}
}
