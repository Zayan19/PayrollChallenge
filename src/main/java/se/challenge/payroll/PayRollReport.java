package se.challenge.payroll;

import java.util.*;

import org.json.*;

public class PayRollReport {
    HashMap<Character, Integer> groupPayMap = new HashMap();
    public PayRollReport() {
        groupPayMap.put('A', 20);
        groupPayMap.put('B', 30);
    }

    public JSONObject GetReport(List<PayRollEntry> payRollEntries) {
        return GetReportJSON(GetReportData(payRollEntries));
    }

    TreeMap<Integer, TreeMap<Date, Float>> GetReportData(List<PayRollEntry> payRollEntries) {
        TreeMap<Integer, TreeMap<Date, Float>> payrollReportMap = new TreeMap<Integer, TreeMap<Date, Float>>();
        for (PayRollEntry entry : payRollEntries) {
            int employeeId = entry.getEmployeeId();
            if (!payrollReportMap.containsKey(employeeId)) {
                payrollReportMap.put(employeeId, new TreeMap<Date, Float>());
            }

            TreeMap<Date, Float> userPayMap = payrollReportMap.get(employeeId);
            Date dateKey = DateHelper.GetDateKey(entry.getDate());
            if (!userPayMap.containsKey(dateKey)) {
                userPayMap.put(dateKey, 0.0F);
            }

            userPayMap.put(dateKey, userPayMap.get(dateKey) + (groupPayMap.get(entry.getJobGroup()) * entry.getHoursWorked()));
        }
        GetReportJSON(payrollReportMap);
        return payrollReportMap;
    }

    private JSONObject GetReportJSON(TreeMap<Integer, TreeMap<Date, Float>> payRollReportMap) {
        JSONObject employeeReportJSON = new JSONObject();
        JSONArray employeeJSONArray = new JSONArray();

        payRollReportMap.forEach((employeeid, employeeDatePair) -> {
            employeeDatePair.forEach((date, amount) -> {
                JSONObject reportEntryJSON = new JSONObject();
                reportEntryJSON.put("employeeId", employeeid);
                reportEntryJSON.put("amountPaid", "$" + amount + "0");

                JSONObject payPeriodJSON = new JSONObject();
                payPeriodJSON.put("startDate", DateHelper.GetDateAsString(date));
                payPeriodJSON.put("endDate", DateHelper.GetEndDate(date));
                reportEntryJSON.put("payPeriod", payPeriodJSON);

                employeeJSONArray.put(reportEntryJSON);

            });
        });

        employeeReportJSON.put("employeeReports", employeeJSONArray);

        JSONObject reportJSON = new JSONObject();
        reportJSON.put("payrollReport", employeeReportJSON);
        return reportJSON;
    }
}

