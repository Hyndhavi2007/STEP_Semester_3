package AccessModifiers.class_problems;
public class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        readings = new double[500];
        count = 0;

        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45) {
            return;
        }

        if (count < readings.length) {
            readings[count] = reading;
            count++;
        }
    }

    public double getAverage() {
        if (count == 0) {
            return 0.0;
        }

        double total = 0.0;

        for (int i = 0; i < count; i++) {
            total += readings[i];
        }

        return total / count;
    }

    public double[] getAllReadings() {
        double[] result = new double[count];

        for (int i = 0; i < count; i++) {
            result[i] = readings[i];
        }

        return result;
    }

    public static void main(String[] args) {

        PatientVitals v = new PatientVitals(
                new double[]{36.5, -2, 37.1}
        );

        double[] result = v.getAllReadings();

        for (double reading : result) {
            System.out.println(reading);
        }

        double[] copy = v.getAllReadings();

        copy[0] = 999;

        System.out.println(v.getAllReadings()[0]);
    }
}
