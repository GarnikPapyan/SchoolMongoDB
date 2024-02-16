package org.example;

import com.mongodb.client.*;

import org.bson.Document;




public class Main {
    public static void main(String[] args) {

            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("SchoolDB");
            MongoCollection<Document> studentsCollection = database.getCollection("Students");
            MongoCollection<Document> coursesCollection = database.getCollection("Courses");
            Student student = new Student();
            Courses courses= new Courses();
            //courses.createCurse(coursesCollection);

            student.createStudent(studentsCollection);
           // student.updateCurse(studentsCollection);
            //student.deleteStudent(studentsCollection);
            System.out.println("Database and collection created successfully.");
    }
}