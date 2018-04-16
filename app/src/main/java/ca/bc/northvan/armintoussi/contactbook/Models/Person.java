package ca.bc.northvan.armintoussi.contactbook.Models;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Person {

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



}
