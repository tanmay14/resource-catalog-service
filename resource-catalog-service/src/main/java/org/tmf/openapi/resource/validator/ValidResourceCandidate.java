package org.tmf.openapi.resource.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ResourceCandidateValidator.class })

public @interface ValidResourceCandidate {

	String message() default "{org.tmf.openapi.catlog.validation.productOffering}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
