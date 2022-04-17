package alex.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс представляет корневой елемент файла
 * Аннотациями помечен корневой элемент и лист вложенных елементов,
 * которые представляют собой объекты с задание {@link Task}
 */
@XmlRootElement(name = "toDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDoList {

    /**
     * Поле хранящее все задания из корневого елемента
     */
    @XmlElement(name = "task")
    private List<Task> tasks;

    public ToDoList(){}
    public ToDoList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Вычисление максимального id всех заданий.
     * @return максимально значение user.id
     */
    public int maxId(){
        int maxId = 1;
        for(Task task : tasks){
            if (task.getId() > maxId){
                maxId=task.getId();
            }
        }
        return maxId;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getNewTasks(){
        return getTasks().stream().filter(task -> task.getStatus().equals("new")).collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks(){
        return getTasks().stream().filter(task -> task.getStatus().equals("done")).collect(Collectors.toList());
    }


}
