package taranova.test_project_API;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import taranova.test_project_API.configuration.MyConfig;
import taranova.test_project_API.entity.Employee;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);
        System.out.println("all beans:");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("*".repeat(20));
        Communication communication = context.getBean(
                "communication", Communication.class);
        List<Employee> allEmployees = communication.getAllEmployees();
        allEmployees.forEach(System.out::println);
        System.out.println("end of list");
        System.out.println();
        int id = 100;
        Employee empById = communication.getEmployee(id);
        System.out.println("employee with id = " + id + "\n" + empById);
        Employee emp = new Employee("Mike", "Sokolov",
                "HR", 2222, 100);
        communication.saveOrUpdateEmployee(emp);
        communication.deleteEmployee(48);
    }
}
