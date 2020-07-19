package main.java.models.General;

public abstract class Entity {
    protected Integer id;

    public Entity() {
    }

    public Entity(int id) {
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
            Entity typedObject = (Entity) object;
            if (this.getId() == null || typedObject.getId() == null) {
                return false;
            }
            return this.getId().equals(typedObject.getId());
        }
        return false;
    }
}
