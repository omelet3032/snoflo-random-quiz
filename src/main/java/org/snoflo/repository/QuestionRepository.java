package org.snoflo.repository;

import java.util.List;

import org.snoflo.domain.CsvFileRow;

public interface QuestionRepository extends AppRepository {

    public List<CsvFileRow> findAll(String selectedFile);

    public List<String> findAllTable();
}
