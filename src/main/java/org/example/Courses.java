package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Courses {
    public void createCurse( MongoCollection<Document> coursesCollection) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course name");
        String courseName = scanner.nextLine();
        System.out.println("Enter department  ");
        String department = scanner.nextLine();
        ObjectId courseId = new ObjectId();
        Document course = new Document("courseId", courseId)
                .append("courseName", courseName)
                .append("department", department);
        coursesCollection.insertOne(course);
    }

    public List<ObjectId> getAllCourseIds(MongoCollection<Document> coursesCollection) {
        List<ObjectId> courseIds = new ArrayList<>();
        coursesCollection.find().forEach(document -> courseIds.add(document.getObjectId("_id")));
        return courseIds;
    }
}
