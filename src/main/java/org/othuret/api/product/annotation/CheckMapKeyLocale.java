package org.othuret.api.product.annotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element should be a map only with a key containing a String.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code Map} (key map is evaluated and should be a BCP 47 language format)</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Olivier THURET
 * @since 0.1
 */

@Documented
@Constraint(validatedBy = {CheckMapKeyLocale.CheckMapKeyLocaleValidator.class})
@Target({TYPE_PARAMETER, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface CheckMapKeyLocale {
    String message() default "The locale should be conform to the IETF BCP 47 standard (fr-FR)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class CheckMapKeyLocaleValidator implements ConstraintValidator<CheckMapKeyLocale, Map<String, ?>> {
        @Override
        public void initialize(CheckMapKeyLocale annotation) {
        }

        @Override
        public boolean isValid(final Map<String, ?> value, final ConstraintValidatorContext context) {
            if (value == null)
                return true;

            final CheckLocale.CheckLocaleValidator localeValidator = new CheckLocale.CheckLocaleValidator();

            for (final String requiredKey : value.keySet()) {
                if (!localeValidator.isValid(requiredKey, context))
                    return false;
            }

            return true;
        }
    }
}
