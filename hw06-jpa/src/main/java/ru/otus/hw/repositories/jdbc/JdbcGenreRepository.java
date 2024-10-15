package ru.otus.hw.repositories.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {

        return jdbc.query("select id, name from genres", new GenreRowMapper());
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {

        return jdbc.query(
            "select id, name from genres where id in (:ids)",
            Map.of("ids", ids),
            new GenreRowMapper()
        );
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {

            return new Genre(
                rs.getLong("id"),
                rs.getString("name")
            );
        }
    }
}
