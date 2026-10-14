package DSA.class_problems;
public class LibraryBook {
    public static String binarySearchBook(
            String[][] catalog, String targetIsbn) {

        int low = 0;
        int high = catalog.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int result = catalog[mid][0].compareTo(targetIsbn);

            if (result == 0) {
                return catalog[mid][1];
            } else if (result < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        String[][] catalog = {
                {"1001", "History of Science"},
                {"1002", "Classic Mythology"},
                {"1003", "Modern Physics"},
                {"1004", "World Geography"}
        };

        String result = binarySearchBook(catalog, "1003");
        System.out.println(result);
    }
}