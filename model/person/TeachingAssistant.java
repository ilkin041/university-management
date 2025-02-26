package model.person;

import java.util.ArrayList;

import model.Course;

public class TeachingAssistant {
    private Student student;
    private ArrayList<Course> coursesAssisted;
    private Faculty supervisor;
    private String evaluation;

    public TeachingAssistant(Student student, ArrayList<Course> coursesAssisted, Faculty supervisor, String evaluation) {
        this.student = student;
        this.coursesAssisted = coursesAssisted;
        this.supervisor = supervisor;
        this.evaluation = evaluation;
    }

    public Student getStudent() { return student; }
    public ArrayList<Course> getCoursesAssisted() { return coursesAssisted; }
    public Faculty getSupervisor() { return supervisor; }
    public String getEvaluation() { return evaluation; }

    public void setStudent(Student student) { this.student = student; }
    public void setCoursesAssisted(ArrayList<Course> coursesAssisted) { this.coursesAssisted = (coursesAssisted != null) ? new ArrayList<>(coursesAssisted) : new ArrayList<>(); }
    public void setSupervisor(Faculty supervisor) { this.supervisor = supervisor; }
    public void setEvaluation(String evaluation) { this.evaluation = evaluation; }

    public boolean addAssistedCourse(Course course) {
        if (course != null) {
            coursesAssisted.add(course);
            return true;
        }
        return false;
    }

    public boolean withdrawFromAssistedCourse(int courseId) {
        return coursesAssisted.removeIf(course -> course.getCourseCode() == courseId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", supervisor=").append(supervisor != null ? supervisor.getFullName() : "N/A")
          .append(", evaluationReview='").append(evaluation != null ? evaluation : "N/A").append('\'')
          .append(", coursesAssisted=").append(coursesAssisted.size()).append(" courses assisted")
          .append('}');
        return sb.toString();
    }
}
