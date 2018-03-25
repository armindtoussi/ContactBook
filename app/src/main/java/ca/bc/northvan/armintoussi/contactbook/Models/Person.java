package ca.bc.northvan.armintoussi.contactbook.Models;

/**
 * Created by armin2 on 3/25/2018.
 */

public class Person {

    private String firstName;
    private String middleName;
    private String lastName;

    public Person(final String firstName, final String middleName, final String lastName) {
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
    }

    public Person(final String firstName, final String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
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
            aClone = new Person(this.firstName, this.middleName, this.lastName);
        } else {
            aClone = new Person(this.firstName, this.lastName);
        }
        return aClone;
    }
}
