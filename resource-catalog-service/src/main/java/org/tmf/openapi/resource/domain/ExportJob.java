package org.tmf.openapi.resource.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
public class ExportJob {

	@NotEmpty
	private String id;

	private String href;

	private String query;

	private String path;

	private String contentType;

	private String status;

	private String url;
	
	private Date completionDate;

	private Date creationDate;

	private String errorLog;

}
