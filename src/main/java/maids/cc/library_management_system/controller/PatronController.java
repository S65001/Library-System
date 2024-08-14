package maids.cc.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.service.PatronService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patron")
@RequiredArgsConstructor
public class PatronController {
    private final PatronService patronService;
    @PostMapping
    public void addPatron(@Valid @RequestBody Patron patron){
        patronService.addPatron(patron);
    }
    @GetMapping
    public List<Patron> getAllPatrons(){
        return patronService.getAllPatrons();
    }
    @GetMapping("/{id}")
    public Patron getPatron(@PathVariable("id") Long id){
        return patronService.getPatron(id);
    }
    @PutMapping("/{id}")
    public void updatePatron(@PathVariable("id") Long id,@Valid @RequestBody Patron patron){
        patronService.updatePatron(id, patron);
    }
    @DeleteMapping("/{id}")
    public void deletePatron(@PathVariable("id") Long id){
        patronService.deletePatron(id);
    }
}
