package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.ResourceCandidate;
import org.tmf.openapi.resource.repository.ResourceCandidateRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceCandidateService {

	@Autowired
	private ResourceCandidateRepository resourceCandidateRepository;

	public ResourceCandidate createResourceCandidate(@Valid ResourceCandidate resourceCandidate) {

		if (resourceCandidate.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating resourceCandidate");
		}

		setDefaultValues(resourceCandidate);

		return resourceCandidateRepository.save(resourceCandidate);
	}

	public ResourceCandidate findResourceCandidate(@NotNull String id) {
		return resourceCandidateRepository.findById(id).get();
	}

	public List<ResourceCandidate> findAllResourceCandidate(Predicate predicate) {

		if (null == predicate) {
			return resourceCandidateRepository.findAll();
		}
		return toList(resourceCandidateRepository.findAll(predicate));
	}

	public void deleteResourceCandidate(@NotNull String id) {

		ResourceCandidate existingResourceCandidate = getExistingResourceCandidate(id);
		resourceCandidateRepository.delete(existingResourceCandidate);

	}

	public ResourceCandidate updateResourceCandidate(@Valid ResourceCandidate resourceCandidate) {

		return saveResourceCandidate(resourceCandidate);
	}

	public ResourceCandidate partialUpdateResourceCandidate(ResourceCandidate resourceCandidate) {

		if (null == resourceCandidate.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceCandidate existingResourceCandidate = getExistingResourceCandidate(resourceCandidate.getId());


		if (null != resourceCandidate.getName()) {
			existingResourceCandidate.setName(resourceCandidate.getName());
		}

		if (null != resourceCandidate.getDescription()) {
			existingResourceCandidate.setDescription(resourceCandidate.getDescription());
		}	

		if (null != resourceCandidate.getLifecycleStatus()) {
			existingResourceCandidate.setLifecycleStatus(resourceCandidate.getLifecycleStatus());
		}

		if (null != resourceCandidate.getValidFor()) {
			existingResourceCandidate.setValidFor(resourceCandidate.getValidFor());
		}

		if (null != resourceCandidate.getVersion()) {
			existingResourceCandidate.setVersion(resourceCandidate.getVersion());
		}

		if (null != resourceCandidate.getSchemaLocation()) {
			existingResourceCandidate.setSchemaLocation(resourceCandidate.getSchemaLocation());
		}	

		if (null != resourceCandidate.getCategory()) {
			existingResourceCandidate.setCategory(resourceCandidate.getCategory());
		}

		if(null!=resourceCandidate.getBaseType()) {
			existingResourceCandidate.setBaseType(resourceCandidate.getBaseType());
		}

		if(null!=resourceCandidate.getResourceSpecification()) {
			existingResourceCandidate.setResourceSpecification(resourceCandidate.getResourceSpecification());
		}

		return saveResourceCandidate(existingResourceCandidate);

	}

	private ResourceCandidate saveResourceCandidate(@Valid ResourceCandidate resourceCandidate) {
		return resourceCandidateRepository.save(resourceCandidate);
	}

	private void setDefaultValues(ResourceCandidate resourceCandidate) {


		if (null == resourceCandidate.getType() || resourceCandidate.getType().trim().equals("")) {

			resourceCandidate.setType("ResourceCategory");
		}

		if (null == resourceCandidate.getBaseType() || resourceCandidate.getType().trim().equals("")) {

			resourceCandidate.setBaseType("Category");
		}

	}

	private ResourceCandidate getExistingResourceCandidate(@NotNull String id) {
		Optional<ResourceCandidate> existingResourceCandidateOption = resourceCandidateRepository.findById(id);
		if (!existingResourceCandidateOption.isPresent()) {
			throw new IllegalArgumentException("ResourceCandidate with id " + id + " doesnot exists");
		}

		ResourceCandidate existingResourceCandidate = existingResourceCandidateOption.get();
		return existingResourceCandidate;
	}

}
