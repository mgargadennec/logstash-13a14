package com.cardiweb.logstash.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cardiweb.logstash.model.Commune;
import com.cardiweb.logstash.model.Maire;

@RepositoryRestResource
public interface MaireRepository extends PagingAndSortingRepository<Maire, Long>{

	@Override
	@Cacheable("maires")
	public Maire findOne(Long id);
}
