package ru.practicum.ewmclient.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.user.UserAdminClient;
import ru.practicum.ewmcommondto.model.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserAdminController {

    private final UserAdminClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDto dto) {
        return client.save(dto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return client.findAll();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> remove(@PathVariable int userId) {
        return client.remove(userId);
    }

}