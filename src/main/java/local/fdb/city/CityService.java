package local.fdb.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;

	public CityService() {
		super();
	}
	
	public List<City> getAllCities() {
		return cityRepository.findAll();
	}
	
	public void addCity(City c) {
		cityRepository.save(c);
	} 
	
	public void deleteCityById(String id) {
		cityRepository.deleteById(id);
	}

}
