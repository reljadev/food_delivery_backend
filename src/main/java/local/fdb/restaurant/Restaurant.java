package local.fdb.restaurant;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import local.fdb.city.City;
import local.fdb.utility.AbstractPersistentObject;

@Entity
public class Restaurant extends AbstractPersistentObject {
	
	private String name;
	private String photoPath;
	
	@ManyToOne
	private City city;
	
	public Restaurant() {
		super();
	}

	public Restaurant(String name, String photoPath) {
		this.name = name;
		this.photoPath = photoPath;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
