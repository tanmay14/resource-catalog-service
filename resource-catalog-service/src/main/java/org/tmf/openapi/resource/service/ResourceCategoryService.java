package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.catalog.ResourceCategory;
import org.tmf.openapi.resource.repository.CategoryRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public ResourceCategory createCategory(@Valid ResourceCategory resourceCategory) {

		if (resourceCategory.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Category");
		}

		setDefaultValues(resourceCategory);

		return categoryRepository.save(resourceCategory);
	}

	public ResourceCategory findCategory(@NotNull String id) {
		return categoryRepository.findById(id).get();
	}

	public List<ResourceCategory> findAllCategories(Predicate predicate) {

		if (null == predicate) {
			return categoryRepository.findAll();
		}
		return toList(categoryRepository.findAll(predicate));
	}
	

	public void deleteCategory(@NotNull String id) {
		ResourceCategory existingCategory = getExistingCategory(id);
		categoryRepository.delete(existingCategory);

	}

	public ResourceCategory updateCategory(@Valid ResourceCategory resourceCategory) {

		return categoryRepository.save(resourceCategory);
	}

	public ResourceCategory partialUpdateCategory(ResourceCategory resourceCategory) {

		if (null == resourceCategory.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceCategory existingCategory = getExistingCategory(resourceCategory.getId());

		if (null != resourceCategory.getName()) {
			existingCategory.setName(resourceCategory.getName());
		}

		if (null != resourceCategory.getDescription()) {
			existingCategory.setDescription(resourceCategory.getDescription());
		}

		if (null != resourceCategory.getSchemaLocation()) {
			existingCategory.setSchemaLocation(resourceCategory.getSchemaLocation());
		}
		
		if(null!=resourceCategory.getBaseType()) {
			existingCategory.setBaseType(resourceCategory.getBaseType());
		}

		if (null != resourceCategory.getVersion()) {
			existingCategory.setVersion(resourceCategory.getVersion());
		}

		if (null != resourceCategory.getValidFor()) {
			existingCategory.setValidFor(resourceCategory.getValidFor());
		}

		if (null != resourceCategory.getLifecycleStatus()) {
			existingCategory.setLifecycleStatus(resourceCategory.getLifecycleStatus());
		}

		if (null != resourceCategory.getParentId()) {
			existingCategory.setParentId(resourceCategory.getParentId());
		}
		
		if (null != resourceCategory.getIsRoot()) {
			existingCategory.setIsRoot(resourceCategory.getIsRoot());
		}
		
		if (null!=resourceCategory.getCategory()) {
			existingCategory.setCategory(resourceCategory.getCategory());
		}
		
		if(null!=resourceCategory.getResourceCandidate()) {
			existingCategory.setResourceCandidate(resourceCategory.getResourceCandidate());
		}
		
		if(null!=resourceCategory.getRelatedParty()) {
			existingCategory.setRelatedParty(resourceCategory.getRelatedParty());
		}

		

		return saveCategory(existingCategory);

	}

	private ResourceCategory saveCategory(@Valid ResourceCategory resourceCategory) {
		return categoryRepository.save(resourceCategory);
	}

	private void setDefaultValues(ResourceCategory resourceCategory) {
		if (null == resourceCategory.getType() || resourceCategory.getType().trim().equals("")) {
			resourceCategory.setType("ResourceCategory");
		}

		if (null == resourceCategory.getBaseType() || resourceCategory.getBaseType().trim().equals("")) {
			resourceCategory.setType("Catalog");
		}
		
		if (null == resourceCategory.getVersion() || resourceCategory.getVersion().trim().equals("")) {
			resourceCategory.setVersion("1.0");
		}

		if (null == resourceCategory.getIsRoot()) {
			resourceCategory.setIsRoot(true);
		}
	}

	private ResourceCategory getExistingCategory(@NotNull String id) {
		Optional<ResourceCategory> existingCategoryOption = categoryRepository.findById(id);
		if (!existingCategoryOption.isPresent()) {
			throw new IllegalArgumentException("ServiceCategory with id " + id + " doesnot exists");
		}

		ResourceCategory existingCategory = existingCategoryOption.get();
		return existingCategory;
	}

}
