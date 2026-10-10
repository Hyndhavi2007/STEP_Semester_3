package WEEK10.class_problems;
import java.util.Arrays;
abstract class Resource {
    String id;
    Resource(String id) {
        this.id = id;
    }
    String getId() {
        return id;
    }
    abstract boolean canReserve();
    abstract boolean canDownload();
}
class Book extends Resource {
    Book(String id) {
        super(id);
    }
    boolean canReserve() {
        return true;
    }
    boolean canDownload() {
        return false;
    }
}
class EBook extends Resource {
    EBook(String id) {
        super(id);
    }
    boolean canReserve() {return false;
    }
    boolean canDownload() {
        return true;
    }
}
class Library {
    private Resource[] resources = new Resource[100];
    private int size = 0;
    public void addResource(Resource resource) {
        int index = findIndex(resource.getId());

        if (index >= 0) {
            System.out.println("duplicate rejected");
            return;
        }

        int insertPos = -index - 1;

        for (int i = size; i > insertPos; i--) {
            resources[i] = resources[i - 1];
        }

        resources[insertPos] = resource;
        size++;

        System.out.println(resource.getId() + " added");
    }

    private int findIndex(String id) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = resources[mid].getId().compareTo(id);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -(low + 1);
    }

    public void reserve(String id, String memberId) {
        int index = findIndex(id);

        if (index < 0) {
            System.out.println(id + " rejected: resource not found");
            return;
        }

        Resource resource = resources[index];

        if (resource.canReserve()) {
            System.out.println(id + " reserved for " + memberId);
        } else {
            System.out.println(id + " rejected: reserve unsupported");
        }
    }

    public void download(String id) {
        int index = findIndex(id);

        if (index < 0) {
            System.out.println(id + " rejected: resource not found");
            return;
        }

        Resource resource = resources[index];

        if (resource.canDownload()) {
            System.out.println(id + " downloaded");
        } else {
            System.out.println(id + " rejected: download unsupported");
        }
    }

    public void find(String id) {
        int index = findIndex(id);

        if (index >= 0) {
            System.out.println(id + " found at index " + index);
        } else {
            System.out.println(id + " not found");
        }
    }
}

public class LibraryPlatform {
    public static void main(String[] args) {
        Library library = new Library();

        library.addResource(new Book("B1"));
        library.addResource(new Book("B1"));
        library.addResource(new EBook("E1"));

        library.reserve("B1", "M1");
        library.download("B1");
        library.download("E1");
        library.find("E1");
    }
}