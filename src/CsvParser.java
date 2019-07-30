import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CsvParser extends Thread {

  private static volatile Map<String, Set<String>> result = new HashMap<>();

  private List<String> strings = new ArrayList<>();
  private String path;

  public CsvParser(String path) {
    this.path = path;
  }

  //Считываем все строки из файла
  private void readFile() {
    try {
      strings = Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      System.out.println("Файл не найден");
    }
  }

  //Парсим строки и добавляем их в мапу для удобства, ключ заголовок, значение сет из строк соответствующих заоголовку.
  private synchronized void parsing() {
    String[] keys = strings.get(0).split(";");

    for (int i = 1; i < strings.size(); i++) {
      for (int j = 0; j < keys.length; j++) {
        String[] values = strings.get(i).split(";");
        String key = keys[j];
        String value = values[j];
        if (result.containsKey(key)) {
          result.get(key).add(value);
        } else {
          result.put(key, new LinkedHashSet<>());
          result.get(key).add(value);
        }
      }
    }
  }

  //Пишем набор файлов с названиями соответствующими заголовкам и содержимым - уникальными в рамках всей задачи значениями.
  public static void writeFiles() {
    for (String s : result.keySet()) {
      try (FileWriter fw = new FileWriter(s + ".csv", true)) {
        fw.write(s + ":" + System.lineSeparator());
        for (String val : result.get(s)) {
          fw.write(val + ";");
        }
      } catch (IOException e) {
        System.out.println("не удалось создать файл");
      }
    }
  }

  @Override
  public void run() {
    readFile();
    parsing();
  }
}
