package WEEK12.class_problems;

class CampusPin {

    public static String banner = "Campus";

    public String name = "General Pin";

    public String getDetails() {

        return "Details: " + name;
    }
}

class HostelPin extends CampusPin {

    public static String banner = "Hostel";

    public String name = "Hostel Pin";

    @Override
    public String getDetails() {

        return "Details: " + name;
    }
}

class Map {

    public static void main(String[] args) {

        CampusPin p = new HostelPin();

        // Static variable
        System.out.println("banner: " + p.banner);

        // Instance variable
        System.out.println("name: " + p.name);

        // Overridden method
        System.out.println(p.getDetails());

        System.out.println();

        System.out.println(
                "banner: decided by reference type");

        System.out.println(
                "name: decided by reference type");

        System.out.println(
                "getDetails(): decided by object type");
    }
}
