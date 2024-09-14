package org.snoflo.service;

import java.util.List;

import org.snoflo.dto.CsvFileDto;
import org.snoflo.model.Question;
import org.snoflo.repository.DataConverter;
import org.snoflo.repository.QuestionDataConverter;

public class QuestionServiceImpl implements QuestionService {

    private DataConverter dataConverter;

    public QuestionServiceImpl (DataConverter dataConverter) {
        this.dataConverter = dataConverter;
    }

    // 추후 옵서녈 도입
	@Override
	public Question findConceptById(int id) {
        List<Question> list = dataConverter.getData();

        for (Question concept : list) {
            if (concept.getId() == id) {
                return concept;
            }
        }
        return null;
	}

    @Override
    public List<Question> findAll() {
        List<Question> list = dataConverter.getData();

        return list;
        
    }


    
    

}
