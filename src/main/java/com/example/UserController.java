package com.example;

import com.example.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    SessionFactory sessionFactory;

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> save(@RequestBody User user) {
        try {
            Session session = sessionFactory.openSession();
            session.save(user);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }

    @PostMapping(value = "/getAllUsers", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> getusers() {
        try {
            Session session = sessionFactory.openSession();
            List<User> usersList = session.createQuery("from User").list();
            session.flush();
            session.close();
            return ResponseEntity.ok(usersList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data not Found");
        }
    }

    @GetMapping(value = "/getOneUser/{sId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> getOneUser(@PathVariable(value = "sId") int id) {
        try {
            Session session = sessionFactory.openSession();
            User user = session.get(User.class, id);
            session.flush();
            session.close();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data not found");
        }
    }

    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> update(@RequestBody User user) {
        try {
            Session session = sessionFactory.openSession();
            session.saveOrUpdate(user);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data Updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data Update Failed");
        }
    }

    @GetMapping(value = "/delete/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
        try {
            Session session = sessionFactory.openSession();
            User user = session.get(User.class, id);
            session.delete(user);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data deletation failed");
        }
    }

}
