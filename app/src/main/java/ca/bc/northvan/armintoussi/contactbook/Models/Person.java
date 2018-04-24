package ca.bc.northvan.armintoussi.contactbook.Models;

import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Person {

    private int    personID;
    private String firstName;
    private String middleName;
    private String lastName;


    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String middleName;

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder middleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(final String firstName) {
        if(firstName != null && firstName.length() > 1) {
            this.firstName = firstName;
        }
    }

    public void setMiddleName(final String middleName) {
        if(middleName != null && middleName.length() > 0) {
            this.middleName = middleName;
        }
    }

    public void setLastName(final String lastName) {
        if(lastName != null && lastName.length() > 1) {
            this.lastName = lastName;
        }
    }

    @Override
    public String toString() {
        String fullName;
        if(middleName != null) {
            fullName = this.firstName + " " + this.middleName + " " + this.lastName;
        } else {
            fullName = this.firstName + " " + this.lastName;
        }
        return fullName;
    }

    public Object clone() {
        Person aClone;
        if(this.middleName != null) {
            aClone = new Builder().firstName(firstName).lastName(lastName).middleName(middleName).build();
        } else {
            aClone = new Builder().firstName(this.firstName).lastName(this.lastName).build();
        }
        return aClone;
    }

    /**
     * Person builder class, gives us a ability to statically call
     * method for a person to be built.
     */
    public static final class PersonBuilder {
        /**
         * Creates a person using person builder.
         *
         * @param first first name of the person.
         * @param last last name of the person.
         * @param middle the middle name of the person (Can be null).
         *
         * @return a built person w/wo a middle name.
         */
        public static Person createPerson(final String first, final String last, final String middle) {
            if(Utilities.checkNotNullNotEmpty(middle)) {
                return new Person.Builder().firstName(first).lastName(last).middleName(middle).build();
            } else {
                return new Person.Builder().firstName(first).lastName(last).build();
            }
        }
    }
}
