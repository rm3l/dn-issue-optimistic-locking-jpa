package mydomain.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractBaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "internal_version", columnDefinition = "BIGINT NOT NULL DEFAULT 0", nullable = false)
    private long version = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AbstractBaseJpaEntity{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }
}
