package model;

import java.util.ArrayList;

import model.person.Student;
import model.person.Faculty;

public class Course {
    private String title;
    private String description;
    private int courseCode;
    private int credits;
    private int CRN;
    private char passingGrade;
    private ArrayList<Course> prerequisites;
    private ArrayList<Student> students;
    private Faculty teacher;

    public Course(String title, String description, int courseCode, int credits, int CRN, char passingGrade, ArrayList<Course> prerequisites, ArrayList<Student> students, Faculty teacher) {
        this.title = title;
        this.description = description;
        this.courseCode = courseCode;
        this.credits = credits;
        this.CRN = CRN;
        this.passingGrade = passingGrade;
        this.students = students;
        this.prerequisites = prerequisites;
        this.teacher = teacher;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCourseCode() { return courseCode; }
    public int getCredits() { return credits; }
    public int getCRN() { return CRN; }
    public char getPassingGrade() { return passingGrade; }
    public ArrayList<Course> getPrerequisites() { return prerequisites; }
    public ArrayList<Student> getStudents() { return students; }
    public Faculty getTeacher() { return teacher; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCourseCode(int courseCode) { this.courseCode = courseCode; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setCRN(int CRN) { this.CRN = CRN; }
    public void setPassingGrade(char passingGrade) { this.passingGrade = passingGrade; }
    public void setPrerequisiteCourses(ArrayList<Course> prerequisites) { this.prerequisites = prerequisites; }
    public void setStudents(ArrayList<Student> students) { this.students = students; }
    public void setTeacher(Faculty teacher) { this.teacher = teacher; }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course{")
          .append("title='").append(title).append('\'')
          .append(", description='").append(description).append('\'')
          .append(", courseCode=").append(courseCode)
          .append(", credits=").append(credits)
          .append(", CRN=").append(CRN)
          .append(", passingGrade=").append(passingGrade)
          .append(", students=").append(students)
          .append(", prerequisites=").append(prerequisites)
          .append('}');
        return sb.toString();
    }
}
