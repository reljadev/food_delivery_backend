package local.fdb.order;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import local.fdb.meal.Meal;
import local.fdb.utility.AbstractPersistentObject;

@Entity
public class OrderedMeal extends AbstractPersistentObject {
	
	@JsonIgnore
	@NotNull
	@ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
	
    private int quantity;
    
	public OrderedMeal() {
		super();
	}

	public OrderedMeal(Order order, Meal meal, int quantity) {
		super();
		this.order = order;
		this.meal = meal;
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof OrderedMeal)) {
			return false;
		}

		OrderedMeal other = (OrderedMeal) o;

		// equivalence by foreign keys
		return this.meal.getId().equals(other.getMeal().getId()) &&
				this.order.getId().equals(other.getOrder().getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.meal.getId(),
							this.order.getId());
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
}
