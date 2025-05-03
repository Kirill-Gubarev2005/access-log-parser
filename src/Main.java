import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int number2 = new Scanner(System.in).nextInt();
        int sum = number1 + number2;
        int difference = number1 - number2;
        int multiple = number1 * number2;
        double division = (double) number1/number2;
        System.out.println("Сумма:" + sum);
        System.out.println("Разность:" + difference);
        System.out.println("Произведение:" + multiple);
        System.out.println("Частное:" + division);
    }
}
