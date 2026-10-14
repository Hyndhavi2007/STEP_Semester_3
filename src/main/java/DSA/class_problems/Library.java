package DSA.class_problems;
public class Library {
    public static String findBook(String[][] catalog, String targetIsbn) {
        int left = 0;
        int right = catalog.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = catalog[mid][0].compareTo(targetIsbn);

            if (comparison == 0) {
                return catalog[mid][1];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
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

        System.out.println(findBook(catalog, "1002"));
        System.out.println(findBook(catalog, "1005"));
    }
}