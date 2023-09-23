package com.example.webapp.controller;

import com.example.webapp.entity.Message;
import com.example.webapp.repostitory.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {


    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model){
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model){
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        messageRepository.save(new Message(text, tag));
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String tag, Map<String, Object> model){

        if(tag == null || tag.isEmpty()) {
            model.put("messages", messageRepository.findAll());
        } else {
            model.put("messages", messageRepository.findByTag(tag));
        }

        return "main";
    }
}
