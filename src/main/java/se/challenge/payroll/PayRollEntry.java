package se.challenge.payroll;
import java.util.Date;

public class PayRollEntry {

    private int employeeId;
    private float hoursWorked;
    private Date date;
    private Character jobGroup;
    public PayRollEntry(int employeeId, float hoursWorked, Date date, Character jobGroup) {
        this.employeeId = employeeId;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.jobGroup = jobGroup;
    }

    public int getEmployeeId(){
        return employeeId;
    }

    public float getHoursWorked(){
        return hoursWorked;
    }

    public Date getDate(){
        return date;
    }

    public Character getJobGroup(){
        return jobGroup;
    }
}
