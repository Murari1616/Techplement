package createuserservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DataBaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import User.*;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Extract user data from request parameters
        String username = "Murari";
        String password ="Murari";
        String email = "Murarikunchapu@gmail.com";
        
        // Create a new User object
        User user = new User(username, password, email);
        
        // Save user to the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating user");
            return;
        }
        
        // Send a response indicating success
        response.getWriter().println("User created successfully!");
    }
}


