package com.pelizzaris.sufs.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorDataRetroativa.class)
public @interface DataRetroativa {

    String message() default "A data deve ser hoje ou até {days} dias atrás";

    int days() default 3; // limite padrão

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
