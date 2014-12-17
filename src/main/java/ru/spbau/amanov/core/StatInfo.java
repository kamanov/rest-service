package ru.spbau.amanov.core;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@XmlRootElement(name = "stat_info")
@Entity
@Table(name = "history")
public class StatInfo {

    @Id
    @SequenceGenerator(name="history_id_seq_gen", sequenceName="history_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO ,generator="history_id_seq_gen")
    @Column(name = "id")
    private int id;

    @Column (name = "operation")
    private String operation;

    @Column (name = "resource")
    private String resource;

    @Column (name = "date")
    Date date;

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
