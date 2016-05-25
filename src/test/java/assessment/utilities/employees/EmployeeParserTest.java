package assessment.utilities.employees;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static assessment.utilities.employees.EmployeeConstants.A_TITLE;
import static assessment.utilities.employees.EmployeeConstants.DEFAULT_TITLE;
import static assessment.utilities.employees.EmployeeTestConstants.*;
import static org.junit.Assert.assertEquals;

public class EmployeeParserTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void happyValidCommonUserIsParsedIntoCorrectFullTitle() {
        String parsedTitle = EmployeeParser.getTitle(VALID_COMMON_TITLE);
        String fullTitle = A_TITLE;

        assertEquals(fullTitle, parsedTitle);
    }

    @Test
    public void happyValidUncommonTitleIsParsedAndReturnedAsIs() {
        String parsedTitle = EmployeeParser.getTitle(VALID_UNCOMMON_TITLE);

        assertEquals(VALID_UNCOMMON_TITLE, parsedTitle);
    }

    @Test
    public void happyEmptyTitleIsReturnedWithDefaultTitle() {
        String parsedTitle = EmployeeParser.getTitle(EMPTY_TITLE);

        assertEquals(DEFAULT_TITLE, parsedTitle);
    }

    @Test
    public void sadInvalidCommonTitleThrowsException() {
        expectedEx.expect(IllegalArgumentException.class);
        EmployeeParser.getTitle(INVALID_COMMON_TITLE);
    }

    @Test
    public void happyNameWithoutAnySpecialCharactersIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITHOUT_SPECIAL_CHARS);

        assertEquals(PARSED_NAME_WITHOUT_SPECIAL_CHARS, parsedName);
    }

    @Test
    public void happyNameWithParenthesesAndSpaceIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITH_PARENTHESES_AND_SPACE);

        assertEquals(PARSED_NAME_WITH_PARENTHESES_AND_SPACE, parsedName);
    }

    @Test
    public void happyNameWithCurlyBracesAndSpaceIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITH_CURLY_BRACES_AND_SPACE);

        assertEquals(PARSED_NAME_WITH_CURLY_BRACES_AND_SPACE, parsedName);
    }


    @Test
    public void happyNameWithSpaceAndPeriodIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITH_SPACE_AND_PERIOD);

        assertEquals(PARSED_NAME_WITH_SPACE_AND_PERIOD, parsedName);
    }

    @Test
    public void happyNameWithSpaceAndNoPeriodIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITH_SPACE_AND_NO_PERIOD);

        assertEquals(PARSED_NAME_WITH_SPACE_AND_NO_PERIOD, parsedName);
    }

    @Test
    public void happyNameWithSpaceAndPeriodAndMultipleLettersIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITH_SPACE_AND_PERIOD_AND_MULTIPLE_LETTERS);

        assertEquals(PARSED_NAME_WITH_SPACE_AND_PERIOD_AND_MULTIPLE_LETTERS, parsedName);
    }

    @Test
    public void happyNameWithoutSpaceAndWithPeriodIsParsedAndReturnedProperly() {
        String parsedName = EmployeeParser.getName(NAME_WITHOUT_SPACE_AND_WITH_PERIOD);

        assertEquals(PARSED_NAME_WITHOUT_SPACE_AND_WITH_PERIOD, parsedName);
    }
}
