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
import org.tmf.openapi.resource.domain.LogicalResourceSpecification;


import org.tmf.openapi.resource.service.LogicalResourceSpecificationService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/logicalResourceSpec")
public class LogicalResourceSpecificationController {

	@Autowired
	private LogicalResourceSpecificationService logicalResourceSpecificationService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCategory(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = LogicalResourceSpecification.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceSpecificationService.findAllLogicalResourceSpecification(predicate)),
				requestParams, "logicalResourceSpecificationFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceSpecificationService.findLogicalResourceSpecification(id)),
				requestParams, "logicalResourceSpecificationFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCategory(@RequestBody @Valid LogicalResourceSpecification logicalResourceSpecification)
			throws URISyntaxException {

		logicalResourceSpecification = logicalResourceSpecificationService.createLogicalResourceSpecification(logicalResourceSpecification);
		return ResponseEntity.created(populateHref(logicalResourceSpecification).getHref())
				.body(mapObjectWithExcludeFilter(logicalResourceSpecification, null, "logicalResourceSpecificationFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCategory(@PathVariable String id, @RequestBody LogicalResourceSpecification logicalResourceSpecification) {

		validateCategory(id, logicalResourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceSpecificationService.updateLogicalResourceSpecification(logicalResourceSpecification)),
				null, "logicalResourceSpecificationFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCategory(@PathVariable String id, @RequestBody LogicalResourceSpecification logicalResourceSpecification) {

		validateCategory(id, logicalResourceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(logicalResourceSpecificationService.partialUpdateLogicalResourceSpecification(logicalResourceSpecification)), null, "logicalResourceSpecificationFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCategory(@PathVariable String id) {
		logicalResourceSpecificationService.deleteLogicalResourceSpecification(id);
		return ResponseEntity.noContent().build();

	}

	private LogicalResourceSpecification populateHref(LogicalResourceSpecification logicalResourceSpecification) {
		logicalResourceSpecification.setHref(linkTo(LogicalResourceSpecificationController.class).slash(logicalResourceSpecification.getId()).toUri());
		return logicalResourceSpecification;
	}

	private List<LogicalResourceSpecification> populateHref(List<LogicalResourceSpecification> logicalResourceSpecifications) {
		for (LogicalResourceSpecification logicalResourceSpecification : logicalResourceSpecifications) {
			populateHref(logicalResourceSpecification);
		}
		return logicalResourceSpecifications;
	}

	private void validateCategory(String id, LogicalResourceSpecification logicalResourceSpecification) {
		if ((null == logicalResourceSpecification.getId()) || (null != logicalResourceSpecification.getId() && !logicalResourceSpecification.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
