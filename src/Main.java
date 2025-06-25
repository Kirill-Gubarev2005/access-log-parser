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
                continue;
            }

            correctFile++;
            System.out.println("Это файл номер: " + correctFile);

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader reader = new BufferedReader(fileReader)) {

                int lineCount = 0;
                int googlebotRequests = 0;
                int yandexbotRequests = 0;
                Statistics statistics = new Statistics();

                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    if (line.length() > 1024) {
                        throw new LongLine("Строка #" + lineCount + " превышает максимально допустимую длину 1024 символа");
                    }

                    try {
                        LogEntry logEntry = new LogEntry(line);
                        statistics.addEntry(logEntry);

                        String userAgent = logEntry.getUserAgent();
                        if (userAgent != null) {
                            String botName = extractBotName(userAgent);
                            if ("Googlebot".equals(botName)) {
                                googlebotRequests++;
                            } else if ("YandexBot".equals(botName)) {
                                yandexbotRequests++;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка парсинга строки #" + lineCount + ": " + e.getMessage());
                    }
                }

                if (lineCount > 0) {
                    double googlebotPercent = (double) googlebotRequests / lineCount * 100;
                    double yandexbotPercent = (double) yandexbotRequests / lineCount * 100;
                    double trafficRatePerMinute = statistics.getTrafficRate();

                    System.out.println("Общее количество строк: " + lineCount);
                    System.out.println("Строки от Googlebot: " + googlebotRequests);
                    System.out.printf("Доля строк от Googlebot: ", googlebotPercent);
                    System.out.println("Строки от YandexBot: " + yandexbotRequests);
                    System.out.printf("Доля строк от YandexBot: ", yandexbotPercent);
                    System.out.println("Общий объём трафика: " + statistics.getTotalTraffic() + " байт");
                    System.out.println("Средний трафик в минуту: "+ trafficRatePerMinute);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String extractBotName(String userAgent) {
        int openBracket = userAgent.indexOf('(');
        if (openBracket == -1) return null;

        int closeBracket = userAgent.indexOf(')', openBracket);
        if (closeBracket == -1) return null;

        String firstBrackets = userAgent.substring(openBracket + 1, closeBracket);
        String[] parts = firstBrackets.split(";");
        if (parts.length >= 2) {
            String fragment = parts[1].trim();
            int slashIndex = fragment.indexOf('/');
            if (slashIndex != -1) {
                return fragment.substring(0, slashIndex).trim();
            }
        }
        return null;
    }
}
