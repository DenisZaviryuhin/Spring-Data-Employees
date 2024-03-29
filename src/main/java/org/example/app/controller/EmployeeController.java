package org.example.app.controller;

import org.example.app.domain.Employee;
import org.example.app.network.ResponseData;
import org.example.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private static final String SMTH_WRONG = "Something wrong!";
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseData save(@RequestBody Employee employee) {
        Optional<Employee> optional = employeeService.create(employee);
        return optional.map(value ->
                        new ResponseData(HttpStatus.CREATED.toString(),
                                true, value))
                .orElseGet(() ->
                        new ResponseData(HttpStatus.NO_CONTENT.toString(),
                                false, SMTH_WRONG));
    }

    @GetMapping("/employees")
    public ResponseData getAll() {
        Optional<List<Employee>> optional = employeeService.getAll();
        return optional.map(employees ->
                        new ResponseData(HttpStatus.OK.toString(),
                                true, employees))
                .orElseGet(() ->
                        new ResponseData(HttpStatus.NOT_FOUND.toString(),
                                false, SMTH_WRONG));
    }

    @GetMapping("/employees/{id}")
    public ResponseData getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null)
            return new ResponseData(HttpStatus.OK.toString(),
                    true, employee);
        else return new ResponseData(HttpStatus.NOT_FOUND.toString(),
                false, SMTH_WRONG);
    }

    @PutMapping("/employees/{id}")
    public ResponseData update(@PathVariable Long id,
                               @RequestBody Employee employee) {
        Employee employeeUpdated = employeeService.update(id, employee);
        if (employeeUpdated != null)
            return new ResponseData(HttpStatus.OK.toString(),
                    true, employeeUpdated);
        else return new ResponseData(HttpStatus.NOT_FOUND.toString(),
                false, SMTH_WRONG);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseData delete(@PathVariable Long id) {
        if (employeeService.delete(id))
            return new ResponseData(HttpStatus.OK.toString(),
                    true, "Deleted.");
        else return new ResponseData(HttpStatus.NOT_FOUND.toString(),
                false, SMTH_WRONG);
    }

    @GetMapping("/employees/first-name/{firstName}")
    public ResponseData getByFirstName(@PathVariable String firstName) {
        List<Employee> list = employeeService.getByFirstName(firstName);
        if (!list.isEmpty())
            return new ResponseData(HttpStatus.OK.toString(),
                    true, list);
        else return new ResponseData(HttpStatus.NOT_FOUND.toString(),
                false, "No data.");
    }

    @GetMapping("/employees/last-name/{lastName}")
    public ResponseData getByLastName(@PathVariable String lastName) {
        List<Employee> list = employeeService.getByLastName(lastName);
        if (!list.isEmpty())
            return new ResponseData(HttpStatus.OK.toString(),
                    true, list);
        else return new ResponseData(HttpStatus.NOT_FOUND.toString(),
                false, "No data.");
    }
}
