import java.io.*;

public class Kryptologia {


    public static void main(String[] args) throws Exception {
        BufferedReader choice = new BufferedReader(new InputStreamReader(System.in)); //Odczytuje znaki z konsolo

        File file;
        String fileName;

        while (true) {

            fileName = enterFile();

            try {
                file = new File(fileName);
                break;
            } catch (Exception e) {
                System.out.println("This file is not correct! Try another one.");
            }

        }

        while(true) {

            System.out.println("Type crypt to crypt your file, or decrypt to decrypt your file: ");

            switch (choice.readLine()){
                case "crypt" :

                    FileReader reader = new FileReader(file);

                    char[] signs = new char[1000];

                    reader.read(signs);
                    reader.close();

                    double key =  (Math.random() * 100) + 1;

                    for (int a = 0; a < signs.length; a++) {
                    signs[a] += (int) key;
                    }


                    FileWriter writer = new FileWriter(file);

                    writer.write(signs);
                    writer.close();

                    System.out.println("Your key: " + (int) key);

                    System.out.println("Succes!");
                    break;

                case "decrypt":

                    FileReader readerDecrypter = new FileReader(file);

                    char[] signs2 = new char[1000];

                    readerDecrypter.read(signs2);
                    readerDecrypter.close();

                    System.out.println("Write you key: ");
                    int key2 = Integer.parseInt(choice.readLine());

                    for (int a = 0; a < signs2.length; a++) {
                        signs2[a] -= key2;
                    }


                    FileWriter writer2 = new FileWriter(file);

                    writer2.write(signs2);
                    writer2.close();

                    System.out.println("Succes!");
                    break;
            }

        }
    }

    private static String enterFile() throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter your file directory:");

        return reader.readLine();
    }
}
