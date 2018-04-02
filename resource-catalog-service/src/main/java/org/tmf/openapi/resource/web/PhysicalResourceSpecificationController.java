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
import org.tmf.openapi.resource.domain.PhysicalResourceSpecification;


import org.tmf.openapi.resource.service.PhysicalResourceSpecificationService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/physicalResourceSpec")
public class PhysicalResourceSpecificationController {

	@Autowired
	private PhysicalResourceSpecificationService physicalResourceSpecificationService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCategory(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = PhysicalResourceSpecification.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceSpecificationService.findAllPhysicalResourceSpecification(predicate)),
				requestParams, "physicalResourceSpecificationFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceSpecificationService.findPhysicalResourceSpecification(id)),
				requestParams, "physicalResourceSpecificationFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCategory(@RequestBody @Valid PhysicalResourceSpecification physicalResourceSpecification)
			throws URISyntaxException {

		physicalResourceSpecification = physicalResourceSpecificationService.createPhysicalResourceSpecification(physicalResourceSpecification);
		return ResponseEntity.created(populateHref(physicalResourceSpecification).getHref())
				.body(mapObjectWithExcludeFilter(physicalResourceSpecification, null, "physicalResourceSpecificationFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCategory(@PathVariable String id, @RequestBody PhysicalResourceSpecification physicalResourceSpecification) {

		validateCategory(id, physicalResourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceSpecificationService.updatePhysicalResourceSpecification(physicalResourceSpecification)),
				null, "physicalResourceSpecificationFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCategory(@PathVariable String id, @RequestBody PhysicalResourceSpecification physicalResourceSpecification) {

		validateCategory(id, physicalResourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(physicalResourceSpecificationService.partialUpdatePhysicalResourceSpecification(physicalResourceSpecification)), null, "physicalResourceSpecificationFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCategory(@PathVariable String id) {
		physicalResourceSpecificationService.deletePhysicalResourceSpecification(id);
		return ResponseEntity.noContent().build();

	}

	private PhysicalResourceSpecification populateHref(PhysicalResourceSpecification physicalResourceSpecification) {
		physicalResourceSpecification.setHref(linkTo(PhysicalResourceSpecificationController.class).slash(physicalResourceSpecification.getId()).toUri());
		return physicalResourceSpecification;
	}

	private List<PhysicalResourceSpecification> populateHref(List<PhysicalResourceSpecification> physicalResourceSpecifications) {
		for (PhysicalResourceSpecification physicalResourceSpecification : physicalResourceSpecifications) {
			populateHref(physicalResourceSpecification);
		}
		return physicalResourceSpecifications;
	}

	private void validateCategory(String id, PhysicalResourceSpecification physicalResourceSpecification) {
		if ((null == physicalResourceSpecification.getId()) || (null != physicalResourceSpecification.getId() && !physicalResourceSpecification.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
