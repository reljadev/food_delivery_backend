package local.fdb.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import local.fdb.meal.Meal;
import local.fdb.restaurant.Restaurant;

public class OrderJSONConverter {
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static ObjectNode orderJSON(Order order) {
		ObjectNode orderJSON = mapper.createObjectNode();
		
		if(order == null) {
			return orderJSON;
		}
		
		// add order info
        populateBasicOrderInfo(orderJSON, order);
        
        // group meals by restaurant
        Map<Restaurant, List<OrderedMeal>> mealsByRestaurant = 
        								getMealsByRestaurant(order);
        
        // add restaurants
        ArrayNode restaurantsJSON = mapper.createArrayNode();
        for(Entry<Restaurant, List<OrderedMeal>> e: mealsByRestaurant.entrySet()) {
        	// add corresponding meals to each restaurant
        	restaurantsJSON.add(restaurantJSON(e.getKey(), e.getValue()));
        }
        orderJSON.set("restaurants", restaurantsJSON);

        return orderJSON;
    }
	
	public static void populateBasicOrderInfo(ObjectNode orderJSON, Order order) {
		orderJSON.put("id", order.getId());
        orderJSON.put("status", order.getStatus().name());
        orderJSON.put("timeOfExecution", 
        			  order.getTimeOfExecution().toString());
        orderJSON.set("user",
				  mapper.valueToTree(order.getUser()));
	} 
	
	public static Map<Restaurant, List<OrderedMeal>> getMealsByRestaurant(Order order) {
		Map<Restaurant, List<OrderedMeal>> mealsByRestaurant = new HashMap<Restaurant, List<OrderedMeal>>();
		
        order.getOrderedMeals()
        	 .forEach((orderedMeal) -> {
        		 Restaurant restaurant = orderedMeal.getMeal().getRestaurant();
        		 
        		 if(!mealsByRestaurant.containsKey(restaurant)) {
        			 mealsByRestaurant.put(restaurant, new ArrayList<OrderedMeal>());
        		 }
        		 mealsByRestaurant.get(restaurant).add(orderedMeal);
        	 });
        
        return mealsByRestaurant;
	}
	
	public static ObjectNode restaurantJSON(Restaurant restaurant, List<OrderedMeal> ordMeals) {
		ObjectNode restaurantJSON = mapper.createObjectNode();
    	restaurantJSON.put("id", restaurant.getId());
    	restaurantJSON.put("name", restaurant.getName());
    	
    	ArrayNode mealsJSON = mapper.createArrayNode();
    	ordMeals.forEach((orderedMeal) -> {
    		mealsJSON.add(orderedMealJSON(orderedMeal));
    	});
    	restaurantJSON.set("meals", mealsJSON);
    	
    	return restaurantJSON;
	}
	
	private static ObjectNode orderedMealJSON(OrderedMeal orderedMeal) {
		ObjectNode mealJSON = mapper.createObjectNode();
		Meal meal = orderedMeal.getMeal();
		 
		mealJSON.put("id", meal.getId());
		mealJSON.put("name", meal.getName());
		mealJSON.put("price", meal.getPrice());
		mealJSON.put("quantity", orderedMeal.getQuantity());
		 
		return mealJSON;
	}

}
