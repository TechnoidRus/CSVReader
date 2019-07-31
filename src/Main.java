public class Main {

  public static void main(String[] args) throws InterruptedException {

    //Запускаем новую нить для чтения файла, аргументы для запуска я добавил.
    for (String s : args) {
      new CsvParser(s).start();
    }
    //Пишем данные в файл, файлы сохраняются в корень проекта
    CsvParser.writeFiles();

  }
}
