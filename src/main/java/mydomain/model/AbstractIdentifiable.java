package mydomain.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractIdentifiable extends AbstractBaseJpaEntity {

    @Basic
    @Column(name = "internal_identifier", length = 36)
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractIdentifiable that = (AbstractIdentifiable) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "AbstractIdentifiable{" +
                "uuid='" + uuid + '\'' +
                "} " + super.toString();
    }
}
