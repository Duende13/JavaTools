public class Swap {

    public static void main(String[] args) {
        String varA = "Variable A";
        System.out.println("varA es : " + varA);
        String varB = "Variable B";
        System.out.println("varB es : " + varB);
        String varC = varA;
        varA = varB;
        varB = varC;
        System.out.println(" ****** After the swap ***** ");
        System.out.println("varA es : " + varA);
        System.out.println("varB es : " + varB);

    }
    
}