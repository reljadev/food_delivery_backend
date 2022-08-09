package local.fdb.meal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, String> {
	public List<Meal> findByRestaurantId(String id);

}
