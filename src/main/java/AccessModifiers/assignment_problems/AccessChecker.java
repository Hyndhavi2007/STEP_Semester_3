package AccessModifiers.assignment_problems;

public class AccessChecker {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        int privateAllowed = 0;
        int privateDenied = 0;
        int defaultAllowed = 0;
        int defaultDenied = 0;
        int protectedAllowed = 0;
        int protectedDenied = 0;
        int publicAllowed = 0;
        int publicDenied = 0;

        for (String[] attempt : attempts) {
            String modifier = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(modifier, context);

            if (modifier.equals("private")) {
                if (result.equals("ALLOWED")) {
                    privateAllowed++;
                } else {
                    privateDenied++;
                }
            } else if (modifier.equals("default")) {
                if (result.equals("ALLOWED")) {
                    defaultAllowed++;
                } else {
                    defaultDenied++;
                }
            } else if (modifier.equals("protected")) {
                if (result.equals("ALLOWED")) {
                    protectedAllowed++;
                } else {
                    protectedDenied++;
                }
            } else if (modifier.equals("public")) {
                if (result.equals("ALLOWED")) {
                    publicAllowed++;
                } else {
                    publicDenied++;
                }
            }
        }

        return "private: " + privateAllowed + " allowed / " + privateDenied
                + " denied | default: " + defaultAllowed + " allowed / "
                + defaultDenied + " denied | protected: " + protectedAllowed
                + " allowed / " + protectedDenied + " denied | public: "
                + publicAllowed + " allowed / " + publicDenied + " denied";
    }

    public static void main(String[] args) {

        System.out.println(
                classifyAccess("private", "SAME_CLASS")
        );

        System.out.println(
                classifyAccess("protected", "DIFFERENT_PACKAGE")
        );

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(summarizeByModifier(attempts));

        try {
            LibraryMember member =
                    new LibraryMember("LB9", "BR1", 0, "Priya Nair");
            System.out.println(member.getDisplayName());
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LibraryMember member =
                new LibraryMember("LB94", "BR1", 0, "Priya Nair");

        System.out.println(member.getMembershipId());
    }
}

class LibraryMember {

    private final String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId,
                         String branchCode,
                         double finesOwed,
                         String displayName) {

        String id = membershipId == null ? "" : membershipId.trim();

        if (id.isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException();
        }

        this.membershipId = id;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public String getDisplayName() {
        return displayName;
    }
}