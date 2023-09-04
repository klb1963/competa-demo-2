package com.competa.competademo.controller;

import com.competa.competademo.entity.SoftSkill;
import com.competa.competademo.repository.SoftSkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping ("/soft-skills")
public class SoftSkillController {

    private final SoftSkillRepository softSkillRepository;

    public SoftSkillController(SoftSkillRepository softSkillRepository) {
        this.softSkillRepository = softSkillRepository;
    }


    @GetMapping("soft-skills")
    public List<SoftSkill> getSoftSkill() {
        return softSkillRepository.findAll();
    }

    @GetMapping("soft-skills/{id}")
    public SoftSkill getSoftSkill(@PathVariable Long id) {
        return softSkillRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    @PostMapping("soft-skills/{id}")
    public ResponseEntity createSoftSkill(@RequestBody SoftSkill softSkill) throws URISyntaxException {
        SoftSkill savedSoftSkill = softSkillRepository.save(softSkill);
        return ResponseEntity.created(new URI("/clients/" + savedSoftSkill.getId())).body(savedSoftSkill);
    }

    @PutMapping("soft-skills/{id}")
    public ResponseEntity updateSoftSkill(@PathVariable Long id, @RequestBody SoftSkill softSkill) {
        SoftSkill currentSoftSkill = softSkillRepository.findById(id).orElseThrow(RuntimeException::new);
        currentSoftSkill.setName(softSkill.getName());
        //currentClient.setEmail(client.getEmail());
        currentSoftSkill = softSkillRepository.save(softSkill);

        return ResponseEntity.ok(currentSoftSkill);
    }

    @DeleteMapping("soft-skills/{id}")
    public ResponseEntity deleteSoftSkill(@PathVariable Long id) {
        softSkillRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
