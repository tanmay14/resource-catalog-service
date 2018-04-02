package org.tmf.openapi.resource.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.tmf.openapi.resource.domain.catalog.ResourceCatalog;

@Repository
public interface CatalogRepository extends MongoRepository<ResourceCatalog, String>, QuerydslPredicateExecutor<ResourceCatalog> {

}



