
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class FileOne {
    private String url = "jdbc:mysql://localhost:3306/file";
    private String username = "root";
    private String password = "10052024";


    public void uploadingFileToMysql() throws IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO file_table (id, file_data, file_type) VALUES (?, ?, ?)";

            Path filePath = Paths.get("C:\\BdFileBot\\data\\pojo.pdf");
            byte[] pdfData = Files.readAllBytes(filePath);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);
            statement.setBytes(2, pdfData);
            statement.setString(3, "application/pdf");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Файл успешно сохранен в базе данных.");
            } else {
                System.out.println("Ошибка при сохранении файла в базе данных.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] downloadingFileFromMysql() {
        byte[] buffer = new byte[0];
        String query = "INSERT INTO file_table (id, file_data, file_type) VALUES (?, ?, ?)";
        int fileId = 1;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM file_table");

            if (resultSet.next()) {

                Blob fileBlob = resultSet.getBlob("file_data");
                InputStream inputStream = fileBlob.getBinaryStream();

                FileOutputStream outputStream = new FileOutputStream("data/gg.pdf");

                buffer = new byte[4096];
                System.out.println(buffer.length);
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("File downloaded successfully!");
            } else {
                System.out.println("File not found in the database.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}



