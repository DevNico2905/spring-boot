package org.devnico;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    @GetMapping
    public List<SoftwareEngineer> getAll(){
        return List.of(
                new SoftwareEngineer(
                        1,
                        "James",
                        "Java, Spring, Angular, Tailwind"
                ),
                new SoftwareEngineer(
                        2,
                        "Nick",
                        "Node, React"
                )
        );
    }

}
