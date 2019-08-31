package com.rakuten.internship;

import java.util.ArrayList;
import java.util.List;

import com.rakuten.internship.entity.Todo;
import com.rakuten.internship.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    //作成したToDoリストをListに格納し、homeに受け渡し、表示
    @GetMapping("/")
    public String home(Model model) {
        List<Todo> todoList = new ArrayList<Todo>();
        todoList = todoService.findTodos();
        model.addAttribute("todos", todoList);

        return "home";
    }

    //createページを表示
    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String createTodo(@ModelAttribute Todo todo, @RequestParam("title") String title, @RequestParam("description") String description) {
    	//レコード数+1をセット
    	todo.setId(todoService.numberOfRecords() + 1);
        todo.setTitle(title);
        todo.setDescription(description);
        todoService.save(todo);

        return "complete";
    }

    @PostMapping("/complete")
    public String backToHome(Model model) {
    	List<Todo> todos = new ArrayList<Todo>();
        todos = todoService.findTodos();
        model.addAttribute("todos", todos);

        return "home";
    }

}
