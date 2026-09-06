package org.devnico;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService sweService;

    public SoftwareEngineerController(SoftwareEngineerService sweService) {
        this.sweService = sweService;
    }

    @GetMapping
    public List<SoftwareEngineer> getAll(){
        return sweService.getAll();
    }

    @PostMapping
    public void saveSwe(@RequestBody SoftwareEngineer newSwe){
        sweService.AddNewSwe(newSwe);
    }

    @GetMapping("/{id}")
    public SoftwareEngineer getById(@PathVariable Integer id){
        return sweService.getById(id);
    }
}
