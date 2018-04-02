package org.tmf.openapi.resource.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.resource.domain.catalog.ResourceCategory;

public class ResourceCategoryValidator implements ConstraintValidator<ValidResourceCategory, ResourceCategory> {

	@Override
	public boolean isValid(ResourceCategory category, ConstraintValidatorContext context) {

		if (null == category || null == category.getIsRoot()) {
			return true;
		}
		
		if (false == category.getIsRoot()) {

			if (null == category.getParentId() || "".equals(category.getParentId().trim())) {
				return false;
			}
		}
		else {
			
			if (null != category.getParentId() || !"".equals(category.getParentId().trim())) {
				return false;
			}
		}
		return true;
	}
}