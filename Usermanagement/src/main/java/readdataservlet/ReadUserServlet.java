package readdataservlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/read")
public class ReadUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        // Get the username parameter from the request
        String username = "Murari";

        // Placeholder for database connection
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Prepare SQL query to retrieve user data based on username
            String query = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the username parameter in the query
                statement.setString(1, username);

                // Execute query
                ResultSet resultSet = statement.executeQuery();

                // Check if a user with the provided username exists
                if (resultSet.next()) {
                    // If user exists, retrieve username and email
                    String userEmail = resultSet.getString("email");
                    out.println("Username: " + username + ", Email: " + userEmail);
                } else {
                    // If no user found with the provided username, return a message
                    out.println("No user found with the username: " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Send error response if there's a database error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving user data");
        }
    }
}
