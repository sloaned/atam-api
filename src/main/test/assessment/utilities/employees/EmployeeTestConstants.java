package assessment.utilities.employees;

public class EmployeeTestConstants {

    //titles
    public static String VALID_COMMON_TITLE = "a Dev1";
    public static String VALID_UNCOMMON_TITLE = "Uncommon title";
    public static String INVALID_COMMON_TITLE = "z badTitle";
    public static String EMPTY_TITLE = "";

    //names and parsed result version
    public static String NAME_WITHOUT_SPECIAL_CHARS = "testName";
    public static String PARSED_NAME_WITHOUT_SPECIAL_CHARS = "testName";

    public static String NAME_WITH_PARENTHESES_AND_SPACE = "testName (a)";
    public static String PARSED_NAME_WITH_PARENTHESES_AND_SPACE = "testName";

    public static String NAME_WITH_CURLY_BRACES_AND_SPACE = "testName {A}";
    public static String PARSED_NAME_WITH_CURLY_BRACES_AND_SPACE = "testName";

    public static String NAME_WITH_SPACE_AND_PERIOD = "testName T.";
    public static String PARSED_NAME_WITH_SPACE_AND_PERIOD = "testName";

    public static String NAME_WITH_SPACE_AND_NO_PERIOD = "testName T";
    public static String PARSED_NAME_WITH_SPACE_AND_NO_PERIOD = "testName";

    public static String NAME_WITH_SPACE_AND_PERIOD_AND_MULTIPLE_LETTERS = "testName Jr.";
    public static String PARSED_NAME_WITH_SPACE_AND_PERIOD_AND_MULTIPLE_LETTERS = "testName Jr";

    public static String NAME_WITHOUT_SPACE_AND_WITH_PERIOD= "testName.T";
    public static String PARSED_NAME_WITHOUT_SPACE_AND_WITH_PERIOD= "testName T";
}
