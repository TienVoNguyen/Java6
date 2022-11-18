package com.example.demojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) {
        try {
            demo02();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void demo02() throws IOException {
        String path = "src/main/resources/students.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode students = mapper.readTree(new File(path));
        students.iterator().forEachRemaining (student -> {
            System.out.println(">> Name: " + student.get("name").asText());
        });
    }

    private static void demo01() throws Exception {
        String path = "src/main/resources/student.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode student = mapper.readTree(new File(path));

        System.out.println(">> Name: " + student.get("name").asText());
        System.out.println(">> Name:" + student.get("name").asText());
        System.out.println(">> Marks: " + student.get("marks").asDouble());
        System.out.println(">> Gender: " + student.get("gender").asBoolean());
        System.out.println(">> Email:" + student.get("contact").get("email").asText());
        System.out.println(">> Phone: " + student.findValue("phone").asText());
        student.get("subjects").iterator().forEachRemaining(subject -> {
            System.out.println(">> Subject: " + subject.asText());
        });
    }
}
