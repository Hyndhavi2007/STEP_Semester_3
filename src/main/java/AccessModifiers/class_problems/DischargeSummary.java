package AccessModifiers.class_problems;
public class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    static {
        System.out.println("Discharge Ledger Started");
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid patient ID");
        }

        if (medicationCodes == null) {
            throw new IllegalArgumentException("Medication codes cannot be null");
        }

        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("Invalid medication code");
            }
        }

        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid medication index");
        }

        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("Invalid medication code");
        }

        String[] correctedCodes = medicationCodes.clone();
        correctedCodes[index] = newCode;

        if (this instanceof CriticalCareDischargeSummary) {
            CriticalCareDischargeSummary critical =
                    (CriticalCareDischargeSummary) this;

            return new CriticalCareDischargeSummary(
                    patientId,
                    correctedCodes,
                    critical.getIcuDays()
            );
        }

        return new DischargeSummary(patientId, correctedCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int skipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {
            return "Processed: 0 | Skipped: 0 | Critical Care: 0 | Routine: 0";
        }

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                skipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return "Processed: " + processed
                + " | Skipped: " + skipped
                + " | Critical Care: " + criticalCare
                + " | Routine: " + routine;
    }

    public static void main(String[] args) {
        String[] medicines = {"MED-A", "MED-B", "MED-C"};

        DischargeSummary summary =
                new DischargeSummary("PT1001", medicines);

        System.out.println("Patient ID: " + summary.getPatientId());

        String[] returnedCodes = summary.getMedicationCodes();
        returnedCodes[0] = "MED-Z";

        System.out.println(
                "Original Medication: "
                        + summary.getMedicationCodes()[0]
        );

        DischargeSummary corrected =
                summary.withCorrectedMedication(1, "MED-X");

        System.out.println(
                "Corrected Medication: "
                        + corrected.getMedicationCodes()[1]
        );

        CriticalCareDischargeSummary critical =
                new CriticalCareDischargeSummary(
                        "PT2001",
                        new String[]{"MED-A", "MED-B"},
                        5
                );

        System.out.println("ICU Days: " + critical.getIcuDays());

        DischargeSummary[] batch = {
                summary,
                critical,
                corrected,
                null
        };

        System.out.println(processNightlyBatch(batch));
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        if (icuDays < 0) {
            throw new IllegalArgumentException("ICU days cannot be negative");
        }

        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}