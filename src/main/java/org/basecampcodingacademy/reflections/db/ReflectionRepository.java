package org.basecampcodingacademy.reflections.db;

import org.basecampcodingacademy.reflections.domain.Reflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReflectionRepository {
    @Autowired
    public JdbcTemplate jdbc;

    public List<Reflection> all() {
        return jdbc.query("SELECT id, date FROM reflections", this::mapper);
    }

    public Reflection create(Reflection reflection) {
        return jdbc.queryForObject(
                "INSERT INTO reflections (date) VALUES (?) RETURNING id, date",
                this::mapper,
                reflection.date
        );
    }

    public Reflection find(LocalDate localDate) {
        try {
            return jdbc.queryForObject("SELECT id, date FROM reflections WHERE date = ? LIMIT 1", this::mapper, localDate);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Reflection find(Integer id) {
        return jdbc.queryForObject("SELECT id, date FROM reflections WHERE id = ?", this::mapper, id);
    }catch (EmptyResultDataAccessException ex) {
            return null;
        }

    public Reflection update(Reflection reflection) {
        return jdbc.queryForObject(
                "UPDATE reflections SET date = ? WHERE id = ? RETURNING id, date",
                this::mapper, reflection.date, reflection.id);
    }

    public void delete(Integer id) {
        jdbc.query("DELETE FROM reflections WHERE id = ? RETURNING id, date", this::mapper, id);
    }

    private Reflection mapper(ResultSet resultSet, int i) throws SQLException {
        return new Reflection(
                resultSet.getInt("id"),
                resultSet.getDate("date").toLocalDate(),
                null
        );
    }
}
