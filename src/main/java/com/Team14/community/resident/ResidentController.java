package com.Team14.community.resident;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {
    private final ResidentRepository residentRepository;
    public ResidentController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resident addResident(@Valid @RequestBody Resident resident) {
        resident.setId(null);
        return residentRepository.save(resident);
    }
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Resident getResidentById(@PathVariable Long id) {
        return residentRepository.findById(id)
                .orElseThrow(() -> new ResidentNotFoundException(id));
    }
    @PutMapping("/{id}")
    public Resident updateResident(@PathVariable Long id,
                                   @Valid @RequestBody Resident updatedResident) {
        Resident existing = residentRepository.findById(id)
                .orElseThrow(() -> new ResidentNotFoundException(id));

        existing.setName(updatedResident.getName());
        existing.setVillage(updatedResident.getVillage());
        existing.setPhone(updatedResident.getPhone());
        existing.setAge(updatedResident.getAge());
        existing.setGender(updatedResident.getGender());

        return residentRepository.save(existing);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResident(@PathVariable Long id) {
        if (!residentRepository.existsById(id)) {
            throw new ResidentNotFoundException(id);
        }
        residentRepository.deleteById(id);
    }
}
