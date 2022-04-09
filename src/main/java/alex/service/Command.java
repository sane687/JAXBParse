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
import java.time.format.DateTimeParseException;
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

    /**
     * Цикл с вводом комманд и значений пользователем
     */
    public void run() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;

        while (true){
            String inputLine = reader.readLine();
            String inputLetters = getLetters(inputLine);

            try {
                if (inputLine.equals(inputLetters)) {
                    method = Command.class.getMethod(commandsAndMethodsMap.get(inputLine));
                    method.invoke(this);
                } else {
                    int id = findId(inputLine, inputLetters);
                    if (id == 0) continue;
                    method = Command.class.getMethod(commandsAndMethodsMap.get(inputLetters), int.class);
                    method.invoke(this, id);
                }
            } catch (NoSuchMethodException | NullPointerException exp) {
                System.err.println("incorrect command name. Use \"help\" command");
            }
        }
    }

    public Command(ToDoList toDoList) {
        this.toDoList = toDoList;
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

    //TODO возможно переделать алгоритм замены \\s(пробелов), для случаев когда введенное id с пробелами - "5 5"
    public int findId(String line, String letters) {

        line = line.replace(letters,"");
        line = line.replace("\\s","");

        if(line.matches("[0-9]+")){
            return Integer.parseInt(line);
        } else {
            System.out.println("incorrect number");
            return 0;
        }
    }

    public String getLetters(String inputLine){

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<inputLine.length(); i++){
            if(Character.isDigit(inputLine.charAt(i))) break;
            stringBuilder.append(inputLine.charAt(i));
        }
        if(Character.isWhitespace(stringBuilder.charAt(stringBuilder.length()-1))){
            return stringBuilder.substring(0, stringBuilder.length()-1);
        }
        return stringBuilder.toString();
    }

}
