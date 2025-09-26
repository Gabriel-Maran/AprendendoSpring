package br.com.gabrielmaran.aprendendoSpring.controllers;

import br.com.gabrielmaran.aprendendoSpring.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String teamplate = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    //http://localhost:8080/greeting?name=Gabriel ->  {"id":12,"content":"Hello, Gabriel!"}
    //http://localhost:8080/greeting ->  {"id":12,"content":"Hello, World!"}
    @RequestMapping("/greeting")
    public Greeting greetings(@RequestParam(value = "name", defaultValue = "World")
                              String name) {
        return new Greeting(counter.incrementAndGet(), String.format(teamplate, name));

    }
}
