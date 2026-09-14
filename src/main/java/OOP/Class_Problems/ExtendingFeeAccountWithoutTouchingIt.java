package OOP.Class_Problems;
public class ExtendingFeeAccountWithoutTouchingIt {

    static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected: amount must be positive.");
                return;
            }

            amountPaid += amount;
        }

        public double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        void payInTwoInstallments(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected.");
                return;
            }

            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {
        private double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee,
                              double amountPaid, double scholarshipPercent) {
            super(regNo, totalFee, amountPaid);

            if (scholarshipPercent < 0 || scholarshipPercent > 100) {
                throw new IllegalArgumentException(
                        "Scholarship percentage must be between 0 and 100."
                );
            }

            this.scholarshipPercent = scholarshipPercent;
        }

        double effectiveDue() {
            return getDue() * (1 - scholarshipPercent / 100);
        }
    }

    public static void main(String[] args) {

        FeeAccount plain = new FeeAccount(
                "RA101", 150000, 0
        );

        HostelFeeAccount hostel = new HostelFeeAccount(
                "RA102", 200000, 60000
        );

        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount(
                "RA103", 180000, 0, 20
        );

        // Apply payments.
        plain.pay(150000);
        hostel.payInTwoInstallments(20000);

        FeeAccount[] accounts = {
                plain,
                hostel,
                scholarship
        };

        for (FeeAccount account : accounts) {

            if (account instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount s =
                        (ScholarshipFeeAccount) account;

                System.out.println(
                        "Scholarship account effective due: Rs " +
                                s.effectiveDue()
                );

            } else if (account instanceof HostelFeeAccount) {
                HostelFeeAccount h =
                        (HostelFeeAccount) account;

                System.out.println(
                        "Hostel account due: Rs " +
                                h.getDue()
                );

            } else {
                System.out.println(
                        "Plain account due: Rs " +
                                account.getDue()
                );
            }
        }
    }
}