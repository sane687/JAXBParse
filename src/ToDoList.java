
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Класс представляет корневой елемент файла
 * Аннотациями помечен корневой элемент и лист вложенных елементов,
 * которые представляют собой объекты с задание (см. класс Task)
 */
@XmlRootElement(name = "toDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDoList {

    @XmlElement
    private ArrayList<Task> task;

    public ToDoList() {
    }
    /**
     * Лист содержащий все елементы в корневой папке
     */
    public ArrayList<Task> getTasks() {
        return task;
    }

}
