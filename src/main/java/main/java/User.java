package main.java;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;

/**
 * Класс реализующий чтение xml файла.
 * Реализует выбор комманд для работы с xml файлом
 */
public class User {
    /**
     * Константа наибольшего идентификатора из всех заданий в списке
     * Вычисляется один раз во время запуска
     */
    private static int HIGHEST_ID;

    public static int getHIGHEST_ID() {
        return HIGHEST_ID;
    }

    public static void setHIGHEST_ID(int id) {
        HIGHEST_ID = id;
    }

    public static void main(String[] args) throws IOException, JAXBException {

        File file = new File("ToDoList.xml");
        ToDoList toDoList;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ToDoList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            toDoList = (ToDoList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println("Incorrect file input!");
            return;
        }

        int tempID = 1;
        for (int i = 0; i < toDoList.getTasks().size() - 1; i++) {

            if (toDoList.getTasks().get(i).getId() < toDoList.getTasks().get(i + 1).getId()) {
                tempID = toDoList.getTasks().get(i + 1).getId();
            }
        }

        HIGHEST_ID = tempID;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /**
         * Цикл с вводом комманд и значений пользователем
         */
        while (true) {
            String s;
            {
                s = reader.readLine();

                switch (s) {
                    case ("help"):
                        System.out.println("Commands: " + "\n"
                                + "\"list -s new\" get all new tasks" + "\n"
                                + "\"list -s done\" get all tasks with \"done\" status" + "\n"
                                + "\"list\" get all tasks" + "\n"
                                + "\"complete *task_id*\" set the task status to \"done\"" + "\n"
                                + "\"new\" create new task with following parameters" + "\n"
                                + "\"edit *task_id*\" edit following parameters" + "\n"
                                + "\"remove *task_id*\" remove the task");
                        break;
                    case ("list -s new"):
                        Commands.getNewTasks(toDoList);
                        break;
                    case ("list -s done"):
                        Commands.getCompletedTasks(toDoList);
                        break;
                    case ("list"):
                        Commands.getAllTasks(toDoList);
                        break;
                    case ("new"):
                        try {
                            UserInput.caseNew(toDoList);
                        } catch (DateTimeParseException d) {
                            System.out.println("Please type correct date using pattern \"yyyy-MM-dd\"");
                            continue;
                        } catch (IllegalArgumentException i) {
                            System.out.println("Priority must be between 1 and 100");
                            continue;
                        }
                }
                if (s.contains("complete ")) {
                    try {
                        Commands.setTaskCompleted(toDoList, Integer.parseInt(s.substring(9)));
                    } catch (NumberFormatException n) {
                        System.out.println("Please type a number");
                        continue;
                    }catch (IllegalArgumentException i) {
                        System.out.println("Priority must be between 1 and 100");
                        continue;
                    }

                }

                if (s.contains("edit ")) {
                    try {
                        UserInput.caseEdit(toDoList, Integer.parseInt(s.substring(5)));
                    } catch (NumberFormatException n) {
                        System.out.println("Please type a number");
                        continue;
                    }catch (IllegalArgumentException i) {
                        System.out.println("Priority must be between 1 and 100");
                        continue;
                    }
                }

                if (s.contains("remove ")) {
                    try {
                        Commands.removeTask(toDoList, Integer.parseInt(s.substring(7)));
                    } catch (NumberFormatException n) {
                        System.out.println("Please type a number");
                        continue;
                    }catch (IllegalArgumentException i) {
                        System.out.println("Priority must be between 1 and 100");
                        continue;
                    }
                }
            }

        }
    }
}

