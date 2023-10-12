package com.project;

import com.project.entity.enums.RoleType;
import com.project.entity.user.UserRole;
import com.project.payload.request.user.UserRequest;
import com.project.repository.user.UserRoleRepository;
import com.project.service.user.UserRoleService;
import com.project.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryManagementProjectApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;

	private final UserRoleRepository userRoleRepository;

	private final UserService userService;

	public LibraryManagementProjectApplication(UserRoleService userRoleService,
											   UserRoleRepository userRoleRepository,
											   UserService userService) {
		this.userRoleService = userRoleService;
		this.userRoleRepository = userRoleRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (userRoleService.getAllUserRole().isEmpty()) {

			UserRole admin = new UserRole();
			admin.setRoleType(RoleType.ADMIN);
			admin.setRoleName("Admin");
			userRoleRepository.save(admin);


			UserRole employee = new UserRole();
			employee.setRoleType(RoleType.EMPLOYEE);
			employee.setRoleName("Teacher");
			userRoleRepository.save(employee);

			UserRole member = new UserRole();
			member.setRoleType(RoleType.MEMBER);
			member.setRoleName("Student");
			userRoleRepository.save(member);

		}

		if(userService.countAllAdmins() == 0){

			UserRequest adminRequest = new UserRequest();
			adminRequest.setEmail("nkucukk@github.com");
			adminRequest.setPassword("nKucuk123_");
			adminRequest.setFirstName("Nejla");
			adminRequest.setLastName("Küçük");
			adminRequest.setPhone("0530-000-00-00");
			adminRequest.setBirthDate(LocalDate.of(1995,2,2));
			adminRequest.setAddress("Bursa");

			userService.registerUser(adminRequest);

		}




	}
}
