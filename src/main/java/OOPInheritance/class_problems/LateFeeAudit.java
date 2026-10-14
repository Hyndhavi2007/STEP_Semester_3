package OOPInheritance.class_problems;

import java.util.Arrays;

public class LateFeeAudit {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.addLateFee(100);

        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));

        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}
