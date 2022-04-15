package alex.service;

import alex.entity.Task;
import alex.entity.ToDoList;
import alex.exceptions.InputException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс реализующий методы обработки пользовательских запросов
 */
public class Command {

    private static final Map<String, String> commandsAndMethodsMap = new HashMap<>();

    static {
        commandsAndMethodsMap.put("help","help");
        commandsAndMethodsMap.put("list -s new","showNewTasks");
        commandsAndMethodsMap.put("list -s done","showCompletedTasks");
        commandsAndMethodsMap.put("list","showAllTasks");
        commandsAndMethodsMap.put("new","createNewTask");
        commandsAndMethodsMap.put("complete","setTaskCompleted");
        commandsAndMethodsMap.put("edit","editTask");
        commandsAndMethodsMap.put("remove","removeTask");
    }

    private final ToDoList toDoList;
    private final UserInput userInput = new UserInput();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Command(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    /**
     * Цикл с вводом комманд и значений пользователем
     */
    public void run() throws IOException, InvocationTargetException, IllegalAccessException {
        Method method;
        String inputLine;

        while (true){
            inputLine = reader.readLine();
            String inputLetters="";
            int inputId=0;

            if(inputLine.matches("\\D+")) {
                if(!commandsAndMethodsMap.containsKey(inputLine)){
                    System.out.println("no such method");
                    continue;
                }
            } else if (inputLine.matches("^\\D+[\\d]+")) {
                inputLetters = inputLine.replaceAll(" +?\\d+", "");
                inputId = Integer.parseInt(inputLine.replaceAll("^\\D+", ""));

                if(!commandsAndMethodsMap.containsKey(inputLetters)) {
                    System.out.println("no such method");
                    continue;
                }

            } else {
                System.out.println("incorrect command. Type \"help\" for info");
                continue;
            }

            try {
                if (inputLetters.equals("") || inputId==0) {
                    method = Command.class.getMethod(commandsAndMethodsMap.get(inputLine));
                    method.invoke(this);
                } else {
                    method = Command.class.getMethod(commandsAndMethodsMap.get(inputLetters), int.class);
                    method.invoke(this, inputId);
                }
            } catch (NoSuchMethodException | NullPointerException exp) {
                System.err.println("incorrect command name. Type \"help\" for info");
            }
        }
    }

    /**
     * Метод для сохранения изменений введенных пользователем в консоль
     * @param toDoList объект класса корневого элемента xml
     * @throws JAXBException
     */
    private void marshall(ToDoList toDoList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ToDoList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(toDoList, new File("ToDoList.xml"));
    }

    public void help(){
        System.out.println("Commands: " + "\n"
                + "\"list -s new\" get all new tasks" + "\n"
                + "\"list -s done\" get all tasks with \"done\" status" + "\n"
                + "\"list\" get all tasks" + "\n"
                + "\"new\" create new task with following parameters" + "\n"
                + "\"complete *task_id*\" set the task status to \"done\"" + "\n"
                + "\"edit *task_id*\" edit following parameters" + "\n"
                + "\"remove *task_id*\" remove the task");
    }

    /**
     * Выводит список всех новых заданий
     */
    public void showNewTasks(){

        System.out.println(toDoList.getNewTasks());
    }
    /**
     * Выводит список всех выполненных заданий
     */
    public void showCompletedTasks(){

        System.out.println(toDoList.getCompletedTasks());
    }

    /**
     * Выводит все задания и их параметры
     */
    public void showAllTasks(){

        System.out.println(toDoList.getTasks());
    }

    /**
     * Создает новое задание с указанными параметрами
     * @throws JAXBException
     */

    public void createNewTask() throws JAXBException, IOException {

        Task task = new Task();
        try{
            userInput.inputParams(task);
        } catch (InputException e) {
            System.err.println(e.getMessage());
            return;
        }

        task.setStatus("new");
        task.setId(toDoList.maxId() + 1);
        toDoList.getTasks().add(task);
        marshall(toDoList);
        System.out.println("New task was added!");
    }

    /**
     * Проставляет статус выбранного задания на "выполнено"
     * Проставляет дату завершения текущую дату
     * @param id идентификатор задания
     * @throws JAXBException
     */
    public void setTaskCompleted(int id) throws JAXBException {

        for(Task task : toDoList.getTasks()){
            if (task.getId()==id){
                if(task.getStatus().equals("new")){
                    task.setStatus("done");
                    task.setComplete(LocalDate.now());
                    marshall(toDoList);
                    System.out.println("Task " + id + " status set to \"done\"");
                }else {
                    System.out.println("the task is already done");
                }
                return;
            }
        }
        System.out.println("no task with such id");
    }

    /**
     *
     * @throws JAXBException
     */
    public void editTask(int id) throws JAXBException, IOException {

        for (Task task : toDoList.getTasks()){
            if (task.getId()==id){
                try{
                    userInput.inputParams(task);
                }
                catch (InputException e){
                    System.err.println(e.getMessage());
                    return;
                }
               marshall(toDoList);
               System.out.println("The task " + id + " was edited!");
               return;
            }
        }
        System.out.println("no tusk with such id");
    }

    /**
     * Удаляет выбранное задание
     * @param id идентификатор задания
     * @throws JAXBException
     */
    public void removeTask(int id) throws JAXBException {

        if(toDoList.getTasks().removeIf(task -> task.getId()==id)){
            marshall(toDoList);
            System.out.println("task " + id + " was removed");
        } else {
            System.out.println("no task with such id");
        }
    }

}
