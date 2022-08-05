package pro.sky.course2.employee_accounting2.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.course2.employee_accounting2.Employee.Employee;
import pro.sky.course2.employee_accounting2.Service.EmployeeService;
import pro.sky.course2.employee_accounting2.exceptions.EmployeeAlreadyAddedException;
import pro.sky.course2.employee_accounting2.exceptions.EmployeeNotFoundException;
import pro.sky.course2.employee_accounting2.exceptions.EmployeeStorageIsFullException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService();

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Marina", "Gorbunova", 2, 70000),
                Arguments.of("Vladimir", "Kozlov", 1, 80000),
                Arguments.of("Elena", "Sidorova", 2, 90000)
        );
    }

    @ParameterizedTest
    @MethodSource("params")

    public void addTest1(String firstName, String lastName, int department, int salary) {
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.add(firstName, lastName, department, salary)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add(firstName, lastName, department, salary));
    }

    @ParameterizedTest
    @MethodSource("params")

    public void addTest2(String firstName, String lastName, int department, int salary) {
        List<Employee> employees = generateEmployees(10);
        employees.forEach(employee ->
                assertThat(employeeService.add(employee.getFirstName(), employee.getLastName(), employee.getDepartment(), employee.getSalary()))
                        .isEqualTo(employee));

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add(firstName, lastName, department, salary));
    }

    @ParameterizedTest
    @MethodSource("params")

    public void removeNegativeTest(String firstName, String lastName, int department, int salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("firstName", "lastName", 1, 0));

        Employee expected = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.add(firstName, lastName, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("firstName", "lastName", 1, 0));
    }

    @ParameterizedTest
    @MethodSource("params")

    public void removePositiveTest(String firstName, String lastName, int department, int salary) {
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.add(firstName, lastName, department, salary)).isEqualTo(expected);

        assertThat(employeeService.remove(firstName, lastName, department, salary)).isEqualTo(expected);
        Assertions.assertThat(employeeService.getAll()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("params")

    public void findNegativeTest(String firstName, String lastName, int department, int salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("firstName", "lastName", 1, 0));

        Employee expected = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.add(firstName, lastName, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("firstName", "lastName", 1, 0));
    }

    @ParameterizedTest
    @MethodSource("params")

    public void findPositiveTest(String firstName, String lastName, int department, int salary) {
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertThat(employeeService.add(firstName, lastName, department, salary)).isEqualTo(expected);

        assertThat(employeeService.find(firstName, lastName, department, salary)).isEqualTo(expected);
        Assertions.assertThat(employeeService.getAll()).hasSize(1);

    }

    private List<Employee> generateEmployees(int size) {
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employee("firstName" + (char) ((int) 'a' + i), "lastName" + (char) ((int) 'a' + i), i, 15000 + i))
                .collect(Collectors.toList());
    }
}
