package local.fdb.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import local.fdb.meal.Meal;

public class OrderJSONConverter {
	
	public static ObjectNode orderJSON(Order order) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode orderJSON = mapper.createObjectNode();
		ArrayNode mealsJSON = mapper.createArrayNode();
		
		if(order == null) {
			return orderJSON;
		}
		
        orderJSON.put("id", order.getId());
        orderJSON.put("status", order.getStatus().name());
        orderJSON.put("timeOfExecution", 
        			  order.getTimeOfExecution().toString());
        orderJSON.set("user",
				  mapper.valueToTree(order.getUser()));
        orderJSON.set("restaurant",
			      mapper.valueToTree(order.getRestaurant()));
        
        order.getOrderedMeals()
        	 .forEach((orderedMeal) -> {
        		 mealsJSON.add(orderedMealJSON(mapper, orderedMeal));
        	 });
        
        orderJSON.set("meals", mealsJSON);

        return orderJSON;
    }
	
	private static ObjectNode orderedMealJSON(ObjectMapper mapper, OrderedMeal orderedMeal) {
		ObjectNode mealJSON = mapper.createObjectNode();
		 Meal meal = orderedMeal.getMeal();
		 
		 mealJSON.put("id", meal.getId());
		 mealJSON.put("name", meal.getName());
		 mealJSON.put("price", meal.getPrice());
		 mealJSON.put("quantity", orderedMeal.getQuantity());
		 
		 return mealJSON;
	}

}
