package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.LogicalResourceSpecification;
import org.tmf.openapi.resource.repository.LogicalResourceSpecificationRespository;

import com.querydsl.core.types.Predicate;

@Service
public class LogicalResourceSpecificationService {

	@Autowired
	private LogicalResourceSpecificationRespository logicalResourceSpecificationRespository;

	public LogicalResourceSpecification createLogicalResourceSpecification(@Valid LogicalResourceSpecification logicalResourceSpecification) {

		if (logicalResourceSpecification.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating LogicalResourceSpecification");
		}

		if(null!=logicalResourceSpecification.getName() && null!=logicalResourceSpecification.getType()) {
		setDefaultValues(logicalResourceSpecification);
		}
		else {
			throw new IllegalArgumentException("name/type must be not be empty while creating LogicalResourceSpecification");
		}

		return logicalResourceSpecificationRespository.save(logicalResourceSpecification);
	}

	public LogicalResourceSpecification findLogicalResourceSpecification(@NotNull String id) {
		return logicalResourceSpecificationRespository.findById(id).get();
	}

	public List<LogicalResourceSpecification> findAllLogicalResourceSpecification(Predicate predicate) {

		if (null == predicate) {
			return logicalResourceSpecificationRespository.findAll();
		}
		return toList(logicalResourceSpecificationRespository.findAll(predicate));
	}
	

	public void deleteLogicalResourceSpecification(@NotNull String id) {
		LogicalResourceSpecification existingLogicalResourceSpecification = getExistinglogicalResourceSpecification(id);
		logicalResourceSpecificationRespository.delete(existingLogicalResourceSpecification);

	}

	public LogicalResourceSpecification updateLogicalResourceSpecification(@Valid LogicalResourceSpecification logicalResourceSpecification) {

		return logicalResourceSpecificationRespository.save(logicalResourceSpecification);
	}

	public LogicalResourceSpecification partialUpdateLogicalResourceSpecification(LogicalResourceSpecification logicalResourceSpecification) {

		if (null == logicalResourceSpecification.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		LogicalResourceSpecification existingLogicalResourceSpecification = getExistinglogicalResourceSpecification(logicalResourceSpecification.getId());

		if (null != logicalResourceSpecification.getName()) {
			existingLogicalResourceSpecification.setName(logicalResourceSpecification.getName());
		}

		if (null != logicalResourceSpecification.getDescription()) {
			existingLogicalResourceSpecification.setDescription(logicalResourceSpecification.getDescription());
		}

		if (null != logicalResourceSpecification.getSchemaLocation()) {
			existingLogicalResourceSpecification.setSchemaLocation(logicalResourceSpecification.getSchemaLocation());
		}
		
		if (null!=logicalResourceSpecification.getBaseType()) {
			existingLogicalResourceSpecification.setBaseType(logicalResourceSpecification.getBaseType());
		}

		if (null != logicalResourceSpecification.getVersion()) {
			existingLogicalResourceSpecification.setVersion(logicalResourceSpecification.getVersion());
		}

		if (null != logicalResourceSpecification.getValidFor()) {
			existingLogicalResourceSpecification.setValidFor(logicalResourceSpecification.getValidFor());
		}

		if (null != logicalResourceSpecification.getLifecycleStatus()) {
			existingLogicalResourceSpecification.setLifecycleStatus(logicalResourceSpecification.getLifecycleStatus());
		}		

		return savelogicalResourceSpecification(existingLogicalResourceSpecification);

	}

	private LogicalResourceSpecification savelogicalResourceSpecification(@Valid LogicalResourceSpecification logicalResourceSpecification) {
		return logicalResourceSpecificationRespository.save(logicalResourceSpecification);
	}

	private void setDefaultValues(LogicalResourceSpecification logicalResourceSpecification) {
		if (null == logicalResourceSpecification.getBaseType() || logicalResourceSpecification.getBaseType().trim().equals("")) {
			logicalResourceSpecification.setBaseType("ResourceSpecification");
		}

		if (null == logicalResourceSpecification.getVersion()) {
			logicalResourceSpecification.setVersion("1.0");
		}

		
	}

	private LogicalResourceSpecification getExistinglogicalResourceSpecification(@NotNull String id) {
		Optional<LogicalResourceSpecification> existingCategoryOption = logicalResourceSpecificationRespository.findById(id);
		if (!existingCategoryOption.isPresent()) {
			throw new IllegalArgumentException("LogicalResourceSpecification with id " + id + " doesnot exists");
		}

		LogicalResourceSpecification existingCategory = existingCategoryOption.get();
		return existingCategory;
	}

}
