package com.cardiweb.logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cardiweb.logstash.model.Commune;
import com.cardiweb.logstash.model.Maire;
import com.cardiweb.logstash.model.Profession;
import com.cardiweb.logstash.repository.CommuneRepository;
import com.cardiweb.logstash.repository.MaireRepository;
import com.cardiweb.logstash.repository.ProfessionRepository;

@Component
public class ScheduledCaching {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledCaching.class);

	@Autowired
	CommuneRepository communeRepository;
	@Autowired
	MaireRepository maireRepository;
	@Autowired
	ProfessionRepository professionRepository;

	@Scheduled(fixedDelay = 60 * 1000 * 5, initialDelay=15*1000)
	public void initCommuneCaching() {
		Pageable pageable = new PageRequest(0, 500);
		Page<Commune> page = null;
		do {
			page = communeRepository.findAll(pageable);

			for (Commune commune : page.getContent()) {
				communeRepository.findOne(commune.getInsee());
			}

			pageable = page.nextPageable();

		} while (page != null && page.hasNext());
		logger.info("Cached {} communes", page.getTotalElements());
	}

	@Scheduled(fixedDelay = 60 * 1000 * 5, initialDelay=15*1000)
	public void initMaireCaching() {
		Pageable pageable = new PageRequest(0, 500);
		Page<Maire> page = null;
		do {
			page = maireRepository.findAll(pageable);

			for (Maire maire : page.getContent()) {
				maireRepository.findOne(maire.getId());
			}

			pageable = page.nextPageable();

		} while (page != null && page.hasNext());

		logger.info("Cached {} maires", page.getTotalElements());

	}

	@Scheduled(fixedDelay = 60 * 1000 * 5, initialDelay=15*1000)
	public void initProfessionCaching() {
		Pageable pageable = new PageRequest(0, 500);
		Page<Profession> page = null;
		do {
			page = professionRepository.findAll(pageable);

			for (Profession profession : page.getContent()) {
				professionRepository.findOne(profession.getCode());
			}

			pageable = page.nextPageable();

		} while (page != null && page.hasNext());
		logger.info("Cached {} professions", page.getTotalElements());
	}
}
