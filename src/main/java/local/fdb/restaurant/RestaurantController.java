package local.fdb.restaurant;

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
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;
	
	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	public List<Restaurant> getRestaurants() {
		return restaurantService.getAllRestaurants();
	}
	
	@RequestMapping(value = "/restaurants/{id}", method = RequestMethod.GET)
	public Optional<Restaurant> getRestaurantById(@PathVariable String id) {
		return restaurantService.getRestaurantById(id);
	}
	
	@RequestMapping(value="/cities/{cityId}/restaurants", method = RequestMethod.GET)
	public List<Restaurant> getRestaurantsByCityId(@PathVariable String cityId) {
		return restaurantService.getRestaurantsByCityId(cityId);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/cities/{cityId}/restaurants", method = RequestMethod.POST)
	public void addRestaurant(@PathVariable String cityId, @RequestBody Restaurant r) {
		restaurantService.addRestaurant(r, cityId);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/restaurants/{id}", method = RequestMethod.PUT)
	public void updateRestaurant(@RequestBody Restaurant r, @PathVariable String id) {
		restaurantService.updateRestaurant(r, id);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/restaurants/{id}", method = RequestMethod.DELETE)
	public void deleteRestaurantById(@PathVariable String id) {
		restaurantService.deleteRestaurantById(id);
	}
	
}
