package com.example.demojson;

import com.example.demojson.models.Contact;
import com.example.demojson.models.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo02 {
    public static void main(String[] args) throws Exception {
        demo07();
    }

    private static void demo07() throws IOException {
        Contact contact = new Contact("teonv@gmail.com", "0913745789");
        List<String> subjects = Arrays.asList("WEB205", "COM108");
        Student student = new Student("Nguyễn Văn Tèo", true, 7.5, contact, subjects);
        ObjectMapper mapper = new ObjectMapper();
// Write to String
        String json = mapper.writeValueAsString(student);
// Write to output
        mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, student);
// Write to file
        mapper.writeValue(new File("e:/temp/student.json"), student);
    }

    private static void demo06() throws IOException {
        Map<String, Object> contact = new HashMap<>();
        contact.put("email", "teonv@gmail.com");
        contact.put("phone", "0913745789");
        List<String> subjects = Arrays.asList("WEB205", "COM108");
        Map<String, Object> student = new HashMap<String, Object>();
        student.put("name", "Nguyễn Văn Tèo");
        student.put("marks", 7.5);
        student.put("gender", true);
        student.put("contact", contact);
        student.put("subjects", subjects);
        ObjectMapper mapper = new ObjectMapper();
        // Write to String
        String json = mapper.writeValueAsString(student);
        System.out.println(json);
        // Write to output
        mapper.writeValue(System.out, student);
        // Write to file
        mapper.writeValue(new File("e:/temp/student.json"), student);
    }

    private static void demo05() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode student = mapper.createObjectNode();
        student.put("name", "Nguyễn Văn Tèo");
        student.put("marks", 7.5);
        student.put("gender", true);
        ObjectNode contact = student.putObject("contact");
        contact.put("email", "teonv@gmail.com");
        contact.put("phone", "0913745789");
        ArrayNode subjects = student.putArray("subjects");
        subjects.add("WEB205");
        subjects.add("COM108");

        // Write to String
        String json = mapper.writeValueAsString(student);
        // Write to output
        mapper.writeValue(System.out, student);
        // Write to file
        mapper.writeValue(new File("e:/temp/student.json"), student);
    }

    private static void demo04() throws IOException {
        String path = "src/main/resources/students.json";
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Student>> type = new TypeReference<List<Student>>() {};
        List<Student> students = mapper.readValue(new File(path), type);
        students.forEach(student -> {
            System.out.println(">> Name: " + student.getName());
        });
    }

    private static void demo03() throws IOException {
        String path = "src/main/resources/student.json";
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(new File(path), Student.class);
        System.out.println(">> Name: " + student.getName());
        System.out.println(">> Marks: " + student.getMarks());
        System.out.println(">> Gender: " + student.getGender());
        Contact contact = student.getContact();
        System.out.println(">> Email: " + contact.getEmail());
        System.out.println(">> Phone: " + contact.getPhone());
        List<String> subjects = student.getSubjects();
        subjects.forEach(subject -> {
            System.out.println(">> Subject: " + subject);
        });
    }

    private static void demo02() throws IOException {
        String path = "src/main/resources/students.json";
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> students = mapper.readValue(new File(path), List.class);
        students.forEach(student -> {
            System.out.println(">> Name: " + student.get("name"));
        });
    }

    private static void demo01() throws Exception {
        String path = "src/main/resources/student.json";
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> student = mapper.readValue(new File(path), Map.class);
        System.out.println(">> Name: " + student.get("name"));
        System.out.println(">> Marks: " + student.get("marks"));
        System.out.println(">> Gender: " + student.get("gender"));
        Map<String, Object> contact = (Map<String, Object>) student.get("contact");
        System.out.println(">> Email: " + contact.get("email"));
        System.out.println(">> Phone: " + contact.get("phone"));
        List<String> subjects = (List<String>) student.get("subjects");
        subjects.forEach(subject -> {
            System.out.println(">> Subject: " + subject);
        });

    }
}
