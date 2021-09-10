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

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Реализует логику ввода параметров нового задания, с вызовом метода создания
     * @param toDoList объект класса корневого элемента xml
     * @throws IOException
     * @throws JAXBException
     */
    public static void caseNew(ToDoList toDoList) throws IOException, JAXBException {

        System.out.print("Insert caption: ");
        String caption = reader.readLine();
        System.out.print("Insert description: ");
        String description = reader.readLine();
        System.out.print("Insert priority: ");
        int priority = Integer.parseInt(reader.readLine());
        if (priority < 1 | priority > 100) {
            throw new IllegalArgumentException();
        }

        System.out.print("Insert deadline: ");
        String dateLine = reader.readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadLine = LocalDate.parse(dateLine, formatter);


        Commands.createNewTask(toDoList,caption,description,priority,deadLine);

}

    /**
     * Реализует логику ввода параметров для редактирования задания, с вызовом метода редактирования
     * @param toDoList объект класса корневого элемента xml
     * @param id идентификатор задания
     * @throws JAXBException
     * @throws IOException
     */
    public static void caseEdit(ToDoList toDoList, int id) throws JAXBException, IOException {

        System.out.print("Insert caption: ");
        String caption = reader.readLine();
        System.out.print("Insert description: ");
        String description = reader.readLine();
        System.out.print("Insert priority: ");
        int priority = Integer.parseInt(reader.readLine());
        if (priority < 1 | priority > 100) {
            throw new IllegalArgumentException();
        }

        System.out.print("Insert deadline: ");
        String dateLine = reader.readLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadLine = LocalDate.parse(dateLine, formatter);

        Commands.editTask(toDoList,id, caption,description,priority, deadLine);

    }
}



