package com.gestor.app.rest.Controller;

import com.gestor.app.rest.Entity.Task;
import com.gestor.app.rest.Repository.ITodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    private final Logger log = LoggerFactory.getLogger(TodoController.class);
    @Autowired
    private ITodoRepository todoRepository;
    // CRUD sobre la entidad Task

    // Buscar todos los libros de la base de datos
    @GetMapping(value = "/tasks")
    public List<Task>getTasks(){
        return todoRepository.findAll();
    }

    // Buscar tarea por id
    @GetMapping(value = "/tasks/{id}")
    public ResponseEntity<Task> findOneBydId(@PathVariable Long id){
        Optional<Task> taskOpt = todoRepository.findById(id); // me permite recibir valores nulos
        if(taskOpt.isPresent())
        {
            return ResponseEntity.ok(taskOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo libro en base de datos
    @PostMapping(value = "/savetask")
    public ResponseEntity<Task> saveTask(@RequestBody Task task, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(task.getId()!=null){
            log.warn("Trying to create task with id");
            return ResponseEntity.badRequest().build();
        }

        Task result = todoRepository.save(task);
        return ResponseEntity.ok(result);
    }
    // Actualizar libro existente en base de datos
    @PutMapping(value="/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task task){
        if(task.getId() == null){
            log.warn("Triying to update a non existent task");
            return ResponseEntity.badRequest().build();
        }
        if (!todoRepository.existsById(task.getId())){
            log.warn("Triying to update a non existent task");
            return ResponseEntity.notFound().build();
        }
        Task result = todoRepository.save(task);
        return ResponseEntity.ok(result);
    }

    //Borrar libro por id en base de datos
    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
        if(!todoRepository.existsById(id)){
            log.warn("Triying to delete a non existent task");
            return ResponseEntity.notFound().build();
        }
        todoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Borrar todas las tareas
    @DeleteMapping("/delete")
    public ResponseEntity<Task> deleteAllTask(){

        if(todoRepository.count()==0){
            log.warn("The database is empty");
        }
        log.info("REST Request for delete all tasks");
        todoRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }

}
