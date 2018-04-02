package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.PhysicalResourceSpecification;
import org.tmf.openapi.resource.repository.PhysicalResourceSpecificationRespository;

import com.querydsl.core.types.Predicate;

@Service
public class PhysicalResourceSpecificationService {

	@Autowired
	private PhysicalResourceSpecificationRespository physicalResourceSpecificationRespository;

	public PhysicalResourceSpecification createPhysicalResourceSpecification(@Valid PhysicalResourceSpecification physicalResourceSpecification) {

		if (physicalResourceSpecification.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating PhysicalResourceSpecification");
		}

		setDefaultValues(physicalResourceSpecification);

		return physicalResourceSpecificationRespository.save(physicalResourceSpecification);
	}

	public PhysicalResourceSpecification findPhysicalResourceSpecification(@NotNull String id) {
		return physicalResourceSpecificationRespository.findById(id).get();
	}

	public List<PhysicalResourceSpecification> findAllPhysicalResourceSpecification(Predicate predicate) {

		if (null == predicate) {
			return physicalResourceSpecificationRespository.findAll();
		}
		return toList(physicalResourceSpecificationRespository.findAll(predicate));
	}
	

	public void deletePhysicalResourceSpecification(@NotNull String id) {
		PhysicalResourceSpecification existingPhysicalResourceSpecification = getExistingphysicalResourceSpecification(id);
		physicalResourceSpecificationRespository.delete(existingPhysicalResourceSpecification);

	}

	public PhysicalResourceSpecification updatePhysicalResourceSpecification(@Valid PhysicalResourceSpecification physicalResourceSpecification) {

		return physicalResourceSpecificationRespository.save(physicalResourceSpecification);
	}

	public PhysicalResourceSpecification partialUpdatePhysicalResourceSpecification(PhysicalResourceSpecification physicalResourceSpecification) {

		if (null == physicalResourceSpecification.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		PhysicalResourceSpecification existingPhysicalResourceSpecification = getExistingphysicalResourceSpecification(physicalResourceSpecification.getId());

		if (null != physicalResourceSpecification.getName()) {
			existingPhysicalResourceSpecification.setName(physicalResourceSpecification.getName());
		}

		if (null != physicalResourceSpecification.getDescription()) {
			existingPhysicalResourceSpecification.setDescription(physicalResourceSpecification.getDescription());
		}

		if (null != physicalResourceSpecification.getSchemaLocation()) {
			existingPhysicalResourceSpecification.setSchemaLocation(physicalResourceSpecification.getSchemaLocation());
		}

		if (null != physicalResourceSpecification.getVersion()) {
			existingPhysicalResourceSpecification.setVersion(physicalResourceSpecification.getVersion());
		}

		if (null != physicalResourceSpecification.getValidFor()) {
			existingPhysicalResourceSpecification.setValidFor(physicalResourceSpecification.getValidFor());
		}

		if (null != physicalResourceSpecification.getLifecycleStatus()) {
			existingPhysicalResourceSpecification.setLifecycleStatus(physicalResourceSpecification.getLifecycleStatus());
		}		

		return savephysicalResourceSpecification(existingPhysicalResourceSpecification);

	}

	private PhysicalResourceSpecification savephysicalResourceSpecification(@Valid PhysicalResourceSpecification physicalResourceSpecification) {
		return physicalResourceSpecificationRespository.save(physicalResourceSpecification);
	}

	private void setDefaultValues(PhysicalResourceSpecification physicalResourceSpecification) {
		if (null == physicalResourceSpecification.getType() || physicalResourceSpecification.getType().trim().equals("")) {
			physicalResourceSpecification.setType("ServiceCategory");
		}

		if (null == physicalResourceSpecification.getVersion()) {
			physicalResourceSpecification.setVersion("1.0");
		}

		
	}

	private PhysicalResourceSpecification getExistingphysicalResourceSpecification(@NotNull String id) {
		Optional<PhysicalResourceSpecification> existingCategoryOption = physicalResourceSpecificationRespository.findById(id);
		if (!existingCategoryOption.isPresent()) {
			throw new IllegalArgumentException("PhysicalResourceSpecification with id " + id + " doesnot exists");
		}

		PhysicalResourceSpecification existingCategory = existingCategoryOption.get();
		return existingCategory;
	}

}
