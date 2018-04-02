package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.ResourceSpecification;

import org.tmf.openapi.resource.repository.ResourceSpecificationRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceSpecificationService {

	@Autowired
	private ResourceSpecificationRepository resourceSpecificationRepository;

	public ResourceSpecification createResourceSpecification(@Valid ResourceSpecification resourceSpecification) {

		if (resourceSpecification.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating ResourceSpecification");
		}
		
		if(null!=resourceSpecification.getName() && null!=resourceSpecification.getType()) {
		
			setDefaultValues(resourceSpecification);
		}
		else {
			throw new IllegalArgumentException("name/type is mandatory while creating ResourceSpecification");
		}

		return resourceSpecificationRepository.save(resourceSpecification);
	}

	public ResourceSpecification findResourceSpecification(@NotNull String id) {
		return resourceSpecificationRepository.findById(id).get();
	}

	public List<ResourceSpecification> findAllResourceSpecification(Predicate predicate) {

		if (null == predicate) {
			return resourceSpecificationRepository.findAll();
		}
		return toList(resourceSpecificationRepository.findAll(predicate));
	}
	

	public void deleteResourceSpecification(@NotNull String id) {
		ResourceSpecification existingResourceSpecification = getExistingCategory(id);
		resourceSpecificationRepository.delete(existingResourceSpecification);

	}

	public ResourceSpecification updateResourceSpecification(@Valid ResourceSpecification resourceSpecification) {

		return resourceSpecificationRepository.save(resourceSpecification);
	}

	public ResourceSpecification partialUpdateResourceSpecification(ResourceSpecification resourceSpecification) {

		if (null == resourceSpecification.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceSpecification existingResourceSpecification = getExistingCategory(resourceSpecification.getId());

		if (null != resourceSpecification.getName()) {
			existingResourceSpecification.setName(resourceSpecification.getName());
		}

		if (null != resourceSpecification.getDescription()) {
			existingResourceSpecification.setDescription(resourceSpecification.getDescription());
		}

		if (null != resourceSpecification.getSchemaLocation()) {
			existingResourceSpecification.setSchemaLocation(resourceSpecification.getSchemaLocation());
		}
		
		if (null!=resourceSpecification.getBaseType()) {
			existingResourceSpecification.setBaseType(resourceSpecification.getBaseType());
		}

		if (null != resourceSpecification.getVersion()) {
			existingResourceSpecification.setVersion(resourceSpecification.getVersion());
		}

		if (null != resourceSpecification.getValidFor()) {
			existingResourceSpecification.setValidFor(resourceSpecification.getValidFor());
		}

		if (null != resourceSpecification.getLifecycleStatus()) {
			existingResourceSpecification.setLifecycleStatus(resourceSpecification.getLifecycleStatus());
		}
		
		if (null != resourceSpecification.getCategory()) {
			existingResourceSpecification.setCategory(resourceSpecification.getCategory());
		}
		
		if (null != resourceSpecification.getTargetResourceSchema()) {
			existingResourceSpecification.setTargetResourceSchema(resourceSpecification.getTargetResourceSchema());
		}
		
		if (null != resourceSpecification.getFeature()) {
			existingResourceSpecification.setFeature(resourceSpecification.getFeature());
		}
		
		if (null != resourceSpecification.getAttachment()) {
			existingResourceSpecification.setAttachment(resourceSpecification.getAttachment());
		}
		
		if (null != resourceSpecification.getRelatedParty()) {
			existingResourceSpecification.setRelatedParty(resourceSpecification.getRelatedParty());
		}
		
		if(null!=resourceSpecification.getResourceSpecCharacteristic()) {
			existingResourceSpecification.setResourceSpecCharacteristic(resourceSpecification.getResourceSpecCharacteristic());
		}
		
		if(null!=resourceSpecification.getResourceSpecificationRelationship()) {
			existingResourceSpecification.setResourceSpecificationRelationship(resourceSpecification.getResourceSpecificationRelationship());
		}
		

		

		

		return saveResourceSpecification(existingResourceSpecification);

	}

	private ResourceSpecification saveResourceSpecification(@Valid ResourceSpecification resourceSpecification) {
		return resourceSpecificationRepository.save(resourceSpecification);
	}

	private void setDefaultValues(ResourceSpecification resourceSpecification) {
		

		

		
	}

	private ResourceSpecification getExistingCategory(@NotNull String id) {
		Optional<ResourceSpecification> existingResourceSpecificationOption = resourceSpecificationRepository.findById(id);
		if (!existingResourceSpecificationOption.isPresent()) {
			throw new IllegalArgumentException("resourceSpecificationRepository with id " + id + " doesnot exists");
		}

		ResourceSpecification existingResourceSpecification = existingResourceSpecificationOption.get();
		return existingResourceSpecification;
	}

}
