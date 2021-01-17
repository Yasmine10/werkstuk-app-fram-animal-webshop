package be.ehb.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Street is required")
    private String street;
    @NotNull(message = "House number is required")
    private String houseNr;
    private String addition;
    @NotNull(message = "Zipcode is required")
    private String zipcode;
    @NotNull(message = "City is required")
    private String city;
    @NotNull(message = "Country is required")
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<Order> orders;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return street.equals(address.street) &&
                houseNr.equals(address.houseNr) &&
                Objects.equals(addition, address.addition) &&
                zipcode.equals(address.zipcode) &&
                city.equals(address.city) &&
                country.equals(address.country) &&
                user.getId().equals(address.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNr, addition, zipcode, city, country, user.getId());
    }
}
