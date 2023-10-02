package ru.practicum.ewmservice.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.service.user.UserAdminService;

import java.util.Collection;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {

    private final UserAdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody UserDto dto) {
        return service.save(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> findAll(@RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) Collection<Integer> ids) {
        return service.findAll(from, size, ids);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable int userId) {
        service.remove(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public UserDto findByName(@RequestParam String user) {
        return service.findRoleOfUserByName(user);
    }

}