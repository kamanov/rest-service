package ru.spbau.amanov.core;

import org.hibernate.HibernateException;

import java.util.List;


public class ScoreDAO extends AbstractDAO {

    public void create(Score score) throws HibernateException {
        super.saveOrUpdate(score);
    }

    public void delete(Score score) throws HibernateException {
        super.delete(score);
    }

    public void update(Score score) throws HibernateException {
        super.saveOrUpdate(score);
    }

    public List findAll() throws HibernateException{
        return super.findAll(Score.class);
    }

    public Score getScore(Student student, Course course) {
        if (student == null || course == null)
            return null;
        Score resScore = null;
        for (Score score : student.getScores()) {
            if (score.getCourse().getId() == course.getId()) {
                resScore = score;
                    break;
            }
        }
        return resScore;
    }

}
