package main.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void unmarshall(){
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

        Assertions.assertNotNull(toDoList);
    }

}