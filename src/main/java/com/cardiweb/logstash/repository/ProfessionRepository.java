package com.cardiweb.logstash.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cardiweb.logstash.model.Profession;

@RepositoryRestResource
public interface ProfessionRepository extends PagingAndSortingRepository<Profession, Integer>{

	@Override
	@Cacheable("professions")
	public Profession findOne(Integer id);
	
}
