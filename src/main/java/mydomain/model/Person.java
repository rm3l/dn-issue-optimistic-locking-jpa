package mydomain.model;

import javax.persistence.*;

@Entity
@Table(name = "MY_PERSON")
public class Person extends AbstractIdentifiable
{
    @Basic
    @Access(AccessType.PROPERTY)
    private String uniqueName;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address lastAddress;

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public Address getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(Address lastAddress) {
        this.lastAddress = lastAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uniqueName='" + uniqueName + '\'' +
                "} " + super.toString();
    }

    public static class Builder {
        private final Person person = new Person();

        public Builder uniqueName(String uniqueName) {
            person.setUniqueName(uniqueName);
            return this;
        }

        public Builder lastAddress(Address address) {
            person.setLastAddress(address);
            return this;
        }

        public Builder uuid(String uuid) {
            person.setUuid(uuid);
            return this;
        }

        public Person build() {
            return this.person;
        }

    }
}
