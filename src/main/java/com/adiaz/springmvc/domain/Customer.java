package com.adiaz.springmvc.domain;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;
    @Version
    private Integer version;
    private String firstName;
    private String secondName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "BILL_ADDRESS_LINE_01")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "BILL_ADDRESS_LINE_02")),
            @AttributeOverride(name = "city", column = @Column(name = "BILL_CITY")),
            @AttributeOverride(name = "state", column = @Column(name = "BILL_STATE")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "BILL_ZIP_CODE"))
    })
    private Address billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "SHIP_ADDRESS_LINE_01")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "SHIP_ADDRESS_LINE_02")),
            @AttributeOverride(name = "city", column = @Column(name = "SHIP_CITY")),
            @AttributeOverride(name = "state", column = @Column(name = "SHIP_STATE")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "SHIP_ZIP_CODE"))
    })
    private Address shipAddress;

    @OneToOne
    private User user;

    public Customer() {
        super();
    }

    public Customer(Integer id, String firstName, String secondName, String email, String phoneNumber, String addressLine01, String addressLine02, String city, String state, String zipCode) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(Address shipAddress) {
        this.shipAddress = shipAddress;
    }
}
