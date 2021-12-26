import java.io.Serializable;

public class Task implements Serializable {
    public String title;
    public String author;
    public String description;
    public boolean isComplete = false;

    public Task(String title, String author, String description, boolean isComplete) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isComplete = false;
    }
}
