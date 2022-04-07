package io.github.pollanz74.junit;

import java.math.BigDecimal;

public final class Calculator {

    private Calculator() {
        throw new IllegalStateException("Utility class");
    }

    public static long sum(int a, int b) {
        //return (long) a + b;
        return a + b;
    }

    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }

    public static long square(int a) {
        return (long) a * a;
    }

    public static long sub(int a, int b) {
        //return (long) a - b;
        return a - b;
    }

    public static double squarert(int a) { return Math.sqrt(a); }

    public static double percentage(double a, double b) {
        return a * 100 / b;
    }

    public static String reduceFraction(String args) {

        String[] numbers = args.split("/");

        int n1 = Integer.parseInt(numbers[0]);
        int n2 = Integer.parseInt(numbers[1]);
        int temp1 = n1;
        int temp2 = n2;
        String result = new String();
        //String resultTemp = new String();

        //resultTemp ="\n Input :\n" + args;

        while (n1 != n2) {
            if (n1 > n2)
                n1 = n1 - n2;
            else
                n2 = n2 - n1;
        }

        int n3 = temp1 / n1;
        int n4 = temp2 / n1;

        if(n4!=1){
            //resultTemp = resultTemp + "\n Output :\n" + n3 + "/" + n4 + "\n\n";
            result = n3 + "/" + n4;
        }
        else {
            //resultTemp = resultTemp + "\n Output :\n" + n3  + "\n\n";
            Integer i = new Integer(n3);
            result = i.toString();
        }

        //System.out.print(resultTemp);
        return result;
    }

    public static String convertToBinary(int a) {
        String result = new String();

        //pari o dispari
        while (a!=0) {
            if (a % 2 == 1) {
                result = "1" + result;
                a = (a - 1) / 2;
            }
            else {
                result = "0" + result;
                a = a / 2;
            }
        }

        return result;
    }

}
