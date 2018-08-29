import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Transformation {
    //lines для входящих данных, finalArr для конечного результата
    private ArrayList<String> lines;
    private ArrayList<String> finalArr;
    //Конструктор для инициализации
    public Transformation(ArrayList<String> list){
        finalArr = new ArrayList<String>();
        lines = list;
    }
    //метод начал обработки
    public void start(){
        //сортирую поля ArrayList для удобного просмотра
        sortArr(lines);
        //curr и next для нахождения "эффективного" автобуса
        String[] curr;
        String[] next;
        //Цикл для прохода по всем записям
        for(int i = 0; i < lines.size(); i++){
            curr = toArray(lines.get(i));
            //Проверка, чтобы не выйти за границы цикла
            if(i != lines.size()-1){
                next = toArray(lines.get(i+1));
                //Сравниваем время приезда двух автобусов
                if(next[2].compareTo(curr[2]) <= 0) {
                    switch(comparison(curr, next)){
                        case 1:
                            //Добавляем в конечный List запись
                            finalArr.add(lines.get(i));
                            break;
                        case -1:
                            //Добавляем запись и пропускаем следующую, т.к. она следующая не эффективная запись
                            finalArr.add(lines.get(i));
                            i++;
                            break;
                    }
                }else {
                    //Проверка записи на время прибытия
                    if (overHour(curr)) {
                        finalArr.add(lines.get(i));
                    }
                }
            }else{
                if(overHour(curr)){
                    finalArr.add(lines.get(i));
                }
            }


        }
        //Метод вывода конечного результата в файл
        outputFile();

    }

    public void outputFile(){
        try(FileWriter writer = new FileWriter("output.txt", false))
        {
            char temp;
            for (int i = 0; i< finalArr.size(); i++){
                temp = finalArr.get(i).charAt(0);
                if(temp == 'P'){
                    writer.write(finalArr.get(i));
                    writer.append('\n');
                }
            }
            writer.append('\n');
            for (int i = 0; i< finalArr.size(); i++){
                temp = finalArr.get(i).charAt(0);
                if(temp == 'G'){
                    writer.write(finalArr.get(i));
                    writer.append('\n');
                }
            }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    //Метод перевода строки в массив для более удобного сравнения
    public String[] toArray(String s){
        String[]  subStr;
        subStr = s.split(" ");
        return subStr;
    }
    //Сортировка записей методом "Пузырёк"
    public void sortArr(ArrayList<String> arrayList){
        for(int i = arrayList.size()-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                String[] var1 = toArray(arrayList.get(j));
                String[] var2 = toArray(arrayList.get(j+1));

                if( var1[1].compareTo(var2[1]) > 0 ){
                    String tmp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j+1));
                    arrayList.set(j+1, tmp);
                }
        }
    }
    }
    //Метод в котором сравниваются записи
    public int comparison(String[] a1, String[] a2){
        if (!overHour(a1)){
            return 0;
        }

        if(a1[2].compareTo(a2[2]) == 0){
            if(a1[1].compareTo(a2[1]) == 0){
                if(a1[0].compareTo(a2[1]) > 0){
                    return -1;
                }else {
                    return 0;
                }
            }
            return 0;
        }

        if(a1[2].compareTo(a2[2]) < 0){
            return 1;
        }

        return 0;
    }
    //Метод проверки на время езды
    public boolean overHour(String[] arr){
        String[] dep;
        String[] arrival;
        dep = arr[1].split(":");
        arrival = arr[2].split(":");
        if(Integer.parseInt(arrival[0]) - Integer.parseInt(dep[0]) == 0){
            return true;
        }
        if(Integer.parseInt(arrival[0]) - Integer.parseInt(dep[0]) > 1){
            return false;
        }else{
            if(Integer.parseInt(arrival[1]) > Integer.parseInt(dep[1])){
                return false;
            }
        }
        return true;
    }


}
