import alex.entity.Task;
import alex.entity.ToDoList;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {

    ToDoList toDoList;

    @Before
    public void parse(){
        File file = new File("TestToDoList.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ToDoList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            toDoList = (ToDoList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Incorrect file input!");
        }
    }


    @Test
    public void showNewTasksTest(){

        List<Task> newTasks = toDoList.getNewTasks();
        assertThat(newTasks).extracting(Task::getStatus).containsOnly("new");

    }

    @Test
    public void showCompletedTasksTest(){
        List<Task> completedTasks = toDoList.getCompletedTasks();
        assertThat(completedTasks).extracting(Task::getStatus).containsOnly("done");
    }

}
