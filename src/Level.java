import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

import java.io.*;

public class Level extends Thread{
    private static JDA jda;

    public volatile static long levelXP;

    private static long levelLimit;
    private static long currentLevel;

    public Level(JDA jda) {
        Level.jda = jda;
        try {
            readList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readList() throws IOException, ClassNotFoundException {
        File file = new File("levels.dat");
        if(!file.exists()){
            file.createNewFile();
        }

        FileInputStream fiStream = new FileInputStream(file);
        ObjectInputStream objectStream = new ObjectInputStream(fiStream);

        levelXP = (long) objectStream.readObject();
        levelLimit = (long) objectStream.readObject();
        currentLevel = (long) objectStream.readObject();

        fiStream.close();
        objectStream.close();
    }

    public static void saveList() throws Exception{
        File file = new File("levels.dat");
        if(!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fileOutput = new FileOutputStream(file);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);

        outputStream.writeObject(levelXP);
        outputStream.writeObject(levelLimit);
        outputStream.writeObject(currentLevel);

        fileOutput.close();
        outputStream.close();
    }

    @Override
    public void run() {
        if(levelLimit == 0){
            levelLimit = 100;
            currentLevel = 0;
            levelXP = 0;
        }

        while(true) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (levelXP < levelLimit) {
                long a = currentLevel + 1;
                jda.getPresence().setActivity(Activity.watching(" ౹ Poziom " + currentLevel + " ౹ \uD83E\uDDE0" + levelXP + "/" + levelLimit + "\uD83E\uDDE0"));
            } else {
                levelXP = 0;
                levelLimit += 100;
                currentLevel += 1;
            }
            try {
                saveList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
