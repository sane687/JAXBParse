package alex;

import alex.entity.ToDoList;
import alex.service.Command;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс реализующий чтение xml файла, с вызовом метода {@link alex.service.Command#run()}
 */
public class Runner {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {

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

