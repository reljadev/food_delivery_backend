package local.fdb.meal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MealController {
	
	@Autowired
	MealService mealService;
	
	@RequestMapping(value = "/meals", method = RequestMethod.GET)
	public List<Meal> getMeals() {
		return mealService.getAllMeals();
	}
	
	@RequestMapping(value = "/restaurants/{id}/meals", method = RequestMethod.GET)
	public List<Meal> getMealsByRestaurantId(@PathVariable String id) {
		return mealService.getMealsByRestaurantId(id);
	}
	
	@RequestMapping(value = "/meals/{id}", method = RequestMethod.GET)
	public Optional<Meal> getMealById(@PathVariable String id) {
		return mealService.getMealById(id);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/restaurants/{id}/meals", method = RequestMethod.POST)
	public void addMeal(@PathVariable String id, @RequestBody Meal m) {
		mealService.addMeal(m, id);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/meals/{id}", method = RequestMethod.PUT)
	public void updateMeal(@RequestBody Meal m, @PathVariable String id) {
		mealService.updateMeal(id, m);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/meals/{id}", method = RequestMethod.DELETE)
	public void deleteMealById(@PathVariable String id) {
		mealService.deleteMealById(id);
	}

}
