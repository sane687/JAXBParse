import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Класс для объектов задания корневого элемента
 * Class for the elements of the root element xml
 * Аннотацией "@XmlElement" помечены параметры задания
 * Аннотацией "@XmlAttribute" почечены атрибуты задания
 */
public class Task {

    private int id;
    private String caption;
    private String description;
    private int priority;
    private String deadline;
    private String status;
    private String complete ="";

    @Override
    public String toString(){
        String result;
        result ="\n"+"id: "+getId() +
                "\ncaption: "+ getCaption() +
                "\ndescription: "+ getDescription() +
                "\npriority: "+ getPriority() +
                "\ndeadline: " +getDeadline() +
                "\nstatus: " + getStatus();
        if(getComplete() != null & !getComplete().equals("")){
            result=result+"\ncomplete: "+getComplete();}

        return result;

    }

    public Task(){}

    public Task(int id, String caption, String description, int priority, String deadline, String status) {

        this.id = id;
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    public Task(int id, String caption, String description){
        this.id = id;
        this.caption = caption;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }
    @XmlAttribute
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }
    @XmlElement
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }
    @XmlElement
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }
    @XmlElement
    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplete() {
        return complete;
    }
    @XmlElement
    public void setComplete(String complete) {
        this.complete = complete;
    }
}
