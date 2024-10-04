package org.snoflo;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.snoflo.domain.Question;
import org.snoflo.function.CsvFileFinder;
import org.snoflo.function.CsvFileReader;
import org.snoflo.function.RandomQuestion;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
       
        Question question = new Question();

        CsvFileReader csvFileReader = new CsvFileReader(question);
        CsvFileFinder csvFileFinder = new CsvFileFinder();

        List<Path> folderList = csvFileFinder.getFolderList();
        List<Path> fileList = csvFileFinder.getFileList(folderList.get(0));

        Path selectedFile = fileList.get(0);

        List<Question> list = csvFileReader.readCsvFile(selectedFile);

        
        // for (Question question2 : list) {
        //     System.out.println(question2.toString());
        //     System.out.println();
        // }


        RandomQuestion randomQuestion = new RandomQuestion();

        randomQuestion.playRandomQuiz(list);

        
    }

    
}
