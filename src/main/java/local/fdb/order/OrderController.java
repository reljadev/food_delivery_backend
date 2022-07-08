package local.fdb.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public List<ObjectNode> getOrders() {
		return orderService.getAllOrders()
						   .stream()
						   .map((order) -> {
							   return OrderJSONConverter.orderJSON(order);
						   })
						   .collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public ObjectNode getOrderById(@PathVariable String id) {
		return OrderJSONConverter.orderJSON(orderService
												.getOrderById(id)
												.orElse(null));
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public void addOrder(@RequestBody Parameters parameters) {
		orderService.addOrder(parameters);
	}
	
	@RequestMapping(value = "/users/{id}/orders", method = RequestMethod.GET)
	public List<Order> getOrdersByUserId(@PathVariable String id) {
		return orderService.getOrdersByUserId(id);
	}
	
//	@RequestMapping(value="/orders/{id}", method = RequestMethod.PUT)
//	public void updateOrder(@RequestBody Order o, @PathVariable String id) {
//		orderService.updateOrder(id, o);
//	}
	
	@RequestMapping(value="/orders/{id}", method = RequestMethod.DELETE)
	public void deleteOrderById(@PathVariable String id) {
		orderService.deleteOrderById(id);
	}

}
