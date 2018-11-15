package mydomain.model;

import javax.persistence.*;

@Entity
@Table(name = "MY_ADDRESS")
public class Address extends AbstractIdentifiable {

    @Basic
    private Long zipCode;

    @Basic
    private Long number;

    @Basic
    private String street;

    @Basic
    private String city;

    @Basic
    private String countryCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
//    @Transient
    private Person person;

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public static class Builder {
        private final Address address = new Address();

        public Builder zipCode(Long zipCode) {
            this.address.setZipCode(zipCode);
            return this;
        }

        public Builder number(Long number) {
            this.address.setNumber(number);
            return this;
        }

        public Builder street(String street) {
            this.address.setStreet(street);
            return this;
        }

        public Builder city(String city) {
            this.address.setCity(city);
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.address.setCountryCode(countryCode);
            return this;
        }

        public Builder person(Person person) {
            this.address.setPerson(person);
            return this;
        }

        public Address build() {
            return address;
        }
    }

}
