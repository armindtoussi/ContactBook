package ca.bc.northvan.armintoussi.contactbook.Models;

import android.database.Cursor;
import android.media.Image;
import android.net.Uri;

import ca.bc.northvan.armintoussi.contactbook.Activities.HomeActivity;
import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

/**
 * Created by armin2 on 3/25/2018.
 *
 * This class represents a contact.
 * Builder pattern is implemented to build
 * a contact as well as ContactBuilder class
 * for ease of repeatedly building a contact.
 */
public class Contact {
    /** The contact _id. */
    private long _id;
    /** The Person _id. */
    private long person_id;
    /** The Address _id. */
    private long address_id;
    /** Uri for the image. */
    private Uri     image;
    /** Person obj that holds contact name. */
    private Person  person;
    /** Address obj that holds the contact address. */
    private Address address;
    /** String that holds contact email. */
    private String  email;
    /** String that holds contact home phone. */
    private String  homePhoneNumber;
    /** String that holds contact mobile phone. */
    private String  mobilePhoneNumber;

    /**
     * Private ctor that sets all the instance variables.
     *
     * @param builder builder obj used for building this obj.
     */
    private Contact(Builder builder) {
        this._id        = builder._id;
        this.person_id  = builder.person_id;
        this.address_id = builder.address_id;
        this.image      = builder.image;
        this.person     = builder.person;
        this.address    = builder.address;
        this.email      = builder.email;
        this.homePhoneNumber   = builder.homePhoneNumber;
        this.mobilePhoneNumber = builder.mobilePhoneNumber;
    }

    /**
     * Static Builder class for easily building
     * a contact with variable arguments.
     */
    public static class Builder {
        /** The contact _id. */
        private long _id;
        /** The Person _id. */
        private long person_id;
        /** The Address _id. */
        private long address_id;
        /** Uri for the image. */
        private Uri     image;
        /** Person obj that holds contact name. */
        private Person  person;
        /** Address obj that holds the contact address. */
        private Address address;
        /** String that holds contact email. */
        private String  email;
        /** String that holds contact home phone. */
        private String  homePhoneNumber;
        /** String that holds contact mobile phone. */
        private String  mobilePhoneNumber;

        /**
         * Add the _id.
         *
         * @param _id the _id to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder _id(final long _id) {
            this._id = _id;
            return this;
        }

        /**
         * Add the person _id.
         *
         * @param person_id the person_id to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder person_id(final long person_id) {
            this.person_id = person_id;
            return this;
        }

        /**
         * Add the address_id.
         *
         * @param address_id the address_id to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder address_id(final long address_id) {
            this.address_id = address_id;
            return this;
        }

        /**
         * Add the image.
         *
         * @param image the image uri to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder image(final Uri image) {
            this.image = image;
            return this;
        }

        /**
         * Add the person obj.
         *
         * @param person the person to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder person(final Person person) {
            this.person = person;
            return this;
        }

        /**
         * Add the Address obj.
         *
         * @param address the address to add to build.
         *
         * @return  this obj to continue building.
         */
        public Builder address(final Address address) {
            this.address = address;
            return this;
        }

        /**
         * Add the email.
         *
         * @param email the email to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Add the home phone.
         *
         * @param homePhoneNumber the home phone to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder homePhoneNumber(final String homePhoneNumber) {
            this.homePhoneNumber = homePhoneNumber;
            return this;
        }

        /**
         * Add the mobile phone.
         *
         * @param mobilePhoneNumber the mobile phone to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder mobilePhoneNumber(final String mobilePhoneNumber) {
            this.mobilePhoneNumber = mobilePhoneNumber;
            return this;
        }

        /**
         * Build method to finish the build and return a
         * built Contact obj.
         *
         * @return built Contact obj.
         */
        public Contact build() {
            return new Contact(this);
        }
    }


    /**
     * Sets the mobile phone no.
     *
     * @param mobilePhoneNumber mobile phone no as a string.
     */
    public void setMobilePhoneNumber(final String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    /**
     * Sets the home phone no.
     *
     * @param homePhoneNumber home phone no as a string.
     */
    public void setHomePhoneNumber(final String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    /**
     * Sets the Address obj.
     *
     * @param address the address to set as Address obj.
     */
    public void setAddress(final Address address) {
        this.address = address;

    }

    /**
     * Sets the Person obj.
     *
     * @param person the person to set as Person obj.
     */
    public void setPerson(final Person person) {
        this.person = person;

    }

    /**
     * Sets the image.
     *
     * @param image the image to set as a Uri.
     */
    public void setImage(final Uri image) {
        this.image = image;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set as a String.
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the _id.
     *
     * @return  _id as a long.
     */
    public long get_id() {
        return _id;
    }

    /**
     * Gets the person_id.
     *
     * @return person_id as a long.
     */
    public long getPerson_id() {
        return person_id;
    }

    /**
     * Gets the address_id.
     *
     * @return address_id as a long.
     */
    public long getAddress_id() {
        return address_id;
    }

    /**
     * Gets the email.
     *
     * @return email as a String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the Image.
     *
     * @return image as a Uri.
     */
    public Uri getImage() {
        return image;
    }

    /**
     * Gets the person.
     *
     * @return a clone of the person as a Person obj.
     */
    public Person getPerson() {
        return (Person) this.person.clone();
    }

    /**
     * Gets the address.
     *
     * @return a clone of the address as an Address obj.
     */
    public Address getAddress() {
        return (Address) this.address.clone();
    }

    /**
     * Gets the home phone.
     *
     * @return the home phone as a String.
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * Gets the mobile phone.
     *
     * @return the mobile phone as a String.
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public static Contact fromCursor(Cursor cursor) {
        final long   _id   = cursor.getLong(0);
        final String fName = cursor.getString(HomeActivity.F_NAME_COL);
        final String lName = cursor.getString(HomeActivity.L_NAME_COL);
        final String num   = cursor.getString(HomeActivity.MOBILE_COL);

        return ContactBuilder.createContact(_id, -1, -1, null,
                Person.PersonBuilder.createPerson(fName, lName, ""),
                "", "", num);
    }

    /**
     * Contact builder class, gives us a ability to statically call
     * method for a Contact to be built.
     */
    public static final class ContactBuilder {

        /**
         * Creates a contact using the Contact Builder.
         *
         * @param address contact's street address.
         * @param person  contact's name.
         * @param email   contact's email.
         * @param homePhone contact's home phone #.
         * @param mobilePhone contact's mobile phone #.
         *
         * @return a built Contact.
         */
        public static Contact createContact(final long    _id,
                                            final long person_id,
                                            final long address_id,
                                            final Address address,
                                            final Person person,
                                            final String email,
                                            final String homePhone,
                                            final String mobilePhone) {
            Contact.Builder cb = new Contact.Builder();

            if (_id > -1) {
                cb._id(_id);
            }
            if(person_id > -1) {
                cb.person_id(person_id);
            }
            if(address_id > -1) {
                cb.address_id(address_id);
            }
            if (address != null) {
                cb.address(address);
            }
            if (person != null) {
                cb.person(person);
            }
            if (Utilities.checkNotNullNotEmpty(email)) {
                cb.email(email);
            }
            if(Utilities.checkNotNullNotEmpty(homePhone)) {
                cb.homePhoneNumber(homePhone);
            }
            if(Utilities.checkNotNullNotEmpty(mobilePhone)) {
                cb.mobilePhoneNumber(mobilePhone);
            }

            return cb.build();
        }
    }
}
