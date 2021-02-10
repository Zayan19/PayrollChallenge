package se.challenge.payroll;
import org.json.JSONObject;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import javax.management.InvalidAttributeValueException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "WebApis")
@MultipartConfig
public class WebAPIs extends HttpServlet {

    PayrollEntryBridge payrollEntryBridge;
    TimeReportEntryBridge timeReportEntryBridge;

    public void init() {
        payrollEntryBridge = new PayrollEntryBridge("jdbc:postgresql://localhost:5432/testdb");
        timeReportEntryBridge = new TimeReportEntryBridge("jdbc:postgresql://localhost:5432/testdb");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<PayRollEntry> payRollEntries = payrollEntryBridge.GetAllEntries();
            JSONObject reportJSON = new PayRollReport().GetReport(payRollEntries);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.println(reportJSON);
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        List<Part> fileParts = request.getParts().stream().collect(Collectors.toList());
        Part filePart = fileParts.get(0);

        try {
            timeReportEntryBridge.Add(filePart.getSubmittedFileName());
            List<PayRollEntry> payRollEntries = CSVParser.GetPayRollEntries(filePart.getInputStream());
            payrollEntryBridge.AddMultipleEntries(payRollEntries);
        } catch (ParseException | SQLException | InvalidAttributeValueException e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
            out.flush();
        }
    }

    public void destroy() {
    }
}