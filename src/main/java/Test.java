import Model.Employee;
import Model.Company;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        Company e = new Company("Super Mario");
        List<Employee> l = new ArrayList<>();
        e.showAllEmployees();
        Employee vador = new Employee("Vador", "Darth", "01/01/1990");
        e.insertEmployee(vador);
        System.out.println(e.findEmployeeById(51));
        e.updateEmployeeFirstName(vador, "Luke");
        System.out.println(e.findEmployeeById(51));
        e.deleteEmployee(vador);
        System.out.println(e.findEmployeeById(51));

    }
}