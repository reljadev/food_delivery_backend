package local.fdb.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import local.fdb.meal.Meal;
import local.fdb.meal.MealRepository;
import local.fdb.order.Order.Status;
import local.fdb.restaurant.RestaurantRepository;
import local.fdb.user.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MealRepository mealRepository;

	public OrderService() {
		super();
	}
	
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
	
	public Optional<Order> getOrderById(String id) {
		return orderRepository.findById(id);
	}
	
	public void addOrder(String userId, Map<String, Integer> meals) {
		Order o = new Order();
		o.setStatus(Status.DELIVERED);
		o.recordTimeOfExecution();
		o.setUser(userRepository
						.findById(userId)
						.orElseThrow(() -> {
							throw new ResponseStatusException(
									  HttpStatus.NOT_FOUND, "user not found");
						})
				);
		meals.entrySet()
			 .forEach((entry) -> {
				 addMealToOrder(o, entry.getKey(), entry.getValue());
			 });
		
		orderRepository.save(o);
	}
	
	private void addMealToOrder(Order order, String mealId, Integer mealQuantity) {
		// get meal
		Meal meal = mealRepository
					.findById(mealId)
					.orElseThrow(() -> {
						throw new ResponseStatusException(
								  HttpStatus.NOT_FOUND, "meal not found");
					});
		if(mealQuantity == null) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, 
					  "quantity of meal " + meal.getId() + " is not properly specified");
		}
		
		// order meal
		OrderedMeal om = new OrderedMeal(order, meal, mealQuantity);
		order.orderMeal(om);
	}
	
	public void deleteOrderById(String id) {
		orderRepository.deleteById(id);
	}

	public List<Order> getOrdersByUserId(String id) {
		return orderRepository.findByUserId(id);
	}

}
