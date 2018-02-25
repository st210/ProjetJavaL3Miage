import Model.Employee;
import Model.Company;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        Company e = new Company("Super Mario");
        List<Employee> l = new ArrayList<>();
        e.showAllEmployees();
    }
}