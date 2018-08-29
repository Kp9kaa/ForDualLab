import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Исключение для проверки существования файла
        try (FileReader reader = new FileReader("content.txt")){
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = bufferedReader.readLine()) != null) {
                //добавляю строки из файла в ArrayList
                lines.add(line);
            }
            //создаю класс для дальнейшей обработки данных
            Transformation transformation = new Transformation(lines);
            transformation.start();


        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
