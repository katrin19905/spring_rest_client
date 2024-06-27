package taranova.test_project_API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import taranova.test_project_API.entity.Employee;

import java.util.List;

@Component
public class Communication {
    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://localhost:8080/spring_rest/api/employees";

    public List<Employee> getAllEmployees() {
        ResponseEntity<List<Employee>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Employee>>() {
                        });
        List<Employee> allEmployees = responseEntity.getBody();
        return allEmployees;
    }

    public Employee getEmployee(int id) {
        try {
        Employee employee = restTemplate.getForObject(URL + "/" + id,
                Employee.class);
        return employee;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Can't get employee");
        }
        return null;
    }

    public void saveOrUpdateEmployee(Employee employee) {
        int id = employee.getId();
        if (id == 0) {
            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(URL, employee, String.class);
            System.out.println("New employee was added to DB");
            System.out.println(responseEntity.getBody());
        } else {
            try {
            restTemplate.put(URL, employee);
            System.out.println("Employee with id " + id + " was updated");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                System.out.println("Can't update employee");
            }
        }
    }

    public void deleteEmployee(int id) {
        try {
        restTemplate.delete(URL+"/"+id);
        System.out.println("Employee with ID " + id + " was deleted from DB");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Can't delete employee");
        }
    }
}
