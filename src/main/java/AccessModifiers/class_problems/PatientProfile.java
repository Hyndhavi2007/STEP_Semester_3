package AccessModifiers.class_problems;
public class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPin;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
        this.lockerPin = null;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (this.patientId == null) {
            this.patientId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin == null) {
            return;
        }

        if (pin.matches("\\d{4,6}")) {
            this.lockerPin = Integer.toString(pin.hashCode());
        }
    }

    public static void main(String[] args) {

        PatientProfile p1 =
                new PatientProfile("Arjun Iyer");

        System.out.println(p1.getPatientId());

        PatientProfile p2 =
                new PatientProfile(
                        "MT2026-0142",
                        "Arjun Iyer"
                );

        System.out.println(p2.getPatientId());

        PatientProfile p3 =
                new PatientProfile();

        p3.setPatientId("MT2026-0142");
        p3.setPatientId("HACKED-0000");

        System.out.println(p3.getPatientId());

        p3.setLockerPin("1234");
    }
}
