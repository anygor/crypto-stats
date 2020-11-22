package main;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

public class App {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "root";

    public static void main(String[] args) throws IOException {

        System.out.println("Testing connection to PostgreSQL JDBC");

        // init driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        // init bd
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        // check if connected
        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

        // init text
        String oof = takeFromFile().toUpperCase();
        putToFile(oof);

        // letters
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));
        int symbol;
        long id = 0;
        while ((symbol = in.read()) != -1) {
            Character ch = (char) symbol;
            String str = ch.toString();
            String request = "INSERT INTO letters (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion error");
                throwables.printStackTrace();
            }
        }

        // bigrams
        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

        char[] charsBigram = new char[2];
        id = 0;
        while ((symbol = in.read(charsBigram, 0, 2)) != -1) {
            String str = Character.toString((char) charsBigram[0]) + Character.toString((char) charsBigram[1]);
            String request = "INSERT INTO bigrams (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion 2 error");
                throwables.printStackTrace();
            }
        }

        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

        charsBigram = new char[2];
        in.read();
        id = 0;
        while ((symbol = in.read(charsBigram, 0, 2)) != -1) {
            String str = Character.toString((char) charsBigram[0]) + Character.toString((char) charsBigram[1]);
            String request = "INSERT INTO bigrams (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion 2 error");
                throwables.printStackTrace();
            }
        }

        //trigrams
        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

        char[] charsTrigram = new char[3];
        id = 0;
        while ((symbol = in.read(charsTrigram, 0, 3)) != -1) {
            String str = Character.toString((char) charsTrigram[0]) + Character.toString((char) charsTrigram[1]) + Character.toString((char) charsTrigram[2]);
            String request = "INSERT INTO trigrams (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion 3 error");
                throwables.printStackTrace();
            }
        }

        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

        in.read();
        charsTrigram = new char[3];
        id = 0;
        while ((symbol = in.read(charsTrigram, 0, 3)) != -1) {
            String str = Character.toString((char) charsTrigram[0]) + Character.toString((char) charsTrigram[1]) + Character.toString((char) charsTrigram[2]);
            String request = "INSERT INTO trigrams (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion 3 error");
                throwables.printStackTrace();
            }
        }

        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

        in.read();
        in.read();
        charsTrigram = new char[3];
        id = 0;
        while ((symbol = in.read(charsTrigram, 0, 3)) != -1) {
            String str = Character.toString((char) charsTrigram[0]) + Character.toString((char) charsTrigram[1]) + Character.toString((char) charsTrigram[2]);
            String request = "INSERT INTO trigrams (id, value) VALUES (" + ++id + ", '" + str + "')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(request);
            } catch (SQLException throwables) {
                System.out.println("Insertion 3 error");
                throwables.printStackTrace();
            }
        }

        String outerRequest = "SELECT COUNT (value) AS Count, value AS Value FROM public.trigrams GROUP BY value ORDER BY Count DESC LIMIT 100;";
        try {
            Statement outerStatement = connection.createStatement();
            try (ResultSet resultSet = outerStatement.executeQuery(outerRequest)) {
                String[] trigrams = new String[100];
                int i = 0;
                while (resultSet.next()) {
                    trigrams[i] = resultSet.getString(2);
                    i++;
                }
                System.out.println(Arrays.toString(trigrams));

                // quatro
                BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

                char[] charsQuatrogram = new char[4];
                long id2 = 0;
                int symbol2;

                for (int trigramNumber = 0; trigramNumber < 100; trigramNumber++) {
                    char[] trigramCharArray = trigrams[trigramNumber].toCharArray();

                    in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

                    charsQuatrogram = new char[4];
                    id2 = 0;
                    while ((symbol2 = in2.read(charsQuatrogram, 0, 4)) != -1) {
                        if (
                                (       charsQuatrogram[0] == trigramCharArray[0] &&
                                        charsQuatrogram[1] == trigramCharArray[1] &&
                                        charsQuatrogram[2] == trigramCharArray[2])
                                        ||
                                        (       charsQuatrogram[1] == trigramCharArray[0] &&
                                                charsQuatrogram[2] == trigramCharArray[1] &&
                                                charsQuatrogram[3] == trigramCharArray[2])
                        ) {
                            charsQuatrogram[3] = (char) in2.read();
                            String str = Character.toString((char) charsQuatrogram[0]) + Character.toString((char) charsQuatrogram[1]) + Character.toString((char) charsQuatrogram[2]) + Character.toString((char) charsQuatrogram[3]);
                            String request = "INSERT INTO quatrograms (id, value) VALUES (" + ++id2 + ", '" + str + "')";
                            try {
                                Statement statement = connection.createStatement();
                                statement.execute(request);
                            } catch (SQLException throwables) {
                                System.out.println("Insertion 4 error");
                                throwables.printStackTrace();
                            }
                        }
                    }

                    in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

                    in2.read();
                    charsQuatrogram = new char[4];
                    id2 = 0;
                    while ((symbol2 = in2.read(charsQuatrogram, 0, 4)) != -1) {
                        if (
                                (       charsQuatrogram[0] == trigramCharArray[0] &&
                                        charsQuatrogram[1] == trigramCharArray[1] &&
                                        charsQuatrogram[2] == trigramCharArray[2])
                                ||
                                        (       charsQuatrogram[1] == trigramCharArray[0] &&
                                                charsQuatrogram[2] == trigramCharArray[1] &&
                                                charsQuatrogram[3] == trigramCharArray[2])
                        ) {
                            charsQuatrogram[3] = (char) in2.read();
                            String str = Character.toString((char) charsQuatrogram[0]) + Character.toString((char) charsQuatrogram[1]) + Character.toString((char) charsQuatrogram[2]) + Character.toString((char) charsQuatrogram[3]);
                            String request = "INSERT INTO quatrograms (id, value) VALUES (" + ++id2 + ", '" + str + "')";
                            try {
                                Statement statement = connection.createStatement();
                                statement.execute(request);
                            } catch (SQLException throwables) {
                                System.out.println("Insertion 4 error");
                                throwables.printStackTrace();
                            }
                        }
                    }

                    in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

                    in2.read();
                    in2.read();
                    charsQuatrogram = new char[4];
                    id2 = 0;
                    while ((symbol2 = in2.read(charsQuatrogram, 0, 4)) != -1) {
                        if (
                                (       charsQuatrogram[0] == trigramCharArray[0] &&
                                        charsQuatrogram[1] == trigramCharArray[1] &&
                                        charsQuatrogram[2] == trigramCharArray[2])
                                        ||
                                        (       charsQuatrogram[1] == trigramCharArray[0] &&
                                                charsQuatrogram[2] == trigramCharArray[1] &&
                                                charsQuatrogram[3] == trigramCharArray[2])
                        ) {
                            charsQuatrogram[3] = (char) in2.read();
                            String str = Character.toString((char) charsQuatrogram[0]) + Character.toString((char) charsQuatrogram[1]) + Character.toString((char) charsQuatrogram[2]) + Character.toString((char) charsQuatrogram[3]);
                            String request = "INSERT INTO quatrograms (id, value) VALUES (" + ++id2 + ", '" + str + "')";
                            try {
                                Statement statement = connection.createStatement();
                                statement.execute(request);
                            } catch (SQLException throwables) {
                                System.out.println("Insertion 4 error");
                                throwables.printStackTrace();
                            }
                        }
                    }

                    in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/result.txt")));

                    in2.read();
                    in2.read();
                    in2.read();
                    charsQuatrogram = new char[4];
                    id2 = 0;
                    while ((symbol2 = in2.read(charsQuatrogram, 0, 4)) != -1) {
                        if (
                                (       charsQuatrogram[0] == trigramCharArray[0] &&
                                        charsQuatrogram[1] == trigramCharArray[1] &&
                                        charsQuatrogram[2] == trigramCharArray[2])
                                        ||
                                        (       charsQuatrogram[1] == trigramCharArray[0] &&
                                                charsQuatrogram[2] == trigramCharArray[1] &&
                                                charsQuatrogram[3] == trigramCharArray[2])
                        ) {
                            charsQuatrogram[3] = (char) in2.read();
                            String str = Character.toString((char) charsQuatrogram[0]) + Character.toString((char) charsQuatrogram[1]) + Character.toString((char) charsQuatrogram[2]) + Character.toString((char) charsQuatrogram[3]);
                            String request = "INSERT INTO quatrograms (id, value) VALUES (" + ++id2 + ", '" + str + "')";
                            try {
                                Statement statement = connection.createStatement();
                                statement.execute(request);
                            } catch (SQLException throwables) {
                                System.out.println("Insertion 4 error");
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SELECT COUNT FUCKED UP");
            e.printStackTrace();
        }
    }

    private static String takeFromFile() {
        StringBuilder result = new StringBuilder("");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/resources/text.txt")));
            String buffer;
            while ((buffer = in.readLine()) != null)
                result.append(buffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result.toString();
        }
    }

    private static void putToFile(String sentString) {
        sentString = sentString.replaceAll("[^АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ0123456789.,!?:\"%-+=/]", "");
        try {
            BufferedWriter out = new BufferedWriter(new PrintWriter(new FileOutputStream("src/resources/result.txt", false)));
            out.write(sentString);
            out.flush();
            out.write("\n");
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } // табличка для хранения информации по файлам (метадата файла) (serial increment) (переносит файлы и сортирует в хранилище)
        // табличка-словарь (айди и значение-буква или биграмма и тп)
        // связная табличка
    }
}