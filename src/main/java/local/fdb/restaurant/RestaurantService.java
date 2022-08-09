package local.fdb.restaurant;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import local.fdb.city.CityRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	CityRepository cityRepository;

	public RestaurantService() {
		super();
	}
	
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
	
	public Optional<Restaurant> getRestaurantById(String id) {
		return restaurantRepository.findById(id);
	}
	
	public List<Restaurant> getRestaurantsByCityId(String cityId) {
		return restaurantRepository.findByCityId(cityId);
	}
	
	public void addRestaurant(Restaurant r, String cityId) {
		r.setCity(cityRepository.findById(cityId)
								.orElseThrow(() -> { 
									throw new ResponseStatusException(
										  HttpStatus.NOT_FOUND,
										  "city not found");
								})
				);
		
		restaurantRepository.save(r);
	} 
	
	public void updateRestaurant(Restaurant r, String id) {
		Restaurant rSaved = this.getRestaurantById(id)
							.orElseThrow(() -> { 
								throw new ResponseStatusException(
									  HttpStatus.NOT_FOUND,
									  "restaurant not found");
							});
		rSaved.setName(r.getName());
		rSaved.setPhotoPath(r.getPhotoPath());
		
		restaurantRepository.save(rSaved);
	}
	
	public void deleteRestaurantById(String id) {
		restaurantRepository.deleteById(id);
	}

}
