package service;

import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.person.Faculty;
import model.person.Student;

public class CourseService {
    private List<Course> courses;

    public CourseService() {
        this.courses = new ArrayList<>();
    }

    /**
     * Creates a new course and adds it to the course list
     * 
     * @param title Course title
     * @param description Course description
     * @param courseCode Unique course code
     * @param credits Number of credits
     * @param CRN Course Registration Number
     * @param passingGrade Minimum passing grade
     * @param prerequisites List of prerequisite courses
     * @param teacher Faculty member teaching the course
     * @return The newly created course
     * @throws IllegalArgumentException if any required parameter is invalid
     */
    public Course createCourse(String title, String description, int courseCode, int credits, 
                            int CRN, char passingGrade, ArrayList<Course> prerequisites, 
                            Faculty teacher) throws IllegalArgumentException {
        // Validate input parameters
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty");
        }
        if (courseCode <= 0) {
            throw new IllegalArgumentException("Course code must be positive");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        if (CRN <= 0) {
            throw new IllegalArgumentException("CRN must be positive");
        }
        if (isCourseCodeDuplicate(courseCode)) {
            throw new IllegalArgumentException("Course code " + courseCode + " already exists");
        }
        if (isCRNDuplicate(CRN)) {
            throw new IllegalArgumentException("CRN " + CRN + " already exists");
        }

        // Create new course
        Course course = new Course(title, description, courseCode, credits, CRN, 
                                passingGrade, prerequisites, new ArrayList<>(), teacher);
        courses.add(course);
        
        // If teacher is provided, assign the course to them
        if (teacher != null) {
            try {
                teacher.assignCourse(course);
            } catch (Exception e) {
                // Remove the course if assignment fails
                courses.remove(course);
                throw new IllegalArgumentException("Failed to assign course to faculty: " + e.getMessage());
            }
        }
        
        return course;
    }

    /**
     * Checks if a course code already exists in the system
     * 
     * @param courseCode The course code to check
     * @return true if the course code exists, false otherwise
     */
    private boolean isCourseCodeDuplicate(int courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode() == courseCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a CRN already exists in the system
     * 
     * @param CRN The CRN to check
     * @return true if the CRN exists, false otherwise
     */
    private boolean isCRNDuplicate(int CRN) {
        for (Course course : courses) {
            if (course.getCRN() == CRN) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a course by its course code
     * 
     * @param courseCode The course code to search for
     * @return The course if found, null otherwise
     */
    public Course findCourseByCode(int courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode() == courseCode) {
                return course;
            }
        }
        return null;
    }

    /**
     * Finds a course by its CRN
     * 
     * @param CRN The CRN to search for
     * @return The course if found, null otherwise
     */
    public Course findCourseByCRN(int CRN) {
        for (Course course : courses) {
            if (course.getCRN() == CRN) {
                return course;
            }
        }
        return null;
    }

    /**
     * Enrolls a student in a course
     * 
     * @param student The student to enroll
     * @param course The course to enroll in
     * @return true if enrollment was successful, false otherwise
     * @throws IllegalArgumentException if the student or course is null
     */
    public boolean enrollStudent(Student student, Course course) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        // Check if the student is already enrolled
        if (course.getStudents().contains(student)) {
            return false;
        }

        // Check prerequisites
        if (!checkPrerequisites(student, course)) {
            throw new IllegalArgumentException("Student does not meet prerequisites for this course");
        }

        // Add student to course
        course.getStudents().add(student);
        
        // Add course to student's courses
        return student.addCourse(course);
    }

    /**
     * Checks if a student has completed all prerequisites for a course
     * 
     * @param student The student to check
     * @param course The course to check prerequisites for
     * @return true if prerequisites are met, false otherwise
     */
    private boolean checkPrerequisites(Student student, Course course) {
        // If no prerequisites, return true
        if (course.getPrerequisites() == null || course.getPrerequisites().isEmpty()) {
            return true;
        }

        // Check if the student has completed all prerequisites
        for (Course prerequisite : course.getPrerequisites()) {
            if (!student.getCourses().contains(prerequisite)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes a student from a course
     * 
     * @param student The student to remove
     * @param course The course to remove from
     * @return true if removal was successful, false otherwise
     * @throws IllegalArgumentException if the student or course is null
     */
    public boolean withdrawStudent(Student student, Course course) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        // Remove student from course
        boolean removedFromCourse = course.getStudents().remove(student);
        
        // Remove course from student
        boolean removedFromStudent = student.withdrawFromCourse(course.getCourseCode());
        
        return removedFromCourse && removedFromStudent;
    }

    /**
     * Assigns a faculty member to teach a course
     * 
     * @param faculty The faculty member to assign
     * @param course The course to assign to
     * @return true if assignment was successful, false otherwise
     * @throws IllegalArgumentException if the faculty or course is null
     */
    public boolean assignFaculty(Faculty faculty, Course course) throws IllegalArgumentException {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        // Assign faculty to course
        course.setTeacher(faculty);
        
        // Add course to faculty's courses
        try {
            faculty.assignCourse(course);
            return true;
        } catch (Exception e) {
            // Revert assignment if it fails
            course.setTeacher(null);
            throw new IllegalArgumentException("Failed to assign course: " + e.getMessage());
        }
    }

    /**
     * Gets all courses taught by a specific faculty member
     * 
     * @param faculty The faculty member to find courses for
     * @return List of courses taught by the faculty member
     * @throws IllegalArgumentException if faculty is null
     */
    public List<Course> getCoursesByFaculty(Faculty faculty) throws IllegalArgumentException {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }

        List<Course> facultyCourses = new ArrayList<>();
        for (Course course : courses) {
            if (faculty.equals(course.getTeacher())) {
                facultyCourses.add(course);
            }
        }
        return facultyCourses;
    }

    /**
     * Gets all courses that a student is enrolled in
     * 
     * @param student The student to find courses for
     * @return List of courses the student is enrolled in
     * @throws IllegalArgumentException if student is null
     */
    public List<Course> getCoursesByStudent(Student student) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        List<Course> studentCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getStudents().contains(student)) {
                studentCourses.add(course);
            }
        }
        return studentCourses;
    }

    /**
     * Gets all available courses
     * 
     * @return List of all courses
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Deletes a course from the system
     * 
     * @param course The course to delete
     * @return true if deletion was successful, false otherwise
     * @throws IllegalArgumentException if course is null
     */
    public boolean deleteCourse(Course course) throws IllegalArgumentException {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        // Remove course from all students
        for (Student student : course.getStudents()) {
            student.withdrawFromCourse(course.getCourseCode());
        }

        // Remove course from faculty
        Faculty teacher = course.getTeacher();
        if (teacher != null) {
            teacher.getCoursesTaught().remove(course);
        }

        // Remove course from system
        return courses.remove(course);
    }
}