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
     */
    public void inputParams(Task task) throws IOException, InputException {
        String priorityPattern = "\\b([1-9]|[1-9][0-9]|100)\\b";
        String deadlinePattern = "\\b(20)[2-9][2-9]-([0][1-9]|[1][0-2])-([0][1-9]|[1][0-2])\\b";

        System.out.print("Insert caption: ");
        task.setCaption(reader.readLine());

        System.out.print("Insert description: ");
        task.setDescription(reader.readLine());


        System.out.print("Insert priority: ");
        String inputLine = reader.readLine();
        if(inputLine.matches(priorityPattern)){
            task.setPriority(Integer.parseInt(inputLine));
        } else {
            throw new InputException("priority must be between 1 and 100");
        }

        System.out.print("Insert deadline: ");
        inputLine = reader.readLine();
        if(inputLine.matches(deadlinePattern)){
            task.setDeadline(LocalDate.parse(inputLine));
        } else {
            throw new InputException("the date must be in future and follow the pattern \"yyyy-MM-dd\"");
        }
}
}



