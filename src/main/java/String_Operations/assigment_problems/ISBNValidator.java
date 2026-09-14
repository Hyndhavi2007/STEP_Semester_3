package String_Operations.assigment_problems;
public class ISBNValidator {

    public static String normalizeCode(String raw) {

        String code = raw.trim();

        if (code.length() >= 3) {
            String publisher = code.substring(0, 3).toUpperCase();
            code = publisher + code.substring(3);
        }

        return code;
    }

    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: Wrong length";
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: Non-letter publisher code";
            }
        }


        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: Non-digit body";
            }
        }


        String publisher = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        StringBuilder result = new StringBuilder();

        result.append("[")
                .append(publisher)
                .append("] YEAR: ")
                .append(year)
                .append(" | CATALOG: ")
                .append(catalog);

        return result.toString();
    }

    public static void main(String[] args) {

        String rawCode = " pen2026004251 ";

        String normalizedCode = normalizeCode(rawCode);

        System.out.println(validateAndFormat(normalizedCode));
    }
}
