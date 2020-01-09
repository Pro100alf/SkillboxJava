package companies;

import employees.Employee;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

public class Company {

    private ArrayList<Employee> listOfEmployees = new ArrayList<>();
    private BigDecimal companyIncome;

    public Company(){
        companyIncome = BigDecimal.valueOf(0);
    }

    public Company(BigDecimal companyIncome) {
        this.companyIncome = companyIncome;
    }

    public void setCompanyIncome(BigDecimal companyIncome){
        this.companyIncome = companyIncome;
    }

    public void hire(Employee employee) {
        listOfEmployees.add(employee);
    }

    public void hireAll(@NotNull List<Employee> newEmployeesList){
        for (Employee employee : newEmployeesList){
            hire(employee);
        }
    }

    public void fire(int employeeId, int employeesNumber) {
        if (employeeId > -1 && employeesNumber == 0) {
            if (employeeId < listOfEmployees.size()) {
                listOfEmployees.remove(employeeId);
            } else {
                System.out.println("Не найден сотрудник");
            }
        }
        else if (employeeId == -1 && employeesNumber > 0){
            for (int i = 0; i < employeesNumber; i++){
                int randomEmployee = (int)(Math.random() * listOfEmployees.size());
                fire(randomEmployee, 0);
            }
        }
        else {
          System.out.println("Определись, кого хочешь уволить");
        }
    }

    public BigDecimal getIncome(){
        return companyIncome;
    }

    public List<Employee> getTopSalaryStaff(int count) {
        System.out.println("TOP TOP STAFF:");
        List<Employee> employeesList = sortedEmployees(Comparator.comparing(Employee::getMonthSalary).reversed(), count);
        printStaffSalary(employeesList);
        return employeesList;
    }

    public List<Employee> getLowestSalaryStaff(int count) {
        System.out.println("TOP LOWEST STAFF:");
        List<Employee> employeesList = sortedEmployees(Comparator.comparing(Employee::getMonthSalary), count);
        printStaffSalary(employeesList);
        return employeesList;
    }

    private List<Employee> sortedEmployees(Comparator<Employee> c, int count){
        ArrayList<Employee> listOfEmployeesClone = new ArrayList<>(listOfEmployees);
        listOfEmployeesClone.sort(c);
        int getStaffCount = count >= listOfEmployeesClone.size() ? listOfEmployeesClone.size() : count;
        return listOfEmployeesClone.subList(0, getStaffCount);
    }

    private void printStaffSalary(List<Employee> employeesList){
        for (Employee employee : employeesList){
            System.out.println(employee.getMonthSalary());
        }
    }
}