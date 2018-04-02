package org.tmf.openapi.resource.domain;

import static org.tmf.openapi.resource.common.Constant.DATE_WITH_TIME_PATTERN;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.tmf.openapi.resource.validator.ValidTimePeriod;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@ValidTimePeriod

@Data
@ToString(includeFieldNames = true)

public class TimePeriod {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WITH_TIME_PATTERN)
	@NotNull(message = "Start Date is mandatory")
	private String startDateTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WITH_TIME_PATTERN)
	private String endDateTime;

}
