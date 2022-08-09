package local.fdb.order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
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
	public ObjectNode getOrderById(@PathVariable String id, Authentication auth) {
		Order order = orderService
						.getOrderById(id)
						.orElse(null);
		
		if(order == null) {
			return null;
		}
		
		boolean isAdmin = auth.getAuthorities().
							   stream().
							   anyMatch(grantedAuth -> grantedAuth.toString()
									 					.equals("SCOPE_admin"));
		boolean isOrderOwner = auth.getName().equals(order.getUser().getId());
		
		if(isAdmin || isOrderOwner) {
			return OrderJSONConverter.orderJSON(order);
		}
		
		return null;
	}
	
//	@PreAuthorize("#parameters.getUserId() == authentication.name")
//	@PreAuthorize("hasAuthority('SCOPE_user')")
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public void addOrder(@RequestBody Map<String, Integer> meals, Authentication auth) {
		orderService.addOrder(auth.getName(), meals);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin') or #id == authentication.name")
	@RequestMapping(value = "/users/{id}/orders", method = RequestMethod.GET)
	public List<ObjectNode> getOrdersByUserId(@PathVariable String id) {
		return orderService.getOrdersByUserId(id)
							.stream()
							.map((order) -> {
								   return OrderJSONConverter.orderJSON(order);
							 })
							.collect(Collectors.toList());
							
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/orders/{id}", method = RequestMethod.DELETE)
	public void deleteOrderById(@PathVariable String id) {
		orderService.deleteOrderById(id);
	}

}
