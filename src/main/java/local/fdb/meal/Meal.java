package local.fdb.meal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import local.fdb.restaurant.Restaurant;
import local.fdb.utility.AbstractPersistentObject;

@Entity
public class Meal extends AbstractPersistentObject {
	
	private String name;
	private String description;
	private String type;
	private String coverPhotoPath;
	private float price;
	
	@ManyToOne
	//TODO: doesn't work when meal extends AbstractPersistentObject
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;
	
	public Meal() {
		super();
	}
	
	public Meal(String name, String description, String type, String coverPhotoPath, float price) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.coverPhotoPath = coverPhotoPath;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCoverPhotoPath() {
		return coverPhotoPath;
	}

	public void setCoverPhotoPath(String coverPhotoPath) {
		this.coverPhotoPath = coverPhotoPath;
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
