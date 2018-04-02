package org.tmf.openapi.resource.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.catalog.ResourceCatalog;
import org.tmf.openapi.resource.repository.CatalogRepository;
import org.tmf.openapi.resource.web.ResourceCatalogController;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceCatalogService {

	@Autowired
	private CatalogRepository catalogRepository;

	public ResourceCatalog createCatalog(@Valid ResourceCatalog resourceCatalog) {

		if (resourceCatalog.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Catalog");
		}

		setDefaultValues(resourceCatalog);
		return catalogRepository.save(resourceCatalog);
	}

	public ResourceCatalog findCatalog(@NotNull String id) {
		return catalogRepository.findById(id).get();
	}

	public List<ResourceCatalog> findAllCatalogs(Predicate predicate) {

		if (null == predicate) {
			return catalogRepository.findAll();
		}
		return toList(catalogRepository.findAll(predicate));
	}

	public void deleteCatalog(@NotNull String id) {
		ResourceCatalog existingCatalog = getExistingCatalog(id);
		catalogRepository.delete(existingCatalog);

	}

	public ResourceCatalog updateCatalog(@Valid ResourceCatalog resourceCatalog) {

		return catalogRepository.save(resourceCatalog);
	}

	public ResourceCatalog partialUpdateCatalog(ResourceCatalog resourceCatalog) {

		if (null == resourceCatalog.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceCatalog existingCatalog = getExistingCatalog(resourceCatalog.getId());

		if (null != resourceCatalog.getName()) {
			existingCatalog.setName(resourceCatalog.getName());
		}

		if (null != resourceCatalog.getDescription()) {
			existingCatalog.setDescription(resourceCatalog.getDescription());
		}
		
		if(null !=resourceCatalog.getBaseType()) {
			existingCatalog.setBaseType(resourceCatalog.getBaseType());
		}

		if (null != resourceCatalog.getSchemaLocation()) {
			existingCatalog.setSchemaLocation(resourceCatalog.getSchemaLocation());
		}

		if (null != resourceCatalog.getVersion()) {
			existingCatalog.setVersion(resourceCatalog.getVersion());
		}

		if (null != resourceCatalog.getValidFor()) {
			existingCatalog.setValidFor(resourceCatalog.getValidFor());
		}

		if (null != resourceCatalog.getLifecycleStatus()) {
			existingCatalog.setLifecycleStatus(resourceCatalog.getLifecycleStatus());
		}
		
		if (null!= resourceCatalog.getRelatedParty()) {
			existingCatalog.setRelatedParty(resourceCatalog.getRelatedParty());
		}
		
		if (null!= resourceCatalog.getCategory()) {
			existingCatalog.setCategory(resourceCatalog.getCategory());
		}

		return catalogRepository.save(existingCatalog);

	}

	private ResourceCatalog getExistingCatalog(@NotNull String id) {
		Optional<ResourceCatalog> existingCatalogOption = catalogRepository.findById(id);
		if (!existingCatalogOption.isPresent()) {
			throw new IllegalArgumentException("Catalog with id " + id + " doesnot exists");
		}

		ResourceCatalog existingCatalog = existingCatalogOption.get();
		return existingCatalog;
	}

	private void setDefaultValues(ResourceCatalog resourceCatalog) {
		if (null == resourceCatalog.getType() || resourceCatalog.getType().trim().equals("")) {
			resourceCatalog.setType("ResourceCatalog");
		}
		
		if (null == resourceCatalog.getBaseType() || resourceCatalog.getBaseType().trim().equals("")) {
			resourceCatalog.setType("Catalog");
		}
		
		if (null == resourceCatalog.getVersion() || resourceCatalog.getVersion().trim().equals("")) {
			resourceCatalog.setVersion("1.0");
		}
		
		if (null == resourceCatalog.getLifecycleStatus() || resourceCatalog.getLifecycleStatus().trim().equals("")) {
			resourceCatalog.setLifecycleStatus("In Design");;
		}
		
		if (null == resourceCatalog.getSchemaLocation()|| resourceCatalog.getSchemaLocation().trim().equals("")) {
			resourceCatalog.setSchemaLocation(linkTo(ResourceCatalogService.class).slash("/catalogManagement/schema").slash("resourceCatalog").toUri().toString());
			
			
		}
	}

}
