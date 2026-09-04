package Java_String_Concepts.class_problems;
import java.util.Scanner;
public class NewsroomCopyEditing{
    public static String[] findShortestandLargestWord(String text){
        text = text.replaceAll("[^a-zA-Z\\s]","");
        String[] words = text.split("\\s+");
        String shortest = words[0];
        String longest = words[0];
        for(String word: words){
            if(word.length()< shortest.length()){
                shortest = word;
            }
            if(word.length() > longest.length()){
                longest = word;
            }
        }
        return new String[]{shortest, longest};
    }
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a paragraph or a sentence: ");
        String text = scanner.nextLine();
        String[] result = findShortestandLargestWord(text);
        System.out.println("The shortest from the given text is " + result[1]);
        System.out.println("The length of the shortest word is " + result[1].length());
        System.out.println("The longest word from the text is " + result[0]);
        System.out.println("The length of the longest word is " + result[0].length());
        scanner.close();
    }
}
