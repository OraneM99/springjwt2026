package co.simplon.springjwt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.springjwt.entity.TodoEntity;
import co.simplon.springjwt.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepositoryInjected) {
        this.todoRepository = todoRepositoryInjected;
    }

    // Accessible par ROLE_USER et ROLE_ADMIN
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN')")
    @GetMapping("")
    public List<TodoEntity> getAll() {
        return this.todoRepository.findAll();
    }

    // Accessible par ROLE_ADMIN uniquement
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntity create(@RequestBody TodoEntity entity) {
        return this.todoRepository.save(entity);
    }
}
