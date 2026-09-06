package org.devnico;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository sweRepo;

    public SoftwareEngineerService(SoftwareEngineerRepository sweRepo) {
        this.sweRepo = sweRepo;
    }

    //Read

    public List<SoftwareEngineer> getAll(){
        return sweRepo.findAll();
    }

    public void AddNewSwe(SoftwareEngineer newSwe){
        sweRepo.save(newSwe);
    }

    public SoftwareEngineer getById(Integer id){
        return sweRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Software Engineer not found."));
    }

}
