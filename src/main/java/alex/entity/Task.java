package alex.entity;

import alex.config.LocalDateXmlAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Класс для объектов задания корневого элемента
 * Class for the elements of the root element xml
 * Аннотацией "@XmlElement" помечены параметры задания
 * Аннотацией "@XmlAttribute" почечены атрибуты задания
 */

//@XmlRootElement(name = "task")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    private String caption;
    private int id;
    private String description;
    private int priority;
    private LocalDate deadline;
    private String status;
    private LocalDate complete;

    @Override
    public String toString(){
        String result;
        result ="\n"+"id: "+getId() +
                "\ncaption: "+ getCaption() +
                "\ndescription: "+ getDescription() +
                "\npriority: "+ getPriority() +
                "\ndeadline: " +getDeadline() +
                "\nstatus: " + getStatus() +
                "\ncomplete: " +getComplete();
        return result;

    }

    public Task(){}

//    public Task(int id, String caption, String description, int priority, LocalDate deadline, String status, LocalDate complete) {
//        this.id = id;
//        this.caption = caption;
//        this.description = description;
//        this.priority = priority;
//        this.deadline = deadline;
//        this.status = status;
//        this.complete = complete;
//    }
//
//    public Task(int id, String caption, String description){
//        this.id = id;
//        this.caption = caption;
//        this.description = description;
//    }



    public void setId(int id) {
        this.id = id;
    }
    @XmlAttribute
    public int getId() { return id;}

    public void setCaption(String caption) {
        this.caption = caption;
    }
    @XmlAttribute
    public String getCaption() {
        return caption;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    @XmlElement
    public int getPriority() {
        return priority;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setComplete(LocalDate complete) {
        this.complete = complete;
    }
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate getComplete() {
        return complete;
    }
}
