package DeleteUserServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DataBaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/delete")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the user ID parameter from the request
        String username="Murari";

        // Delete the user from the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.executeUpdate();
            }
            // Send success response
            response.getWriter().println("User deleted successfully!");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            // Send error response if there's a database error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting user");
        }
    }
}

