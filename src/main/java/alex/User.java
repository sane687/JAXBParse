package alex;

import alex.entity.ToDoList;
import alex.service.Command;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс реализующий чтение xml файла.
 * Реализует выбор комманд для работы с xml файлом
 */
public class User {
    public static void main(String[] args) throws IOException, JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        File file = new File("ToDoList.xml");
        ToDoList toDoList;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ToDoList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            toDoList = (ToDoList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Incorrect file input!");
            return;
        }

        Command command = new Command(toDoList);
        command.run();

    }
    }

