package compass.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import compass.test.interfaces.ScoredField;
import compass.test.model.Contact;
import compass.test.model.ContactComparisonResult;
import compass.test.service.ContactsService;

import java.lang.reflect.Field;
import java.util.List;

public class Library {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ContactsService contactsService;

    public Library(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    public List<ContactComparisonResult> findPossibleDuplicates(long id) {
        Contact contact = contactsService.findById(id);
        return compareWithContacts(contact);
    }

    private List<ContactComparisonResult> compareWithContacts(Contact contactToCompare) {
        return contactsService.getAllContacts().stream()
                .filter(contact -> !contactToCompare.id().equals(contact.id()))
                .map(contact -> scoreEquality(contactToCompare, contact))
                .toList();
    }

    private ContactComparisonResult scoreEquality(Contact aContact, Contact otherContact) {
        ObjectNode aContactNode = MAPPER.valueToTree(aContact);
        ObjectNode anotherContactNode = MAPPER.valueToTree(otherContact);
        int maxScore = 0;
        int total = 0;
        for (Field field: Contact.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(ScoredField.class)) {
                ScoredField annotation = field.getAnnotation(ScoredField.class);
                int score = annotation.value();
                maxScore += score;
                field.setAccessible(true);
                String fieldName = field.getName();
                if (aContactNode.get(fieldName).equals(anotherContactNode.get(fieldName))) {
                    total += score;
                }
            }
        }
        double relativeScore = (double) total / maxScore * 100;
        return new ContactComparisonResult(
                aContact.id(),
                otherContact.id(),
                relativeScore > 60.0 ? "High": "Low"
        );
    }
}
