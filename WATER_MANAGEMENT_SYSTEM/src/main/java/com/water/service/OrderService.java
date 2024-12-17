package com.water.service;

import java.time.LocalDate;
import java.util.List;

import com.water.model.Order;

public interface OrderService {

	public void saveOrder(Order order);

	public List<Order> getAllOrders();

	public List<Order> getFilteredOrders(Long customerId, String month);

	public List<Order> findOrdersByVehicleAndDateRange(Long vehicleId, LocalDate startDate, LocalDate endDate);

	public List<Order> getMonthlyOrdersForCustomer(Long customerId, int month, int year);

	public Long calculateTotalJarsForMonth(List<Order> orders);

	 public List<Order> getOrdersForMonth(Long customerId, int month, int year);

	public List<Order> findOrdersByMonthAndYear(int month, int year);

	public void deleteOrderById(long id);

}
