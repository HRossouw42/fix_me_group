import java.io.*;
import java.util.*;
import java.io.Serializable;

// https://www.developer.com/design/article.php/3597071/Serializing-an-Object-via-a-ClientServer-Connection.html
public class InstrumentList implements Serializable {

    private int employeeNumber;
    private String employeeName;

    InstrumentList(int num, String name) {
        employeeNumber = num;
        employeeName= name;
    }

    public int getEmployeeNumber() {
        return employeeNumber ;
    }

    public void setEmployeeNumber(int num) {
        employeeNumber = num;
    }

    public String getEmployeeName() {
        return employeeName ;
    }

    public void setEmployeeName(String name) {
        employeeName = name;
    }
}