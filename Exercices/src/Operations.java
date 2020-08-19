import java.util.Scanner;
/* Write a Java program to print the result of the following operations. Go to the editor
// Test Data:
// a. -5 + 8 * 6
// b. (55+9) % 9
// c. 20 + -3*5 / 8
// d. 5 + 15 / 3 * 2 - 8 % 3
// Expected Output :
// 43
// 1
// 19
// 13
*/
public class Operations {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner (System.in);
        System.out.print("Select type of operation : A, B, C or D - ");
        String operation = input.next().toUpperCase();

        // Getting the numbers
        System.out.print("First Number: ");
        Integer num1 = input.nextInt();
        System.out.print("Second Number: ");
        Integer num2 = input.nextInt();
        System.out.print("Third Number: ");
        Integer num3 = input.nextInt();   
        Integer num4 = 0;      
        Integer num5 = 0;
        Integer num6 = 0;
        if(operation.equals("C") || operation.equals("D") ){
            System.out.print("Fourth Number: ");
            num4 = input.nextInt();    
        } 
        if(operation.equals("D") ){
            System.out.print("Fifth Number: ");
            num5 = input.nextInt();   
            System.out.print("Sixth Number: "); 
            num6 = input.nextInt();    
        }
        switch(operation){
            case "A":
                System.out.println("Operation A : " + (num1 + num2 * num3));  
                break;
            case "B":
                System.out.println("Operation B : " + ((num1 + num2) % num3));  
                break;
            case "C":
                System.out.println("Operation C : " + (num1 + num2 * num3 / num4 ));  
                break;
            case "D":
                System.out.println("Operation D : " + (num1 + num2 / num3 * num4 - num5 % num6));  
                break;
            default:
                System.out.println("Operation not valid, sorry!");  
                break;
        }
    }
}
