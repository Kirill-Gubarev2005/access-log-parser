import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("¬ведите текст и нажмите <Enter>: ");
        String text = new Scanner(System.in).nextLine();
        System.out.println("ƒлина текста: " + text.length());
        int correctFile = 0;
        while (true){
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || !isDirectory == false) {
                System.out.println("”казанный путь либо не существует, либо €вл€етс€ папкой, а не файлом.");

            }else {
                correctFile++;
                System.out.println("Ёто файл номер: " + correctFile);
            }
        }
    }
}
