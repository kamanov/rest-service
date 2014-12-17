package ru.spbau.amanov.core;

import javax.persistence.*;
import java.util.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "course")
@Entity
@Table(name = "course")
public class Course {
    @Id
    @SequenceGenerator(name="course_id_seq_gen", sequenceName="course_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO ,generator="course_id_seq_gen")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="course")
    private List<Score> scores = new ArrayList<Score>();

    public Course() {}

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @XmlTransient
    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String toString() {
        return getName();
    }
}