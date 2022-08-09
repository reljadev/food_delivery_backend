package local.fdb.city;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
	public void deleteByName(String name);

}
