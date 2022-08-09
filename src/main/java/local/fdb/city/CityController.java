package local.fdb.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

	@Autowired
	CityService cityService;
	
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public List<City> getCities() {
		return cityService.getAllCities();
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/cities", method = RequestMethod.POST)
	public void addCity(@RequestBody City c) {
		cityService.addCity(c);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@RequestMapping(value="/cities/{id}", method = RequestMethod.DELETE)
	public void deleteCityById(@PathVariable String id) {
		cityService.deleteCityById(id);
	}
	
}
