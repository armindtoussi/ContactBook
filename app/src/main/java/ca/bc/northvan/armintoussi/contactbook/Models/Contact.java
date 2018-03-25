package ca.bc.northvan.armintoussi.contactbook.Models;

import android.media.Image;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Contact {
    private static final int MIN_AREA_CODE = 99;
    private static final int MAX_AREA_CODE = 1000;
    private static final int MIN_PHONE_NO  = 999_999;
    private static final int MAX_PHONE_NO  = 10_000_000;

    private Image   image;
    private Person  person;
    private Address address;
    private String  organization;
    private String  relationship;
    private int     homeAreaCode;
    private int     homePhoneNumber;
    private int     mobileAreaCode;
    private int     mobilePhoneNumber;


    public Contact(final Person person, final int mobileAreaCode, final int mobilePhoneNumber) {
        this.setPerson(person);
        this.setMobileAreaCode(mobileAreaCode);
        this.setMobilePhoneNumber(mobilePhoneNumber);
    }

    public void setMobilePhoneNumber(final int mobilePhoneNumber) {
        if(mobilePhoneNumber > MIN_PHONE_NO && mobilePhoneNumber < MAX_PHONE_NO) {
            this.mobilePhoneNumber = mobilePhoneNumber;
        }
    }

    public void setHomePhoneNumber(final int homePhoneNumber) {
        if(homePhoneNumber > MIN_PHONE_NO && homePhoneNumber < MAX_PHONE_NO) {
            this.homePhoneNumber = homePhoneNumber;
        }
    }

    public void setMobileAreaCode(final int mobileAreaCode) {
        if(mobileAreaCode > MIN_AREA_CODE && mobileAreaCode < MAX_AREA_CODE) {
            this.mobileAreaCode = mobileAreaCode;
        }
    }

    public void setHomeAreaCode(final int homeAreaCode) {
        if(homeAreaCode > MIN_AREA_CODE && homeAreaCode < MAX_AREA_CODE) {
            this.homeAreaCode = homeAreaCode;
        }
    }

    public void setRelationship(final String relationship) {
        if(relationship != null) {
            this.relationship = relationship;
        }
    }

    public void setOrganization(final String organization) {
        if(organization != null) {
            this.organization = organization;
        }
    }

    public void setAddress(final Address address) {
        if(address != null) {
            this.address = address;
        }
    }

    public void setPerson(final Person person) {
        if(person != null) {
            this.person = person;
        }
    }

    public void setImage(final Image image) {
        if(image != null) {
            this.image = image;
        }
    }

    public Image getImage() {
        return image;
    }

    public Person getPerson() {
        return (Person) this.person.clone();
    }

    public Address getAddress() {
        return (Address) this.address.clone();
    }

    public String getOrganization() {
        return organization;
    }

    public String getRelationship() {
        return relationship;
    }

    public int getHomeAreaCode() {
        return homeAreaCode;
    }

    public int getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public int getMobileAreaCode() {
        return mobileAreaCode;
    }

    public int getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }
}
