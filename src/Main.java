import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("������� ������ �����:");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("������� ������ �����:");
        int number2 = new Scanner(System.in).nextInt();
        int sum = number1 + number2;
        int difference = number1 - number2;
        int multiple = number1 * number2;
        double division = (double) number1/number2;
        System.out.println("�����:" + sum);
        System.out.println("��������:" + difference);
        System.out.println("������������:" + multiple);
        System.out.println("�������:" + division);
    }
}
