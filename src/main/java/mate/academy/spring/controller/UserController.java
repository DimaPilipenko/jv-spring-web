package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.UserResponseDto;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import mate.academy.spring.service.mapper.UserDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserDtoMapper dtoMapper;

    public UserController(UserService userService, UserDtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/users/{id}")
    public UserResponseDto get(@PathVariable Long id) {
        return dtoMapper.parse(userService.get(id));
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapper::parse)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/inject")
    public String injectMockData() {
        User first = new User();
        first.setFirstName("John");
        first.setLastName("Doe");

        User second = new User();
        second.setFirstName("Emily");
        second.setLastName("Stone");

        User third = new User();
        third.setFirstName("Hugh");
        third.setLastName("Dane");

        userService.add(first);
        userService.add(second);
        userService.add(third);
        return "Users are injected!";
    }
}