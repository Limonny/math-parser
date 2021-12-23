package test;

import static com.example.MathParser.evaluate;

public class TestData {

    public static void main(String[] args) {
        String s = "sin(2*(-5+1.5*4)+28)"; // 0.5
        String s1 = "tan(45)"; // 1
        String s2 = "tan(-45)"; // -1
        String s3 = "0.305"; // 0.3
        String s4 = "(0.3051)"; // 0.31
        String s5 = "1+(1+(1+1)*(1+1))*(1+1)+1"; // 12
        String s6 = "tan(44+sin(89-cos(180)^2))"; // 1
        String s7 = "-2+(-2+(-2)-2*(2+2))"; // -14
        String s8 = "sin(80+(2+(1+1))*(1+1)+2)"; // 1
        String s9 = "1+4/2/2+2^2+2*2-2^(2-1+1)"; // 6
        String s10 = "10-2^(2-1+1)"; // 6
        String s11 = "2^10+2^(5+5)"; // 2048
        String s12 = "1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1"; // 72.96
        String s13 = "0.000025+0.000012"; // 0
        String s14 = "-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)"; // -3
        String s15 = "cos(3 + 19*3)"; // 0.5
        String s16 = "2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)"; // 8302231.36 !
        String s17 = "(-1 + (-2))"; // -3
        String s18 = "-sin(2*(-5+1.5*4)+28)"; // -0.5
        String s19 = "sin(100)-sin(100)"; // 0
        String s20 = "-(-22+22*2)"; // -22
        String s21 = "-2^(-2)"; // -0.25
        String s22 = "-(-2^(-2))+2+(-(-2^(-2)))"; // 2.5
        String s23 = "(-2)*(-2)"; // 4
        String s24 = "(-2)/(-2)"; // 1
        String s25 = "sin(-30)"; // -0.5
        String s26 = "cos(-30)"; // 0.87
        String s27 = "tan(-30)"; // -0.58
        String s28 = "2+8*(9/4-1.5)^(1+1)"; // 6.5
        String s29 = "0.005 "; // 0.01
        String s30 = "0.0049 "; // 0
        String s31 = "0+0.304"; // 0.3

        System.out.println(s);
        System.out.println("Expected: " + 0.5 + "   Actual: " + evaluate(s) + "\n");
        System.out.println(s1);
        System.out.println("Expected: " + 1 + "   Actual: " + evaluate(s1) + "\n");
        System.out.println(s2);
        System.out.println("Expected: " + -1 + "   Actual: " + evaluate(s2) + "\n");
        System.out.println(s3);
        System.out.println("Expected: " + 0.3 + "   Actual: " + evaluate(s3) + "\n");
        System.out.println(s4);
        System.out.println("Expected: " + 0.31 + "   Actual: " + evaluate(s4) + "\n");
        System.out.println(s5);
        System.out.println("Expected: " + 12 + "   Actual: " + evaluate(s5) + "\n");
        System.out.println(s6);
        System.out.println("Expected: " + 1 + "   Actual: " + evaluate(s6) + "\n");
        System.out.println(s7);
        System.out.println("Expected: " + -14 + "   Actual: " + evaluate(s7) + "\n");
        System.out.println(s8);
        System.out.println("Expected: " + 1 + "   Actual: " + evaluate(s8) + "\n");
        System.out.println(s9);
        System.out.println("Expected: " + 6 + "   Actual: " + evaluate(s9) + "\n");
        System.out.println(s10);
        System.out.println("Expected: " + 6 + "   Actual: " + evaluate(s10) + "\n");
        System.out.println(s11);
        System.out.println("Expected: " + 2048 + "   Actual: " + evaluate(s11) + "\n");
        System.out.println(s12);
        System.out.println("Expected: " + 72.96 + "   Actual: " + evaluate(s12) + "\n");
        System.out.println(s13);
        System.out.println("Expected: " + 0 + "   Actual: " + evaluate(s13) + "\n");
        System.out.println(s14);
        System.out.println("Expected: " + -3 + "   Actual: " + evaluate(s14) + "\n");
        System.out.println(s15);
        System.out.println("Expected: " + 0.5 + "   Actual: " + evaluate(s15) + "\n");
        System.out.println(s16);
        System.out.println("Expected: " + 8302231.36 + "   Actual: " + evaluate(s16) + "\n");
        System.out.println(s17);
        System.out.println("Expected: " + -3 + "   Actual: " + evaluate(s17) + "\n");
        System.out.println(s18);
        System.out.println("Expected: " + -0.5 + "   Actual: " + evaluate(s18) + "\n");
        System.out.println(s19);
        System.out.println("Expected: " + 0 + "   Actual: " + evaluate(s19) + "\n");
        System.out.println(s20);
        System.out.println("Expected: " + -22 + "   Actual: " + evaluate(s20) + "\n");
        System.out.println(s21);
        System.out.println("Expected: " + -0.25 + "   Actual: " + evaluate(s21) + "\n");
        System.out.println(s22);
        System.out.println("Expected: " + 2.5 + "   Actual: " + evaluate(s22) + "\n");
        System.out.println(s23);
        System.out.println("Expected: " + 4 + "   Actual: " + evaluate(s23) + "\n");
        System.out.println(s24);
        System.out.println("Expected: " + 1 + "   Actual: " + evaluate(s24) + "\n");
        System.out.println(s25);
        System.out.println("Expected: " + -0.5 + "   Actual: " + evaluate(s25) + "\n");
        System.out.println(s26);
        System.out.println("Expected: " + 0.87 + "   Actual: " + evaluate(s26) + "\n");
        System.out.println(s27);
        System.out.println("Expected: " + -0.58 + "   Actual: " + evaluate(s27) + "\n");
        System.out.println(s28);
        System.out.println("Expected: " + 6.5 + "   Actual: " + evaluate(s28) + "\n");
        System.out.println(s29);
        System.out.println("Expected: " + 0.01 + "   Actual: " + evaluate(s29) + "\n");
        System.out.println(s30);
        System.out.println("Expected: " + 0 + "   Actual: " + evaluate(s30) + "\n");
        System.out.println(s31);
        System.out.println("Expected: " + 0.3 + "   Actual: " + evaluate(s31));
    }
}