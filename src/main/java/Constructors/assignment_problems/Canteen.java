package Constructors.assignment_problems;
public class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        int codeResult = this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeResult != 0) {
            return codeResult;
        }

        return this.canteenName.length() - other.canteenName.length();
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        for (int i = 0; i < canteens.length - 1; i++) {
            for (int j = 0; j < canteens.length - i - 1; j++) {
                if (canteens[j].compareTo(canteens[j + 1]) > 0) {
                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }

        return canteens;
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getTrustScore() {
        return trustScore;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] result = rankCanteens(canteens);

        System.out.println("Ranked Canteens:");

        for (Canteen canteen : result) {
            System.out.println(
                    canteen.getCanteenCode() + " - "
                            + canteen.getCanteenName() + " - "
                            + canteen.getTrustScore()
            );
        }
    }
}
