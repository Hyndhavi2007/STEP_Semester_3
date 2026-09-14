package OOP.Assignment_problems;

public class F4 {
    static class BrokenLibraryMember {

        static String name;
        static String memberId;
        static int booksIssued;

        BrokenLibraryMember(String name,
                            String memberId,
                            int booksIssued) {

            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }

        void printName() {
            System.out.println(name);
        }
    }

    static class LibraryMember {

        // Instance fields - every member has separate values.
        String name;
        String memberId;
        int booksIssued;

        // Static fields - shared by all members.
        static String libraryName = "SRM Library";
        static int memberCount = 0;

        LibraryMember(String name, int booksIssued) {

            this.name = name;
            this.booksIssued = booksIssued;

            memberCount++;

            // Generate member ID automatically.
            this.memberId =
                    "LM-" + (1000 + memberCount);
        }

        void printMemberCard() {

            System.out.println(
                    name + " | " + memberId
            );
        }

        static void printTotalMembers() {

            System.out.println(
                    "Total members: " +
                            memberCount
            );
        }
    }


    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenLibraryMember member1 =
                new BrokenLibraryMember(
                        "Aditi", "LM-1001", 2
                );

        BrokenLibraryMember member2 =
                new BrokenLibraryMember(
                        "Rohan", "LM-1002", 3
                );

        member1.printName();
        member2.printName();
        System.out.println();

        System.out.println("Fixed version:");

        LibraryMember m1 =
                new LibraryMember("Aditi", 2);

        LibraryMember m2 =
                new LibraryMember("Rohan", 3);

        m1.printMemberCard();
        m2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}