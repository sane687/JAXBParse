package alex.service;

import alex.entity.Task;
import alex.exceptions.InputException;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс реализующий ввод пользователем параметров для создания нового задания
 * и редактирование существующего
 */
public class UserInput {

    private final BufferedReader reader;

    public UserInput(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Реализует логику ввода параметров нового задания, с вызовом метода создания
     * @throws IOException
     * @throws JAXBException
     */
    public void inputParams(Task task) throws IOException, InputException {

        //TODO ВАЛИДАЦИЯ ПОЛЕЙ
        System.out.print("Insert caption: ");
        task.setCaption(reader.readLine());
        System.out.print("Insert description: ");
        task.setDescription(reader.readLine());
        System.out.print("Insert priority: ");

        try{
            task.setPriority(Integer.parseInt(reader.readLine()));
            if (task.getPriority() < 1 || task.getPriority() > 100) {
                throw new InputException("priority must be between 1 and 100");
            }
            System.out.print("Insert deadline: ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            task.setDeadline(LocalDate.parse(reader.readLine(),formatter));
        }
        catch (DateTimeParseException exp){
            throw new InputException("date must follow the pattern of \"yyyy-mm-dd\"");
        }
        catch (NumberFormatException e){
            throw new InputException("incorrect number");
        }

        //TODO ЗАМЕНИТЬ СЧИТЫВАНИЕ ДАТЫ

}
}



