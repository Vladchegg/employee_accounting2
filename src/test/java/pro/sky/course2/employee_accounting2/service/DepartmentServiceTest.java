package pro.sky.course2.employee_accounting2.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.course2.employee_accounting2.Employee.Employee;
import pro.sky.course2.employee_accounting2.Service.DepartmentService;
import pro.sky.course2.employee_accounting2.Service.EmployeeService;
import pro.sky.course2.employee_accounting2.exceptions.EmployeeNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Крис", "Эванс", 1, 100000),
                new Employee("Роберт", "Дауни", 1, 150000),
                new Employee("Крис", "Хэмсфорт", 2, 90000),
                new Employee("Марк", "Руффало", 2, 80000),
                new Employee("Том", "Холланд", 2, 50000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMaxSalary(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalary(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeWithMinSalaryPositiveTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMinSalary(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMinSalaryNegativeTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalary(3));
    }


    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeesFromDepartmentPositiveTest(int departmentId, List <Employee> expected) {
        Assertions.assertThat(departmentService.findEmployeesFromDepartment(departmentId)).containsExactlyElementsOf(expected);
    }

    @Test
    public void employeesGroupedByDepartmentTest() {
        Assertions.assertThat(departmentService.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Крис", "Эванс", 1, 100000),
                                    new Employee("Роберт", "Дауни", 1, 150000)),
                        2, List.of(new Employee("Крис", "Хэмсфорт", 2, 90000),
                                new Employee("Марк", "Руффало", 2, 80000),
                                new Employee("Том", "Холланд", 2, 50000)))
                );
    }


    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Роберт", "Дауни", 1, 150000)),
                Arguments.of(2, new Employee("Крис", "Хэмсфорт", 2, 90000))
        );
    }

    public static Stream<Arguments> employeeWithMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Крис", "Эванс", 1, 100000)),
                Arguments.of(2, new Employee("Том", "Холланд", 2, 50000))
        );
    }

    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Крис", "Эванс", 1, 100000),
                                        new Employee("Роберт", "Дауни", 1, 150000))),
                Arguments.of(2, List.of(new Employee("Крис", "Хэмсфорт", 2, 90000),
                                        new Employee("Марк", "Руффало", 2, 80000),
                                        new Employee("Том", "Холланд", 2, 50000))),
                Arguments.of(3, Collections.emptyList())
        );
    }

}

