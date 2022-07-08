package local.fdb.order;

import java.util.Map;

public class Parameters {
	
	private String restaurantId;
	private String userId;
	private Map<String, Integer> meals;
	
	public String getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Map<String, Integer> getMeals() {
		return meals;
	}
	
	public void setMeals(Map<String, Integer> meals) {
		this.meals = meals;
	}

}
