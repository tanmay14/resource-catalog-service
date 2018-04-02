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
import org.tmf.openapi.resource.domain.catalog.ResourceCatalog;
import org.tmf.openapi.resource.service.ResourceCatalogService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/resourceCatalog")
public class ResourceCatalogController {

	@Autowired
	private ResourceCatalogService resourceCatalogService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCatalog(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = ResourceCatalog.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceCatalogService.findAllCatalogs(predicate)),
				requestParams, "resourceCatalogFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceCatalogService.findCatalog(id)), requestParams,
				"resourceCatalogFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCatalog(@RequestBody @Valid ResourceCatalog serviceCatalog)
			throws URISyntaxException {

		serviceCatalog = resourceCatalogService.createCatalog(serviceCatalog);
		return ResponseEntity.created(populateHref(serviceCatalog).getHref())
				.body(mapObjectWithExcludeFilter(serviceCatalog, null, "resourceCatalogFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCatalog(@PathVariable String id, @RequestBody ResourceCatalog serviceCatalog) {

		validateCatalog(id, serviceCatalog);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(resourceCatalogService.updateCatalog(serviceCatalog)), null, "resourceCatalogFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCatalog(@PathVariable String id, @RequestBody ResourceCatalog serviceCatalog) {

		validateCatalog(id, serviceCatalog);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceCatalogService.partialUpdateCatalog(serviceCatalog)),
				null, "resourceCatalogFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCatalog(@PathVariable String id) {
		resourceCatalogService.deleteCatalog(id);
		return ResponseEntity.noContent().build();

	}

	private ResourceCatalog populateHref(ResourceCatalog serviceCatalog) {
		serviceCatalog.setHref(linkTo(ResourceCatalogController.class).slash(serviceCatalog.getId()).toUri());
		return serviceCatalog;
	}

	private List<ResourceCatalog> populateHref(List<ResourceCatalog> serviceCatalogs) {
		for (ResourceCatalog serviceCatalog : serviceCatalogs) {
			populateHref(serviceCatalog);
		}
		return serviceCatalogs;
	}

	private void validateCatalog(String id, ResourceCatalog serviceCatalog) {
		if ((null == serviceCatalog.getId()) || (null != serviceCatalog.getId() && !serviceCatalog.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
