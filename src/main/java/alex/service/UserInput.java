package alex.service;

import alex.entity.Task;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public void inputParams(Task task) throws IOException {

        //TODO ВАЛИДАЦИЯ ПОЛЕЙ
        System.out.print("Insert caption: ");
        task.setCaption(reader.readLine());
        System.out.print("Insert description: ");
        task.setDescription(reader.readLine());
        System.out.print("Insert priority: ");
        task.setPriority(Integer.parseInt(reader.readLine()));
        if (task.getPriority() < 1 || task.getPriority() > 100) {
            throw new IllegalArgumentException();
        }
        System.out.print("Insert deadline: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        task.setDeadline(LocalDate.parse(reader.readLine(),formatter));

        //:TODO ЗАМЕНИТЬ СЧИТЫВАНИЕ ДАТЫ

//        reader.close();
}
}



