package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

public class Student {
    public  void createStudent(MongoCollection<Document> studentsCollection) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student name ");
        String studentName = scanner.nextLine();
        boolean out = false;
        int studentAge = 0;
        int studentId = 0;
        while (!out) {
            try {
                System.out.println("Enter student age ");
                studentAge = scanner.nextInt();
                System.out.println("Enter student Id");
                studentId = scanner.nextInt();
                Document createdStudent = studentsCollection.find(new Document("studentId", studentId)).first();
                if(createdStudent==null){
                    out = true;
                } else {
                    System.out.println("id from this student busy enter other ID ");
                }
            } catch (InputMismatchException e) {
                System.out.println("ENTER ONLY THE NUMBER YOU'RE STUPID? Try again!! ");
                scanner.next();
            }
        }
        List<ObjectId> enrolledCourses = new ArrayList<>();
        System.out.println("enter courses count");
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            ObjectId courseId = new ObjectId();
            enrolledCourses.add(courseId);
        }
        Document student = new Document("studentName",studentName)
                .append("age",studentAge)
                .append("studentId",studentId)
                .append("enrolledCourses",enrolledCourses);
                studentsCollection.insertOne(student);
    }

    @SuppressWarnings({"unchecked"})
    public void updateCurse(MongoCollection<Document> studentsCollection) {
        Scanner scanner = new Scanner(System.in);
        boolean out = false;
        while (!out) {
            try {
                System.out.println("Enter the student ID you want to update:");
                int studentId = scanner.nextInt();

                Document st = studentsCollection.find(new Document("studentId", studentId)).first();

                if (st != null) {
                    List<ObjectId> studentCurse = (List<ObjectId>) st.get("enrolledCourses");
                    if (studentCurse != null) {

                        System.out.println("Current enrolled courses: " + studentCurse);
                        System.out.println("Enter the number of courses: ");
                        int count = scanner.nextInt();
                        if (count > studentCurse.size()) {
                            for (int i = 0; i < count - studentCurse.size(); i++) {
                                studentCurse.add(new ObjectId());
                            }
                        } else {
                            studentCurse.subList(count, studentCurse.size()).clear();
                        }
                        Bson filter = Filters.eq("studentId", studentId);
                        Bson update = Updates.set("enrolledCourses", studentCurse);
                        studentsCollection.updateOne(filter, update);
                        System.out.println("Courses updated successfully!");
                        out = true;
                    } else {
                        System.out.println("Error: enrolledCourses is null");
                    }
                } else {
                    System.out.println("Error: student not found");

                }
            } catch (InputMismatchException e){
                System.out.println("Enter only number from ID ");
                scanner.next();
            }

        }
    }

    public void deleteStudent(MongoCollection<Document> studentsCollection) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter id student which you want to delete ");

        boolean out = false;
        int studentId = 0;
        while (!out) {
            try {
                studentId = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("enter only number from ID ");
                scanner.next();
            }
            Document deleteStudent = studentsCollection.find(new Document("studentId", studentId)).first();
            if (deleteStudent != null) {
                studentsCollection.deleteOne(Filters.eq("studentId", studentId));
                out = true;
                System.out.println("deleted successfully");
            } else {
                System.out.println("Student from this id not found try again ");
            }
        }
    }
}
