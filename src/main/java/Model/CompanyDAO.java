package Model;

import java.util.List;

public interface CompanyDAO {
    List<Employee> findAll();
    Employee findEmployeeById(int id);

    void insertEmployee(Employee employee);

    void updateEmployeeName(Employee employee, String name);
    void updateEmployeeFirstName(Employee employee, String firstname);
    void updateEmployeeEntry(Employee employee, String entry);

    void deleteEmployee(Employee employee);
}
