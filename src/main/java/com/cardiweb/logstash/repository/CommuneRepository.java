package com.cardiweb.logstash.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.cardiweb.logstash.model.Commune;

@RepositoryRestResource
public interface CommuneRepository extends PagingAndSortingRepository<Commune, String>{

	@Override
	@Cacheable("communes")
	public Commune findOne(String id);

	@RestResource
	public Page<Commune> findByNomLikeIgnoreCase(@Param("nom") String nom, Pageable page);

	@RestResource
	public Page<Commune> findByCodeDepartement(@Param("code") String codeDpt, Pageable page);

	@RestResource
	public Page<Commune> findByCodeRegion(@Param("code") String codeRegion, Pageable page);
}
