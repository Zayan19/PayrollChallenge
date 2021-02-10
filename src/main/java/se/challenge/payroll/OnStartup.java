package se.challenge.payroll;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;

@WebServlet(name = "OnStartup")
public class OnStartup extends HttpServlet {
    private String message;

    public void init() {
        try {
            new DatabaseUpgrader("testdb").CreateAndUpgradeDatabase();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}