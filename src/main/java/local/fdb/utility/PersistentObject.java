package local.fdb.utility;

public interface PersistentObject {
    public String getId();
    public void setId(String id);

    public Integer getVersion();
    public void setVersion(Integer version);
}

