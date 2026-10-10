package WEEK10.class_problems;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Customer customer = (Customer) obj;

        return id == customer.id &&
                Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
public class CustomerRegistry {
    public static void main(String[]args){
       Set<Customer> registry = new HashSet<>();
        Customer c1 = new Customer(101, "Asha");
        Customer c2 = new Customer(101, "Asha");
        Customer c3 = new Customer(102, "Ravi");

        System.out.println(registry.add(c1));
        boolean added = registry.add(c2);
        if (added) {
            System.out.println("true");
        } else {
            System.out.println("false (duplicate rejected)");
        }

        System.out.println(registry.add(c3));

        System.out.println("unique count " + registry.size());

        System.out.println("contains: " +
                registry.contains(new Customer(101, "Asha")));
    }
}
