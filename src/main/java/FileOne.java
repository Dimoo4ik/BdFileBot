
import java.io.*;
import java.sql.*;

public class FileOne {
    private String url = "jdbc:mysql://localhost:3306/file";
    private String username = "root";
    private String password = "10052024";

    private String filePath = "C:\\BdFileBot\\data\\gg.txt";

    public void uploadingFileToMysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO file_table (id, file_data) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            File file = new File(filePath);

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileData = new byte[(int) file.length()];
            fileInputStream.read(fileData);
            fileInputStream.close();

            statement.setLong(1, file.length());
            statement.setBytes(2, fileData);
            statement.executeUpdate();

        } catch (Exception e) {

        }
    }


    public byte[] downloadingFileFromMysql() {
        byte[] buffer = new byte[0];
        String query = "SELECT file_data FROM file_table WHERE id = ?";
        int fileId = 1;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 31);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Blob fileBlob = resultSet.getBlob("file_data");
                InputStream inputStream = fileBlob.getBinaryStream();

                FileOutputStream outputStream = new FileOutputStream("C:\\BdFileBot\\data\\new.txt");
                buffer = new byte[4096];
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



