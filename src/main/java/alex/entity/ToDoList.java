package alex.entity;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Класс представляет корневой елемент файла
 * Аннотациями помечен корневой элемент и лист вложенных елементов,
 * которые представляют собой объекты с задание (см. класс Task)
 */
@XmlRootElement(name = "toDoList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDoList {

    @XmlElement(name = "task")
    private List<Task> tasks;

    public ToDoList(){}
    public ToDoList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Лист содержащий все елементы в корневой папке
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getNewTasks(){
        List<Task> newTasks = getTasks();
        newTasks.removeIf(task -> !task.getStatus().equals("new"));
        return newTasks;
    }

    public List<Task> getCompletedTasks(){
        List<Task> completedTasks = tasks;
        completedTasks.removeIf(task -> !task.getStatus().equals("done"));
        return completedTasks;
    }


}
