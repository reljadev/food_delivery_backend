package local.fdb.meal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import local.fdb.restaurant.Restaurant;
import local.fdb.utility.AbstractPersistentObject;

@Entity
public class Meal extends AbstractPersistentObject {
	
	private String name;
	private float price;
	
	@ManyToOne
	//TODO: doesn't work when meal extends AbstractPersistentObject
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;
	
	public Meal() {
		super();
	}
	
	public Meal(String name, float price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
