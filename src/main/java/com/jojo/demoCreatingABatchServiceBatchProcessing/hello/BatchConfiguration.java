package com.jojo.demoCreatingABatchServiceBatchProcessing.hello;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @EnableBatchProcessing
 *   this annotation adds many critical beans that support jobs and saves you
 *   a lot of leg work. This example uses a memory-based database (Provided by the annotation)
 *   MEANING when it's done, the data is gone
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//////////////////////
    /**
     * todo   CHUNK ONE (1)
     *
     * Chunk one / part 1
     * This first chunk of code defines the "input, processor, and output"
     *  - reader() method creates an "ItemReader"
     *    It looks for a file called "sample-data.csv"
     *    and parses each line item with enough information to turn it into a "Person".
     */
    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }
//////////////////////
    /**
     * Chunk one, part 2
     *
     *  - processor() creates an instance of our "PersonItemProcessor" we defined earlier,
     *      meant to upperCase the data.
     */
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
////////////
    /**
     * Chunk one, part 3
     *
     *  - write(DataSource) creates an "ItemWriter"
     *     this one is aimed at a JDBC destination and automatically gets a copy of the
     *     dataSource created by "@EnableBatchProcessing". It includes the SQL statement
     *     needed to insert a single "Person" driven by JAVA bean properties
     */
    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }
    // end::readerwriterprocessor[]
    //TODO       END OF CHUNK ONE (1)

///////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

    /**
     * TODO     CHUNK TWO (2)
     *
     * Chunk two, part 1
     *
     * THIS CHUNK FOCUSES ON THE ACTUAL JOB CONFIGURATION
     *
     * The first method: "importUserJob(JobCompletionNotificationListener, Step)
     *   - this defines the job
     *
     * The second method: "step1(JdbcBatchItemWriter<Person>)
     *   - this defines a single step
     *
     *
     * Jobs are built from steps, where each step ca involve a
     *   "reader, a processor, and a writer"
     *
     *
     *
     * In this Job Definition (Method 1)
     *   - you need an "incrementer", because jobs use a database to maintain execution state
     *     THEN you list each step, of which this job has only one step
     *     THEN, the Job ends, and the JAVA API produces a perfectly configured job
     *
     *      todo NOTE: (google more about execution state)
     */
    // tag::jobstep[]
    @Bean //Job Completion Notification Listener class not made yet
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }


    /**
     * In this method (the step definition):
     *  - you define how much data to write at a time. In this case, it writes up to
     *      to ten records at a time.
     *  - Next, you configure the reader, processor, and writer USING the injected
     *      bits from earlier.
     */
    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
    // end::jobstep[]

    /**
     * todo: GREEN NOTE ON SITE
     *         chunk() is prefixed<"Person, Person"> because it's a generic method.
     *         This represents the input and output type of each "chunk" of processing,
     *         and lines up with "ItemReader<Person>" and "ItemWriter<Person>"
     */

}
