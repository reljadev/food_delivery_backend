package local.fdb.order;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import local.fdb.user.User;
import local.fdb.utility.AbstractPersistentObject;

@Entity
public class Order extends AbstractPersistentObject {
	
	public enum Status {
		  PROCESSING, IN_DELIVERY, DELIVERED;
	}
	
	private Status status;
	private ZonedDateTime timeOfExecution;
	private final ZoneId zone = ZoneId.of( "Europe/Belgrade" );
	
	@ManyToOne
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<OrderedMeal> orderedMeals = new HashSet<OrderedMeal>();
	
	public Order() {
		super();
	}
	
	public Order(Status status, ZonedDateTime timeOfExecution) {
		super();
		this.status = status;
		this.timeOfExecution = timeOfExecution;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public ZonedDateTime getTimeOfExecution() {
		return timeOfExecution;
	}
	
	public void recordTimeOfExecution() {
		this.timeOfExecution = ZonedDateTime.now(zone);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void orderMeal(OrderedMeal m) {
		orderedMeals.add(m);
	}

	public Set<OrderedMeal> getOrderedMeals() {
		return orderedMeals;
	}
	
	@Override
	public String toString() {
		String result = "Status: " + this.status + "\n" +
				"Execution time: " + this.timeOfExecution + "\n" +
				"User: " + this.user.getName() + "\n";
		
		for(OrderedMeal orderedMeal : this.orderedMeals) {
			result += "Meal: " + orderedMeal.getMeal().getId() + "\n";
		}
		
		return result;
	}

}
