package ru.practicum.ewmclient.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmclient.client.user.UserAdminClient;
import ru.practicum.ewmclient.model.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserAdminController {

    private final UserAdminClient client;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDto dto,
                                       HttpServletRequest request) {
        return client.save(dto, request);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                          @Positive @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) Collection<Integer> ids,
                                          HttpServletRequest request) {
        return client.findAll(from, size, ids, request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> remove(@NotNull @PathVariable int userId,
                                         HttpServletRequest request) {
        return client.remove(userId, request);
    }

}