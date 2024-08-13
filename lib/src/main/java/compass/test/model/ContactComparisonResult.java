package compass.test.model;

public record ContactComparisonResult(
        Long contactIdSource,
        Long contactIdMatch,
        String accuracy
) {
}