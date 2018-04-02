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
import org.tmf.openapi.resource.domain.ResourceSpecification;
import org.tmf.openapi.resource.service.ResourceSpecificationService;


import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/resourceSpecification")
public class ResourceSpecificationController {

	@Autowired
	private ResourceSpecificationService resourceSpecificationService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCategory(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = ResourceSpecification.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceSpecificationService.findAllResourceSpecification(predicate)),
				requestParams, "resourceSpecificationFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceSpecificationService.findResourceSpecification(id)),
				requestParams, "resourceSpecificationFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCategory(@RequestBody @Valid ResourceSpecification resourceSpecification)
			throws URISyntaxException {

		resourceSpecification = resourceSpecificationService.createResourceSpecification(resourceSpecification);
		return ResponseEntity.created(populateHref(resourceSpecification).getHref())
				.body(mapObjectWithExcludeFilter(resourceSpecification, null, "resourceSpecificationFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCategory(@PathVariable String id, @RequestBody ResourceSpecification resourceSpecification) {

		validateCategory(id, resourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceSpecificationService.updateResourceSpecification(resourceSpecification)),
				null, "resourceSpecificationFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCategory(@PathVariable String id, @RequestBody ResourceSpecification resourceSpecification) {

		validateCategory(id, resourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(resourceSpecificationService.partialUpdateResourceSpecification(resourceSpecification)), null, "resourceSpecificationFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCategory(@PathVariable String id) {
		resourceSpecificationService.deleteResourceSpecification(id);
		return ResponseEntity.noContent().build();

	}

	private ResourceSpecification populateHref(ResourceSpecification resourceSpecification) {
		resourceSpecification.setHref(linkTo(ResourceSpecificationController.class).slash(resourceSpecification.getId()).toUri());
		return resourceSpecification;
	}

	private List<ResourceSpecification> populateHref(List<ResourceSpecification> catalogs) {
		for (ResourceSpecification resourceSpecification : catalogs) {
			populateHref(resourceSpecification);
		}
		return catalogs;
	}

	private void validateCategory(String id, ResourceSpecification resourceSpecification) {
		if ((null == resourceSpecification.getId()) || (null != resourceSpecification.getId() && !resourceSpecification.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
