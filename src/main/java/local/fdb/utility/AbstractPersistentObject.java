package local.fdb.utility;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

//TODO: could I somehow only use the logic of this class without making it the entity?
@Entity
public abstract class AbstractPersistentObject implements PersistentObject {

	@Id
	private String id = IdGenerator.createId();
	@Version
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof PersistentObject)) {

			return false;
		}

		PersistentObject other = (PersistentObject) o;

		// if the id is missing, return false
		if (id == null)
			return false;

		// equivalence by id
		return id.equals(other.getId());
	}

	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]";
	}
}