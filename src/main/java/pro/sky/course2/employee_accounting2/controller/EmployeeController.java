package pro.sky.course2.employee_accounting2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.course2.employee_accounting2.Employee.Employee;
import pro.sky.course2.employee_accounting2.Service.EmployeeService;



import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee add (@RequestParam("firstName") String firstName,
                         @RequestParam("lastName") String lastName,
                         @RequestParam("departmentId") int department,
                         @RequestParam("salary") int salary) {
        return employeeService.add (firstName, lastName, department, salary);
    }

    @GetMapping("/remove")
    public Employee remove (@RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("departmentId") int department,
                            @RequestParam("salary") int salary) {
        return employeeService.remove(firstName, lastName, department, salary);
    }

    @GetMapping("/find")
    public Employee find (@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("departmentId") int department,
                          @RequestParam("salary") int salary) {
        return employeeService.find(firstName, lastName, department, salary);
    }

    @GetMapping
    public List <Employee> getAll() {
        return employeeService.getAll();
    }
}

