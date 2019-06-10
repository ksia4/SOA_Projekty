package parking;

import enums.EmployeeRole;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    private int employeeId;
    private Parking parking;
    private String firstName;
    private String lastName;
    private EmployeeRole employeeRole;

    //login, password, role, isLogged ???

    public Employee(){super();}

    public Employee(String fName, String lName, EmployeeRole role){
        this.firstName = fName;
        this.lastName = lName;
        this.employeeRole = role;
    }

    @Id
    @GeneratedValue
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    //czy na pewno many to one??? TRZEBA ROZWAŻYĆ ONE TO ONE
    @OneToOne
    @JoinColumn(name = "PARKING_ID", nullable = true)//zostawiam na true, to bedzie sytuacja jak tego dnia jeszcze mu nikt nie przypisal parkungu
    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @Column(name = "FIRST_NAME",nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME",nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "ROLE")
    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }
}
