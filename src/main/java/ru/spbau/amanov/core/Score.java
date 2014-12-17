package ru.spbau.amanov.core;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "score" , namespace = "ru.spbau.amanov.core")
@XmlSeeAlso({Student.class, Course.class})
@Entity
@Table(name = "score")
public class Score {

    @Id
    @SequenceGenerator(name="score_id_seq_gen", sequenceName="score_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO ,generator="score_id_seq_gen")
    @Column(name = "id")
    private int id;


    @Column(name = "score")
    private int score;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    public Score() {}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @XmlTransient
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}