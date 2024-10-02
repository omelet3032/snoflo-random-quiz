package org.snoflo.repository;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.snoflo.domain.Question;

import com.zaxxer.hikari.HikariDataSource;

public class FinderRepository {

    private HikariDataSource dataSource;

    public FinderRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    public void save(List<Question> list) {
        String insertSql = """
                INSERT INTO question (concept, description)
                VALUES (?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            for (Question question : list) {
                String concept = question.getConcept();
                String description = question.getDescription();

                preparedStatement.setString(1, concept);
                preparedStatement.setString(2, description);

                preparedStatement.addBatch();

            }
            
            preparedStatement.executeBatch();
           
            // preparedStatement.executeQuery();
            System.out.println("저장 완료");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTable() {

        try (Connection connection = dataSource.getConnection()) {

            String createTableSql = """
                    CREATE TABLE IF NOT EXISTS question (
                        id INTEGER IDENTITY,
                        concept VARCHAR(255) NOT NULL,
                        description VARCHAR(255) NOT NULL,
                        keyword1 VARCHAR(255),
                        keyword2 VARCHAR(255),
                        PRIMARY KEY (id)
                        )
                        """;
            PreparedStatement stmt = connection.prepareStatement(createTableSql);

            stmt.execute();

            System.out.println("테이블 생성 완료");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
