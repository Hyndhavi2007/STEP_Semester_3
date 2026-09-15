package AccessModifiers.assignment_problems;
public class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    static {
        System.out.println("Circulation Ledger Started");
    }

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (bookIds == null || bookIds.length > 20) {
            throw new IllegalArgumentException("Invalid book IDs");
        }

        for (String bookId : bookIds) {
            if (bookId == null || !bookId.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException("Invalid book ID");
            }
        }

        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book ID index");
        }

        if (newId == null || !newId.matches("BK-\\d{3}")) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        String[] correctedIds = bookIds.clone();
        correctedIds[index] = newId;

        if (this instanceof ReferenceOnlyLoanReceipt) {
            ReferenceOnlyLoanReceipt reference =
                    (ReferenceOnlyLoanReceipt) this;

            return new ReferenceOnlyLoanReceipt(
                    memberId,
                    correctedIds,
                    reference.getRoomNumber()
            );
        }

        return new LoanReceipt(memberId, correctedIds);
    }

    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            LoanReceipt invalid =
                    new LoanReceipt(
                            "LIB-8841",
                            new String[]{"BK-100", "bad"}
                    );
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LoanReceipt receipt =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{"BK-100", "BK-101"}
                );

        String[] ids = receipt.getBookIds();
        ids[0] = "HACKED";

        System.out.println(receipt.getBookIds()[0]);

        LoanReceipt corrected =
                receipt.withCorrectedBookId(1, "BK-999");

        System.out.println(corrected.getBookIds()[1]);

        ReferenceOnlyLoanReceipt reference =
                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{"BK-200"},
                        "Reading Room 3"
                );

        LoanReceipt[] batch = {
                reference,
                null,
                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(
                processNightlyCirculation(batch)
        );
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid room number");
        }

        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

