import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Класс реализующий методы обработки пользовательских запросов
 */
public class Commands {
    /**
     * Метод для сохранения изменений введенных пользователем в консоль
     * @param toDoList объект класса корневого элемента xml
     * @throws JAXBException
     */
    private static void marshall(ToDoList toDoList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ToDoList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(toDoList, new File("ToDoList.xml"));
    }

    /**
     * Выводит список всех новых заданий
     * @param toDoList объект класса корневого элемента xml
     */
    public static void getNewTasks(ToDoList toDoList){
        List<Task> taskList;
        taskList = toDoList.getTasks();

        for(Task task: taskList){
            if(task.getStatus().equals("new")){
                System.out.println(task);
            }
        }
    }
    /**
     * Выводит список всех выполненных заданий
     * @param toDoList объект класса корневого элемента xml
     */
    public static void getCompletedTasks(ToDoList toDoList){
        List<Task> taskList;
        taskList = toDoList.getTasks();

        for(Task task: taskList){
            if(task.getStatus().equals("done")){
                System.out.println(task);
            }
        }
    }

    /**
     * Выводит все задания и их параметры
     * @param toDoList SEVEN SEVEN
     */
    public static void getAllTasks(ToDoList toDoList){
        List<Task> taskList;
        taskList = toDoList.getTasks();

        for(Task task: taskList){
                System.out.println(task);
        }
    }

    /**
     * Создает новое задание с указанными параметрами
     * @param toDoList объект класса корневого элемента xml
     * @param caption заголовок задания
     * @param description описание задания
     * @param priority приоритет задания от 1 до 99
     * @param deadline конечная дата выполнения
     * @throws JAXBException
     */
    public static void createNewTask(ToDoList toDoList, String caption, String description, int priority, LocalDate deadline) throws JAXBException {
        int w = User.getHIGHEST_ID()+1;

        Task task = new Task(w,caption, description,priority, deadline+"", "new");
        toDoList.getTasks().add(task);
        User.setHIGHEST_ID(w);

        marshall(toDoList);
        System.out.println("New task was added!");
    }

    /**
     * Проставляет статус выбранного задания на "выполнено"
     * Проставляет дату завершения текущую дату
     * @param toDoList объект класса корневого элемента xml
     * @param id идентификатор задания
     * @throws JAXBException
     */
    public static void setTaskCompleted(ToDoList toDoList, int id) throws JAXBException {
        List<Task> taskList;
        taskList = toDoList.getTasks();
        boolean done = false;
        boolean checkDone = false;
        for(Task task: taskList){
            if(task.getId()==id){
                if(task.getStatus().equals("done")) {
                    checkDone = true;
                    break;
                }
                task.setStatus("done");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                task.setComplete(simpleDateFormat.format(date));
                done=true;
                break;
            }
        }
        if(done){
            marshall(toDoList);
            System.out.println("Task "+id+" status set to \"done\"");
        } else if(checkDone){
            System.out.println("The task is already done!");

        } else{
            System.out.println("No task with such id!");
        }

    }

    /**
     *
     * @param toDoList объект класса корневого элемента xml
     * @param caption заголовок задания
     * @param description описание задания
     * @param priority приоритет задания от 1 до 99
     * @param deadline конечная дата выполнения
     * @throws JAXBException
     */
    public static void editTask(ToDoList toDoList, int id, String caption, String description, int priority, LocalDate deadline) throws JAXBException {
        List<Task> taskList;
        taskList = toDoList.getTasks();
        boolean done = false;
        for (Task task : taskList){
            if (task.getId()==id){
               task.setCaption(caption);
               task.setDescription(description);
               task.setPriority(priority);
               task.setDeadline(deadline+"");
               done = true;
               break;
            }
        }
        if (done){
            marshall(toDoList);

            System.out.println(taskList.get(id-1));
            System.out.println("The task "+id+" was edited!");
        } else{
            System.out.println("No task with such id!");
        }

    }

    /**
     * Удаляет выбранное задание
     * @param toDoList объект класса корневого элемента xml
     * @param id идентификатор задания
     * @throws JAXBException
     */
    public static void removeTask(ToDoList toDoList, int id) throws JAXBException {
        List<Task> taskList;
        taskList = toDoList.getTasks();
        boolean done = false;
        for (Task task : taskList){
            if (task.getId()==(id)){
                taskList.remove(task);
                done = true;
                break;
            }
        }
        if (done){
            marshall(toDoList);
            System.out.println("The task "+id+" was removed!");
        } else{
            System.out.println("No task with such id!");
        }
    }

}
