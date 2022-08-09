package local.fdb.city;

import javax.persistence.Entity;

import local.fdb.utility.AbstractPersistentObject;

@Entity
public class City extends AbstractPersistentObject {
	
	private String name;
	
	public City() {
		super();
	}
	
	public City(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
