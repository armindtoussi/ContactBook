package ca.bc.northvan.armintoussi.contactbook.Models;

import android.media.Image;
import android.net.Uri;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Contact {

    private Uri     image;
    private Person  person;
    private Address address;
    private String  email;
    private String  homePhoneNumber;
    private String  mobilePhoneNumber;


    private Contact(Builder builder) {
        this.image = builder.image;
        this.person = builder.person;
        this.address = builder.address;
        this.email = builder.email;
        this.homePhoneNumber = builder.homePhoneNumber;
        this.mobilePhoneNumber = builder.mobilePhoneNumber;
    }

    public static class Builder {
        private Uri     image;
        private Person  person;
        private Address address;
        private String  email;
        private String  homePhoneNumber;
        private String  mobilePhoneNumber;

        public Builder image(final Uri image) {
            this.image = image;
            return this;
        }

        public Builder person(final Person person) {
            this.person = person;
            return this;
        }

        public Builder address(final Address address) {
            this.address = address;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder homePhoneNumber(final String homePhoneNumber) {
            this.homePhoneNumber = homePhoneNumber;
            return this;
        }

        public Builder mobilePhoneNumber(final String mobilePhoneNumber) {
            this.mobilePhoneNumber = mobilePhoneNumber;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    public void setMobilePhoneNumber(final String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setHomePhoneNumber(final String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public void setAddress(final Address address) {
        this.address = address;

    }

    public void setPerson(final Person person) {
        this.person = person;

    }

    public void setImage(final Uri image) {
        this.image = image;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Uri getImage() {
        return image;
    }

    public Person getPerson() {
        return (Person) this.person.clone();
    }

    public Address getAddress() {
        return (Address) this.address.clone();
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }
}
