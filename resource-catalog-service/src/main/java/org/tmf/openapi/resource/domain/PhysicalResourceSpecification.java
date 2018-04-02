package org.tmf.openapi.resource.domain;

import java.net.URI;
import java.util.Date;
import java.util.List;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tmf.openapi.resource.domain.common.LifeCycleStatus;
import org.tmf.openapi.resource.domain.common.PlaceRef;
import org.tmf.openapi.resource.domain.common.RelatedPartyRef;
import org.tmf.openapi.resource.domain.common.TargetResourceSchemaRef;
import org.tmf.openapi.resource.validator.ValidResourceSpecification;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ValidResourceSpecification
@Document
@JsonFilter("physicalResourceSpecificationFilter")
@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class PhysicalResourceSpecification {

	@NotEmpty
	private String id;

	@Transient
	private URI href;

	private String name;

	private String description;
	
	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	
	@NotNull
	@Valid
	private TimePeriod validFor;

	private String version;
	
	private Date lastUpdate;

	private LifeCycleStatus lifecycleStatus;

	private boolean isBundle;
	
	private String category;	
	
	private String model;
	
	@NotEmpty @NotBlank @NotNull
	private String part;
	
	private String sku;
	
	@NotEmpty @NotBlank @NotNull
	private String vendor;
	
	private List<PlaceRef> place;	
	
	private List<TargetResourceSchemaRef> targetResourceSchema;
	
	private List<Feature> feature;
	
	private List<Attachment> attachment;
	
	private List<RelatedPartyRef> relatedParty;	

	private List<ResourceSpecCharacteristic> resourceSpecCharacteristic;	

	private List<ResourceSpecificationRelationship> resourceSpecificationRelationship;	

	
	

}


















