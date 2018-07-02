public class ExtraNotes {
    /**
     * Create a file called: sample-data.csv
     *   in the resources folder IN THE MAIN
     *
     *   // this spreadsheet contains first and last names on each row, separated by comma
     *        This is a fairly common pattern that Spring handles out-of-the-box, as you'll see
     *
     * ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * -- Create a table to store data from the "sample-data file" from the resources folder
     * -- Spring boot runs "schema--@@platform@@.sql
     *
     * /////////////////////////////////////////////////////////////////////////////////
     * ///////////////////////NOW WE START MAKING A BUSINESS CLASS ///////////////////////////////////////
     * Now that you see the format of data inputs and outputs, you write
     *   code to represent a row of data.
     *
     *
     *
     * Make a Person POJO with first name and last name and to string etc
     *  - You can instantiate the Person class either with first and last name
     *      through a constructor, or by setting the properties
     *
     * /////////////////////////////////////////////////////////////
     * ///////////////////////////////////////////////////////////
     *
     * /////////////CREATE AN INTERMEDIATE PROCESSOR ///////////////////
     *
     * A common paradigm in batch processing is to ingest data,
     *   transform it, and then pipe it out somewhere else. Here
     *   you write a simple transformer that converts the names to
     *   uppercase.
     *
     *
     * PersonItemProcessor implements Spring Batch’s ItemProcessor interface. This
     * makes it easy to wire the code into a batch job that you define further down
     * in this guide. According to the interface, you receive an incoming Person object,
     * after which you transform it to an upper-cased Person.
     *
     *
     * NOTES IN GREEN:
     * todo There is no requirement that the input and output types be the same. In fact,
     * todo    after one source of data is read, sometimes the application’s data flow needs
     * todo    a different data type.
     *
     * ////////////////////////////////////////////////////////////////////////////////
     *
     * PUT TOGETHER A BATCH JOB
     *
     * Now we put together the actual batch job. Spring Batch provides many
     *   utility classes that reduce the need to write custom code. Instead
     *   we can just focus on the business logic.
     *
     *
     * BREAK DOWN IN THE BATCH CONFIGURATION CLASS
     *
     * ///////////////////////////////////////////////////
     *
     *
     *
     * NOW YOU MAKE THE APPLICATION EXECUTABLE
     *   - NOTES IN APPLICATION CLASS (the psvm class)
     *
     */
}
