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
import org.tmf.openapi.resource.domain.ResourceCandidate;
import org.tmf.openapi.resource.service.ResourceCandidateService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/resourceCandidate")
public class ResourceCandidateController {

	@Autowired
	private ResourceCandidateService resourceCandidateService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getResourceCandidate(
			@RequestParam MultiValueMap<String, String> requestParams, Pageable pageable,
			@QuerydslPredicate(root = ResourceCandidate.class) Predicate predicate) {
		return ResponseEntity
				.ok(mapObjectWithExcludeFilter(populateHref(resourceCandidateService.findAllResourceCandidate(predicate)),
						requestParams, "resourceCandidateFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getResourceCandidate(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(resourceCandidateService.findResourceCandidate(id)), requestParams, "resourceCandidateFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createResourceCandidate(
			@RequestBody @Valid ResourceCandidate resourceCandidate) throws URISyntaxException {

		resourceCandidate = resourceCandidateService.createResourceCandidate(resourceCandidate);
		return ResponseEntity.created(populateHref(resourceCandidate).getHref())
				.body(mapObjectWithExcludeFilter(resourceCandidate, null, "resourceCandidateFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateResourceCandidate(@PathVariable String id,
			@RequestBody ResourceCandidate resourceCandidate) {

		validateResourceCandidate(id, resourceCandidate);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(resourceCandidateService.updateResourceCandidate(resourceCandidate)),
						null, "resourceCandidateFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchResourceCandidate(@PathVariable String id,
			@RequestBody ResourceCandidate resourceCandidate) {

		validateResourceCandidate(id, resourceCandidate);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(resourceCandidateService.partialUpdateResourceCandidate(resourceCandidate)), null,
				"resourceCandidateFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCatalog(@PathVariable String id) {
		resourceCandidateService.deleteResourceCandidate(id);
		return ResponseEntity.noContent().build();

	}

	private ResourceCandidate populateHref(ResourceCandidate resourceCandidate) {
		resourceCandidate.setHref(linkTo(ResourceCandidateController.class).slash(resourceCandidate.getId()).toUri());
		return resourceCandidate;
	}

	private List<ResourceCandidate> populateHref(List<ResourceCandidate> resourceCandidates) {
		for (ResourceCandidate resourceCandidate : resourceCandidates) {
			populateHref(resourceCandidate);
		}
		return resourceCandidates;
	}

	private void validateResourceCandidate(String id, ResourceCandidate resourceCandidate) {
		if ((null == resourceCandidate.getId())
				|| (null != resourceCandidate.getId() && !resourceCandidate.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
