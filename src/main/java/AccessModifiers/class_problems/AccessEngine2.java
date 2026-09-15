package AccessModifiers.class_problems;
public class AccessEngine2 {

    public static String classifyAccess(
            String fieldModifier,
            String accessorContext) {

        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    || accessorContext.equals(
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null) {
            return "";
        }

        String[] parts = accessorContext.split("_");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(
                    part.substring(0, 1).toUpperCase()
                            + part.substring(1).toLowerCase()
            );
        }

        return result.toString();
    }

    public static void main(String[] args) {

        System.out.println(
                classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                )
        );

        System.out.println(
                classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );

        System.out.println(
                describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );
    }
}
