package com.rakuten.internship.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

//import com.rakuten.internship.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import com.rakuten.internship.entity.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
@Transactional
public class TodoService {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    //
    public List<Todo> findTodos(){
        List list = jdbcTemplate.queryForList("select * from tasks");
        List<Todo> todoList = new ArrayList<Todo>();
        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            Map map = (Map)itr.next();
            Todo todo = createTodo(map);
            todoList.add(todo);
        }

        return todoList;
    }

    //ToDoリストを作成
    private Todo createTodo(Map map) {
        Todo todo = new Todo();
        todo.setId(((Integer) map.get("number")).intValue());
        todo.setTitle((String) map.get("title"));
        todo.setDescription((String) map.get("description"));
        return todo;
    }

    //データべースにデータを挿入
    public Todo save(Todo todo) {
        String sql = "INSERT INTO tasks(number, title, description) VALUES("
        			+ todo.getId() + ",'"
        			+ todo.getTitle() + "','"
        			+ todo.getDescription() + "');";
        jdbcTemplate.update(sql);

        return null;
    }

    //現在のレコードの数を返却
    public int numberOfRecords(){
    	return jdbcTemplate.queryForList("select * from tasks").size();
    }

}