package main.java.models.Storage;

public abstract class ContainerEntity {
    protected Integer id;

    public ContainerEntity(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && this.getClass() == object.getClass()) {
            ContainerEntity typedObject = (ContainerEntity) object;
            if (this.getId() == null || typedObject.getId() == null) {
                return false;
            }
            return this.getId().equals(typedObject.getId());
        }
        return false;
    }
}
