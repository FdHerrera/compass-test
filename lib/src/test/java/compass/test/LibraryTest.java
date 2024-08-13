package compass.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import compass.test.model.Contact;
import compass.test.model.ContactComparisonResult;
import compass.test.service.ContactsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Mock
    ContactsService contactsService;

    @InjectMocks
    Library library;

    static Stream<Arguments> findPossibleDuplicates_arguments() throws IOException {
        return Stream.of(
                Arguments.of(
                        1001L,
                        Arrays.stream(MAPPER.readValue(new File("src/test/resources/contacts_list.json"), Contact[].class)).toList(),
                        List.of(
                                new ContactComparisonResult(1001L, 1002L, "High"),
                                new ContactComparisonResult(1001L, 1003L, "Low")
                        )
                ),
                Arguments.of(
                        1001L,
                        Arrays.stream(MAPPER.readValue(new File("src/test/resources/same_contacts.json"), Contact[].class)).toList(),
                        List.of(
                                new ContactComparisonResult(1001L, 1002L, "High"),
                                new ContactComparisonResult(1001L, 1003L, "High")
                        )
                ),
                Arguments.of(
                        1001L,
                        Arrays.stream(MAPPER.readValue(new File("src/test/resources/different_contacts.json"), Contact[].class)).toList(),
                        List.of(
                                new ContactComparisonResult(1001L, 1002L, "Low"),
                                new ContactComparisonResult(1001L, 1003L, "Low")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("findPossibleDuplicates_arguments")
    void findPossibleDuplicates(
            Long idToSearchForDups,
            List<Contact> contacts,
            List<ContactComparisonResult> expectedResults
    ) {
        Contact targetContact = contacts.stream().filter(c -> c.id().equals(idToSearchForDups)).findFirst().orElseThrow();

        when(contactsService.findById(idToSearchForDups)).thenReturn(targetContact);
        when(contactsService.getAllContacts()).thenReturn(contacts);

        List<ContactComparisonResult> actual = library.findPossibleDuplicates(idToSearchForDups);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedResults);
    }
}