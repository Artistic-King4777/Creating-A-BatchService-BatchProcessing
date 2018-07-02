package com.jojo.demoCreatingABatchServiceBatchProcessing.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person>{

    /**
     * PersonItemProcessor implements Spring Batch’s ItemProcessor interface.
     * This makes it a lot easier to wire(inject) the code into a batch job
     *   that we define "ENTER DEFINITION"
     *
     *   According to the interface we made, we get a Person object,
     *     which transforms to an upper-cased Person object.
     * ////////////////////////////////////////////////////////////
     *
     * Writing a simple transformer that converts the names
     *   to uppercase
     *
     * todo Input and output DO NOT have to be the same
     *       -- input can be one thing while output can be a different thing
     */

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
