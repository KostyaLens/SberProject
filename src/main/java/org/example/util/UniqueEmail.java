package org.example.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Пользователь с данным email уже загерегстрирован";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
