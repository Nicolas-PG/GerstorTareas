package com.gestor.app.rest.Controller;

import com.gestor.app.rest.Entity.Task;
import com.gestor.app.rest.Repository.ITodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private ITodoRepository todoRepository;
    // CRUD sobre la entidad Task

    // Buscar todos los libros de la base de datos
    @GetMapping(value = "/tasks")
    public List<Task>getTasks(){
        return todoRepository.findAll();
    }


    // Crear un nuevo libro en base de datos
    @PostMapping(value = "/savetask")
    public String saveTasl(@RequestBody Task task){
        todoRepository.save(task);
        return "Saved task";
    }
    // Actualizar libro existente en base de datos
    @PutMapping(value="/update/{id}")
    public String updateTask(@PathVariable Long id,@RequestBody Task task){

        Task updateTask = todoRepository.findById(id).get();
        updateTask.setTitle(task.getTitle());
        updateTask.setDescription(task.getDescription());
        todoRepository.save(updateTask);
        return "Updated task";
    }

    //Borrar libro por id en base de datos
    @DeleteMapping(value="delete/{id}")
    public String deleteTask(@PathVariable Long id){
        Task deletedTask = todoRepository.findById(id).get();
        todoRepository.delete(deletedTask);
        return "Deletede Task";
    }

}
