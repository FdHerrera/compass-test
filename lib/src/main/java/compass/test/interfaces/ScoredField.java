package compass.test.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ScoredField {

    /**
     * Defines the score given to the field comparing to the same field in the other object.
     *
     * @return field equality score.
     */
    int value() default 0;
}