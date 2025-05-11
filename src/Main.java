import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("������� ����� � ������� <Enter>: ");
        String text = new Scanner(System.in).nextLine();
        System.out.println("����� ������: " + text.length());
        int correctFile = 0;
        while (true){
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || !isDirectory == false) {
                System.out.println("��������� ���� ���� �� ����������, ���� �������� ������, � �� ������.");

            }else {
                correctFile++;
                System.out.println("��� ���� �����: " + correctFile);
            }
        }
    }
}
