package com.cardiweb.logstash;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cardiweb.logstash.data.CommuneCSV;
import com.cardiweb.logstash.data.MaireCSV;
import com.cardiweb.logstash.model.Commune;
import com.cardiweb.logstash.model.Maire;
import com.cardiweb.logstash.model.Profession;
import com.cardiweb.logstash.repository.CommuneRepository;
import com.cardiweb.logstash.repository.MaireRepository;
import com.cardiweb.logstash.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;


@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Logstash13a14Application {
	  private static final Logger logger = LoggerFactory.getLogger(Logstash13a14Application.class);


    public static void main(String[] args) {
        SpringApplication.run(Logstash13a14Application.class, args);
    }
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("communes", "maires", "professions");
    }
    
    @Bean 
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner initCodesPostaux(@Value("classpath:communes.csv") Resource codesPostaux){
    	return new CommandLineRunner() {
    		
    		@Autowired
    		CommuneRepository communeRepository;
    		
			@Override
			public void run(String... args) throws Exception {
				CsvMapper csvMapper = new CsvMapper();
				CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
				MappingIterator<CommuneCSV> it = csvMapper.readerFor(CommuneCSV.class).with(schema).readValues(codesPostaux.getInputStream());

				int count = 0;
				while (it.hasNext()) {
					CommuneCSV communeCSV = it.next();
					Commune commune = new Commune(communeCSV);
					communeRepository.save(commune);
					count++;
				}
				logger.info("Inserted {} communes", count);
			}
		};
    }
    
    @Bean 
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner initMaires(@Value("classpath:maires.csv") Resource maires){
    	return new CommandLineRunner() {

    		@Autowired
    		CommuneRepository communeRepository;
    		@Autowired
    		MaireRepository maireRepository;
    		@Autowired
    		ProfessionRepository professionRepository;
    		

			@Override
			public void run(String... args) throws Exception {
				CsvMapper csvMapper = new CsvMapper();
				CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
				MappingIterator<MaireCSV> it = csvMapper.readerFor(MaireCSV.class).with(schema).readValues(maires.getInputStream());

				int count = 0;
				int countProfession = 0;
				while (it.hasNext()) {
					MaireCSV maireCSV = it.next();
					
					Profession profession = professionRepository.findOne(maireCSV.getCsp()); 
					if(profession == null){
						profession = professionRepository.save(new Profession(maireCSV.getCsp(),maireCSV.getLibcsp()));
						countProfession++;
					}
					
					Commune commune = communeRepository.findOne(maireCSV.getCodeinsee());
					
					maireRepository.save(new Maire(maireCSV,profession, commune));
					count++;
				}
				logger.info("Inserted {} maires", count);
				logger.info("Inserted {} professions", countProfession);
			}
		};
    }
}
