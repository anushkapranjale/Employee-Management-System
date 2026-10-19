package com.example.ems.config;

import com.example.ems.model.*;
import com.example.ems.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Creates demo users and employees on first run. Change/remove before real use. */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(AppUserRepository users, EmployeeRepository employees, PasswordEncoder enc) {
        return args -> {
            if (users.count() == 0) {
                users.save(new AppUser("admin", enc.encode("admin123"), Role.ADMIN));
                users.save(new AppUser("hr",    enc.encode("hr123"),    Role.HR));
                users.save(new AppUser("user",  enc.encode("user123"),  Role.USER));
            }
            if (employees.count() == 0) {
                employees.save(make("Asha", "Rao", "asha.rao@example.com", "Engineering", "Software Engineer", "720000", LocalDate.of(2023, 7, 1)));
                employees.save(make("Rahul", "Mehta", "rahul.mehta@example.com", "Security", "SOC Analyst", "680000", LocalDate.of(2024, 1, 15)));
                employees.save(make("Neha", "Singh", "neha.singh@example.com", "HR", "HR Executive", "540000", LocalDate.of(2022, 11, 10)));
            }
        };
    }

    private Employee make(String f, String l, String email, String dept, String desig, String sal, LocalDate doj) {
        Employee e = new Employee();
        e.setFirstName(f); e.setLastName(l); e.setEmail(email);
        e.setDepartment(dept); e.setDesignation(desig);
        e.setSalary(new BigDecimal(sal)); e.setDateOfJoining(doj);
        return e;
    }
}
