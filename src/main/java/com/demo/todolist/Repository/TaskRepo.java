package com.demo.todolist.Repository;

import com.demo.todolist.Entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task, Long> {

}
