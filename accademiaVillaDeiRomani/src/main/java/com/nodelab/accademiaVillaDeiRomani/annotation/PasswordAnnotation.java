package com.nodelab.accademiaVillaDeiRomani.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.nodelab.accademiaVillaDeiRomani.validators.PasswordConstraintValidator;


@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAnnotation {

public abstract String message() default "Invalid Password";
	
    public abstract Class<?>[] groups() default {};

    public abstract Class<?>[] payload() default {};
	
}
