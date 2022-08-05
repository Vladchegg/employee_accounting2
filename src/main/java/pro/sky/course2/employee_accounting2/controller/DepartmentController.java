package pro.sky.course2.employee_accounting2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.course2.employee_accounting2.Employee.Employee;
import pro.sky.course2.employee_accounting2.Service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalary (@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMaxSalary(department);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalary(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMinSalary(department);
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> findEmployeesFromDepartment (@RequestParam("departmentId") int department) {
        return departmentService.findEmployeesFromDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmployees () {
        return departmentService.findEmployees();
    }
}

