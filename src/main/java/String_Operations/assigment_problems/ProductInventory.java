package String_Operations.assigment_problems;
public class ProductInventory {

    public static void parseInventoryRecord(String csvLine) {

        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
        } else {
            System.out.println("Product: " + fields[0]
                    + " | SKU: " + fields[1]
                    + " | Qty: " + fields[2]);
        }
    }

    public static void main(String[] args) {

        String record1 = "Wireless Mouse,WM-2201,150";
        parseInventoryRecord(record1);

        String record2 = "Wireless Mouse,150";
        parseInventoryRecord(record2);
    }
}
