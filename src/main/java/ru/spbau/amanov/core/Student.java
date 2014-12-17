package ru.spbau.amanov.core;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "student")
@Entity
@Table(name = "student")
public class Student {
    @Id
    @SequenceGenerator(name="student_id_seq_gen", sequenceName="student_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO ,generator="student_id_seq_gen")
    @Column(name = "id")
    private int id;

    @Column(name = "last_name")
    private String lastName;


    @OneToMany(fetch = FetchType.EAGER, mappedBy="student")
    private List<Score> scores = new ArrayList<Score>();

    public Student() {}

    @XmlTransient
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String toString() {
        return getLastName();
    }

}
