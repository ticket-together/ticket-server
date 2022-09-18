package com.tickettogether.batch.job;

import com.tickettogether.domain.parts.domain.Parts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PartsCloseJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private static final int chunkSize = 100;
    private static final String JOB_NAME = "CloseParts";

    @Bean
    public Job closePartsJob() {
        return jobBuilderFactory.get(JOB_NAME + "Job")
                .start(closePartsStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step closePartsStep() {
        return stepBuilderFactory.get(JOB_NAME + "Step")
                .<Parts, Parts>chunk(chunkSize)
                .reader(closePartsReader())
                .processor(closePartsProcessor())
                .writer(closePartsWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Parts> closePartsReader() {
        JpaPagingItemReader<Parts> reader = new JpaPagingItemReader<>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setPageSize(chunkSize);
        reader.setQueryString("select p " +
                "from Parts p join fetch p.manager m " +
                "where part_date = DATE(now()) and p.status = 'ACTIVE' " +
                "order by p.id");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("ClosePartsReader");
        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Parts, Parts> closePartsProcessor() {
        return parts -> {
            log.info("[자동 마감되는 팟 정보] partsId={}, partsName={}, partsDate={}, status={}", parts.getId(), parts.getPartName(), parts.getPartDate(), parts.getStatus());
            return parts.close();
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Parts> closePartsWriter() {
        log.info("[팟 자동 마감 완료]");
        return new JpaItemWriterBuilder<Parts>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
