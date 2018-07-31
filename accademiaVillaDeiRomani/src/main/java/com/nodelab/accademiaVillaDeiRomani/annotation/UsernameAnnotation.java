package com.nodelab.accademiaVillaDeiRomani.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotEmpty
@Length(min=5)
public @interface UsernameAnnotation {

	public abstract String message() default "";
	
    public abstract Class<?>[] groups() default {};

    public abstract Class<?>[] payload() default {};
	
}
