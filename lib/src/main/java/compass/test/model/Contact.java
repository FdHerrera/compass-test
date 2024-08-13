package compass.test.model;

import compass.test.interfaces.ScoredField;

public record Contact(
        Long id,
        @ScoredField(2)
        String firstName,
        @ScoredField(5)
        String lastName,
        @ScoredField(7)
        String email,
        @ScoredField(2)
        String zipCode,
        @ScoredField(6)
        String address
) {
}