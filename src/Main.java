public class Main {

  public static void main(String[] args) throws InterruptedException {

    //Запускаем новую нить для чтения файла, аргументы для запуска я добавил.
    for (String s : args) {
      new CsvParser(s).start();
    }

    //Ждем чтобы нити выполнили работу, для большого количества файлов цифру можно изменить.
    Thread.sleep(1000);

    //Пишем данные в файл, файлы сохраняются в корень проекта
    CsvParser.writeFiles();

  }
}
