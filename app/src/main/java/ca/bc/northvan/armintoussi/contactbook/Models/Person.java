package ca.bc.northvan.armintoussi.contactbook.Models;

import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

/**
 * Created by armin2 on 3/25/2018.
 *
 * This class represents a Person.
 * Builder pattern is implemented to build
 * a Person as well as PersonBuilder class
 * for ease of repeatedly building a person.
 *
 */
public class Person {
    /** String that holds first name. */
    private String firstName;
    /** String that holds middle name. */
    private String middleName;
    /** String that holds last name. */
    private String lastName;

    /**
     * Private ctor that sets all the instance variables.
     *
     * @param builder builder obj used for building this obj.
     */
    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
    }

    /**
     * Static Builder class for easily buidling
     * a Person with variable arguments.
     */
    public static class Builder {
        /** String that holds first name. */
        private String firstName;
        /** String that holds middle name. */
        private String middleName;
        /** String that holds last name. */
        private String lastName;

        /**
         * Add the first name.
         *
         * @param firstName the first name to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Add the last name.
         *
         * @param lastName the last name to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Adds the middle name.
         *
         * @param middleName the middle name to add to build.
         *
         * @return this obj to continue building.
         */
        public Builder middleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        /**
         * Build method to finish the build and return a
         * built Person obj.
         *
         * @return built Person obj.
         */
        public Person build() {
            return new Person(this);
        }
    }

    /**
     * Gets the first name.
     *
     * @return first name as a String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return middle name as a String.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets the last name.
     *
     * @return last name as a String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name as a String.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the middle name as a String.
     */
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name as a String.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * To String method for displaying the name.
     *
     * @return full name as a String.
     */
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

    /**
     * Makes a clone of the Person object this is called on.
     *
     * @return a built clone of the obj. 
     */
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
                return new Person.Builder().firstName(Utilities.capitalizeFirstLetter(first))
                                           .lastName(Utilities.capitalizeFirstLetter(last))
                                           .middleName(Utilities.capitalizeFirstLetter(middle)).build();
            } else {
                return new Person.Builder().firstName(Utilities.capitalizeFirstLetter(first))
                                           .lastName(Utilities.capitalizeFirstLetter(last)).build();
            }
        }
    }
}
