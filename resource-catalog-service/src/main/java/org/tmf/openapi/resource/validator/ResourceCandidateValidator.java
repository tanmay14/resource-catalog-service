package org.tmf.openapi.resource.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.resource.domain.ResourceCandidate;

public class ResourceCandidateValidator implements ConstraintValidator<ValidResourceCandidate, ResourceCandidate> {

	@Override
	public boolean isValid(ResourceCandidate resourceCandidate, ConstraintValidatorContext context) {

		if (null == resourceCandidate)
			return true;

		
		return true;
	}
}