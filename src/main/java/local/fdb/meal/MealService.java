package local.fdb.meal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import local.fdb.restaurant.RestaurantRepository;

@Service
public class MealService {
	
	@Autowired
	MealRepository mealRepository;
	@Autowired
	RestaurantRepository restaurantRepository;

	public MealService() {
		super();
	}
	
	public List<Meal> getAllMeals() {
		return mealRepository.findAll();
	}
	
	public List<Meal> getMealsByRestaurantId(String id) {
		return mealRepository.findByRestaurantId(id);
	} 
	
	public Optional<Meal> getMealById(String id) {
		return mealRepository.findById(id);
	}
	
	public void addMeal(Meal m, String restaurant_id) {
		m.setRestaurant(restaurantRepository
							.findById(restaurant_id)
							.orElseThrow(() -> {
								throw new ResponseStatusException(
									  HttpStatus.NOT_FOUND, "restaurant not found");
								})
						);
		mealRepository.save(m);
	} 
	
	public void updateMeal(String id, Meal m) {
		Meal mSaved = this.getMealById(id).orElseThrow(() -> {
								throw new ResponseStatusException(
									  HttpStatus.NOT_FOUND, "meal not found");
								});
			
		mSaved.setName(m.getName());
		mSaved.setDescription(m.getDescription());
		mSaved.setType(m.getType());
		mSaved.setCoverPhotoPath(m.getCoverPhotoPath());
		mSaved.setPrice(m.getPrice());
		mealRepository.save(mSaved);
		
	}
	
	public void deleteMealById(String id) {
		mealRepository.deleteById(id);
	}

}
