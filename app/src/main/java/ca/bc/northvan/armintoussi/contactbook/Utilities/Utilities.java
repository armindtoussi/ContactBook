package ca.bc.northvan.armintoussi.contactbook.Utilities;

/**
 * Created by armin2 on 4/23/2018.
 *
 * A utilities class that holds functions
 * for string checks and email checks etc.
 */
public class Utilities {

    /**
     * Checks that strings aren't null or empty.
     *
     * @param isNull arg to check.
     * @return true if not null or not empty.
     */
    public static boolean checkNotNullNotEmpty(String isNull) {
        if(isNull == null || isNull.length() < 1) {
            return false;
        }
        return true;
    }

    /**
     * Check that the phone number is formatted the way we want.
     * Formatting: ###-###-####
     *
     * @param number the number to format.
     * @return constructed & built string.
     */
    public static String checkPhoneNumberFormat(char[] number) {
        if(number.length == 10) {
            return insertPhoneHyphens(number);
        }//otherwise it's 12 just return.
        return number.toString();
    }

    /**
     * Inserts the hyphens into a phone number for
     * consistent formatting.
     *
     * @param number the number to format.
     * @return constructed & formatted string.
     */
    private static String insertPhoneHyphens(char[] number) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < number.length; i++) {
            if(i == 2 || i == 5) {
                sb.append(number[i]);
                sb.append("-");
            } else {
                sb.append(number[i]);
            }
        }
        return sb.toString();
    }

    /**
     * Checks if the email has an @ symbol.
     *
     * @param email the email to check.
     * @return true if has @ symbol.
     */
    public static boolean checkEmailFormat(char[] email) {
        for(char c: email) {
            if(c == '@') {
                return true;
            }
        }
        return false;
    }
}
