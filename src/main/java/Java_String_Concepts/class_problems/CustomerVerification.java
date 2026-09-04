package Java_String_Concepts.class_problems;
import java.util.Scanner;
 class CustomerVerification{
    public static String validateCustomerID(String customerID){
        if(customerID.startsWith("VIP - ")){
            return "VIP Customer";
        } else {
            return "Regular Customer";
        }
    }
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID: ");
        String customerID = scanner.nextLine();
        String result = validateCustomerID(customerID);
        System.out.println(result);
        scanner.close();
    }
}
