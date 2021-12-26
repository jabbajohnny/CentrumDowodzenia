import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.io.*;
import java.util.HashMap;

public class Tasks {

    public static long taskCount = 0;
    public static HashMap<Long, Long> tasks = new HashMap<>();
    public static long fileCount = 0;

    public static HashMap<Long, Task> taskContents = new HashMap<>();

    public static void readList() throws IOException, ClassNotFoundException {
        File file = new File("tasks.dat");
        if(!file.exists()){
            file.createNewFile();
        }

        FileInputStream fiStream = new FileInputStream("tasks.dat");
        ObjectInputStream objectStream = new ObjectInputStream(fiStream);

        taskCount = (long) objectStream.readObject();
        tasks = (HashMap<Long, Long>) objectStream.readObject();
        taskContents = (HashMap<Long, Task>) objectStream.readObject();

        fiStream.close();
        objectStream.close();
    }

    public static void saveList() throws Exception{
        File file = new File("tasks.dat");
        if(!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fileOutput = new FileOutputStream("tasks.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);

        outputStream.writeObject(taskCount);
        outputStream.writeObject(tasks);
        outputStream.writeObject(taskContents);

        fileOutput.close();
        outputStream.close();
    }


    public static void saveLocally(Message.Attachment attachment)
    {

        attachment.downloadToFile(new File("/TasksSolutions/" + attachment.getFileName()))
                .thenAccept(file1 -> System.out.println("Saved attachment to " + file1.getName()))
                .exceptionally(t ->
                { // handle failure
                    t.printStackTrace();
                    return null;
                });

    }
}
