package org.tmf.openapi.resource.domain.catalog;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tmf.openapi.resource.domain.TimePeriod;
import org.tmf.openapi.resource.domain.common.CategoryRef;
import org.tmf.openapi.resource.domain.common.LifeCycleStatus;
import org.tmf.openapi.resource.domain.common.RelatedPartyRef;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document
@JsonFilter("resourceCatalogFilter")
@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class ResourceCatalog {

	@Id
	private String id;

	@Transient
	private URI href;

	@NotEmpty
	@Indexed(unique = true)
	private String name;

	private String description;

	@JsonProperty("@type")
	
	private String type;

	@JsonProperty("@baseType")
	
	private String baseType;

	@JsonProperty("@schemaLocation")
	
	private String schemaLocation;

	private String lastUpdate;

	private String lifecycleStatus;

	private String version;

	@Valid
	private TimePeriod validFor;

	@Valid
	private List<CategoryRef> category;

	@Valid
	private List<RelatedPartyRef> relatedParty;

}
