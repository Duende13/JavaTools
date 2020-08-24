
import java.util.Scanner;
public class Multiply {

    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        System.out.print("Input a number: ");
        Integer number = input.nextInt();

        for(int i=1;i<=10;i++){
            System.out.println(number + " * "+ i +" = "+number*i);
        }
        
    }
    
}