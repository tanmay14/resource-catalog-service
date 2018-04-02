package org.tmf.openapi.resource.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.resource.domain.ResourceCandidate;
import org.tmf.openapi.resource.domain.ResourceSpecification;

public class ResourceSpecificationValidator implements ConstraintValidator<ValidResourceSpecification, ResourceSpecification> {

	@Override
	public boolean isValid(ResourceSpecification resourceSpecification, ConstraintValidatorContext context) {

		if (null == resourceSpecification)
			return true;

		
		return true;
	}
}