package com.example.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository; // Daten vom Datenbank

    @GetMapping("/student")
    public ResponseEntity<Student> getUser(@RequestParam(value = "id") int id) {

        Optional<Student> studentInDb = studentRepository.findById(id); // Student im Datenbank finden

        // Erst prüfen, ob der Student existiert
        if (studentInDb.isPresent()) {
            return new ResponseEntity<Student>(studentInDb.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity("nothing found with id" + id, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/student/all")
    public ResponseEntity<Iterable<Student>> getAll() {

        // mit Iterable nutzen wir die Methode findall()
        Iterable<Student> allStudentsInDb = studentRepository.findAll();

        return new ResponseEntity<Iterable<Student>>(allStudentsInDb, HttpStatus.OK);

    }

    // Neuer Student eintragen
    @PostMapping("/register")
    private ResponseEntity<Student> createUser(@RequestBody Student newStudent){

        var savedStudent = studentRepository.save(newStudent);
        return new ResponseEntity<Student>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/edit")                // id für das Suchen und updateStudent ersetzt vorherige Attribute
    public ResponseEntity<Student> edit(@RequestParam(value = "id") int id, @RequestBody Student updateStudent) {

        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student studentInDb = optionalStudent.get();

            // Aktualisiert die Attribute des bestehenden Studenten
            studentInDb.setName(updateStudent.getName());
            studentInDb.setEmail(updateStudent.getEmail());
            studentInDb.setPassword(updateStudent.getPassword());

            // Speichert den aktualisierten Studenten
            Student savedStudent = studentRepository.save(studentInDb);

            return new ResponseEntity<Student>(savedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity("nothing found with id " + id, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("student")
    public ResponseEntity delete(@RequestParam(value = "id") int id) { // kein typ, weil nur Repository bearbeitet wird

        Optional<Student> studentInDb = studentRepository.findById(id);

        if (studentInDb.isPresent()) {
            studentRepository.deleteById(id);
            return new ResponseEntity("Student deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("nothing found with id " + id, HttpStatus.NO_CONTENT);
        }
    }
}

