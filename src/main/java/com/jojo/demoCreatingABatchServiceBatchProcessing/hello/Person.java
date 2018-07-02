package com.jojo.demoCreatingABatchServiceBatchProcessing.hello;

//Simple POJO
public class Person {

    /**
     * Business class: written after we see the format of data inputs
     *   and outputs, we write code to represent the rows of data
     *
     * AKA: First Name and Last Name
     */


    private String lastName;
    private String firstName;

    // Multiple ways to instantiate (make) a Person!
    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
