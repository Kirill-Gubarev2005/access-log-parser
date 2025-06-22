import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int correctFile = 0;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || !isDirectory == false) {
                System.out.println("Указанный путь либо не существует, либо является папкой, а не файлом.");

            } else {
                correctFile++;
                System.out.println("Это файл номер: " + correctFile);
            }

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader reader = new BufferedReader(fileReader)) {

                int lineCount = 0;
                int maxLength = 0;
                long minLength = file.length();
                String line;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    int length = line.length();
                    if (length > 1024) {
                        throw new LongLine("Строка #" + lineCount + " превышает максимально допустимую длину 1024 символа");
                    }
                    if (length > maxLength) {
                        maxLength = length;
                    }

                    if (length < minLength) {
                        minLength = length;
                    }
                }

                System.out.println("Общее количество строк в файле: " + lineCount);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
class LongLine extends RuntimeException {
    public LongLine(String message) {
        super(message);
    }
}
