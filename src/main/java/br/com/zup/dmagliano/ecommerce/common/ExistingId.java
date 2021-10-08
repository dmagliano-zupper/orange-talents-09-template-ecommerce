package br.com.zup.dmagliano.ecommerce.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = {ExistingIdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingId {

    String message() default "O id informado não existe!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName();

    Class<?> domainClass();

}
