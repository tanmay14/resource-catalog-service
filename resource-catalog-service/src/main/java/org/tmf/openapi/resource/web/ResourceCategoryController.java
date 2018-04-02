package org.tmf.openapi.resource.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.resource.common.ObjectMapper.mapObjectWithExcludeFilter;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmf.openapi.resource.domain.catalog.ResourceCategory;
import org.tmf.openapi.resource.service.ResourceCategoryService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/resourceCategory")
public class ResourceCategoryController {

	@Autowired
	private ResourceCategoryService categoryService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCategory(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = ResourceCategory.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(categoryService.findAllCategories(predicate)),
				requestParams, "resourceCategoryFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(categoryService.findCategory(id)),
				requestParams, "resourceCategoryFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCategory(@RequestBody @Valid ResourceCategory category)
			throws URISyntaxException {

		category = categoryService.createCategory(category);
		return ResponseEntity.created(populateHref(category).getHref())
				.body(mapObjectWithExcludeFilter(category, null, "resourceCategoryFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCategory(@PathVariable String id, @RequestBody ResourceCategory category) {

		validateCategory(id, category);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(categoryService.updateCategory(category)),
				null, "resourceCategoryFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCategory(@PathVariable String id, @RequestBody ResourceCategory category) {

		validateCategory(id, category);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(categoryService.partialUpdateCategory(category)), null, "resourceCategoryFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCategory(@PathVariable String id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();

	}

	private ResourceCategory populateHref(ResourceCategory category) {
		category.setHref(linkTo(ResourceCategoryController.class).slash(category.getId()).toUri());
		return category;
	}

	private List<ResourceCategory> populateHref(List<ResourceCategory> catalogs) {
		for (ResourceCategory category : catalogs) {
			populateHref(category);
		}
		return catalogs;
	}

	private void validateCategory(String id, ResourceCategory category) {
		if ((null == category.getId()) || (null != category.getId() && !category.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
