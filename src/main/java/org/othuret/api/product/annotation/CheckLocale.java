package org.othuret.api.product.annotation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Locale;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CheckLocale.CheckLocaleValidator.class})
@Target({TYPE_USE, TYPE_PARAMETER, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@ReportAsSingleViolation
@Retention(RUNTIME)
public @interface CheckLocale {
    String message() default "The locale should be conform to the IETF BCP 47 standard (fr-FR)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class CheckLocaleValidator implements ConstraintValidator<CheckLocale, String> {
        @Override
        public void initialize(final CheckLocale annotation) {
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext context) {
            if (value == null)
                return true;


            if (LocaleUtils.isAvailableLocale(new Locale.Builder().setLanguageTag(value).build()))
                return true;

            return false;
        }
    }
}