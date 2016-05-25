package assessment.utilities.employees;

import static assessment.utilities.employees.EmployeeConstants.*;

/**
 * Utility class to help parse the information returned from the Employee Service CAMP project
 *

 */
public class EmployeeParser {

    private static final String mainTitlesRegex = "^[a-zA-Z] .*$";
    private static final String oddCharactersForNameRegex = "\\{.*|\\(.*|[a-zA-Z]?\\.";

    /**
     * Method to return common titles as a more user friendly format
     *
     * All common titles have the format of [a-m] description
     * Example: a Dev1, b Dev2, etc
     *
     * @param title The title returned from the Employee Service
     * @return The fixed title
     */
    public static String getTitle(String title) {


        if (title.equals("")) {
            return DEFAULT_TITLE;
        } else if (!title.matches(mainTitlesRegex)) { //if it isn't a common one just return it since they're fine as is
            return title;
        }

        //should already be lowercase, but just in case it changes in the future
        char c = Character.toLowerCase(title.charAt(0));

        String newTitle;
        switch (c) {
            case 'a':
                newTitle = A_TITLE;
                break;
            case 'b':
                newTitle = B_TITLE;
                break;
            case 'c':
                newTitle = C_TITLE;
                break;
            case 'd':
                newTitle = D_TITLE;
                break;
            case 'e':
                newTitle = E_TITLE;
                break;
            case 'f':
                newTitle = F_TITLE;
                break;
            case 'g':
                newTitle = G_TITLE;
                break;
            case 'h':
                newTitle = H_TITLE;
                break;
            case 'i':
                newTitle = I_TITLE;
                break;
            case 'j':
                newTitle = J_TITLE;
                break;
            case 'k':
                newTitle = K_TITLE;
                break;
            case 'l':
                newTitle = L_TITLE;
                break;
            case 'm':
                newTitle = M_TITLE;
                break;
            default:
                throw new IllegalArgumentException("Something went wrong while trying to parse the title");
        }
        return newTitle;
    }

    /**
     * Parses the email and converts old catalystITServices emails to devworks, and enforces them to be lowercase
     *
     * @param email The email to parse
     * @return The fixed email
     */
    public static String getEmail(String email) {
        String[] emailParts = email.split("@");
        if (emailParts.length < 2) {
            return null;
        } else {
            //if they're using the old catalystitservices email replace with devworks one, otherwise leave as is
            return emailParts[0] + "@" + (emailParts[1].toLowerCase().equals(OLD_EMAIL_URL) ? CURRENT_EMAIL_URL : emailParts[1].toLowerCase());
        }
    }

    /**
     * Parses through the name(first or last) and removes middle initials, periods, or other odd characters returned
     * from the Employee Service
     *
     * @param name The name to parse
     * @return The fixed name
     */
    public static String getName(String name) {
        String[] nameParts = name.split(" ");

        nameParts[0] = removePeriod(nameParts[0]);

        if (nameParts.length < 2) {
            return nameParts[0];
        }

        //some of the names have brackets, parentheses a period in them. Ex David (D), John {H}, Will S.
        if (nameParts[1].length() == 1 ||
                nameParts[1].matches(oddCharactersForNameRegex)) {
            return nameParts[0];
        }

        nameParts[1] = removePeriod(nameParts[1]);
        return nameParts[0] + " " + nameParts[1];
    }

    private static String removePeriod(String str) {
        if (str.contains(".")) {
            String[] splitParts = str.split("\\.");
            String newChar = splitParts.length > 1 ? " " : "";
            return str.replace(".", newChar);
        }
        return str;
    }
}