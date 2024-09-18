package org.snoflo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.snoflo.db.CsvFileReader;
import org.snoflo.repository.FinderRepository;
import org.snoflo.service.FinderService;
import org.snoflo.db.CsvFileConverter;
import org.snoflo.view.FinderView;

// csvFile을 세팅하는 도메인 컨트롤러
// file 선택후 save 메서드로 db에 csvfile을 저장하는 책임

public class FinderController extends AppController {

    private FinderView finderView;
    private CsvFileConverter dataConverter;

    public FinderController(CsvFileConverter dataConverter, FinderView finderView) {
        this.dataConverter = dataConverter;
        this.finderView = finderView;
    }
    
    public void sendSelectedFileToService() {
        Path selectedFile = selectFile();
        FinderService finderService = new FinderService(new FinderRepository());
        finderService.saveFile(selectedFile);
        // dataConverter.convertDataForDomain(selectedFile);
        
        // CsvFileReader csvFileReader = new CsvFileReader();
        // CsvFileReader csvFileReader = CsvFileReader.getInstance();
        // csvFileReader.readCsvFile(selectedFile);



        //QuestionRepository repository = repository.save(selectedFile);

    }

    private Path selectFile() {
        Path selectedFolder = executeFindFolder();
        Path selectedFile = executeFindFile(selectedFolder);

        return selectedFile;
    }

    private Path executeFindFolder() {
        finderView.showPromptFolder();

        List<Path> folderList = getFolderList();

        finderView.showSelectFolder(folderList);

        int number = scanner.nextInt();
        scanner.nextLine();

        Path selectedFolder = folderList.get(number);
        return selectedFolder;
    }

    private Path executeFindFile(Path selectedFolder) {
        finderView.showPromptCsvFile();

        List<Path> fileList = getFileList(selectedFolder);

        finderView.showSelectCsvFile(fileList);
        int number = scanner.nextInt();
        scanner.nextLine();

        Path selectedFile = fileList.get(number);
        return selectedFile;
    }

    // 파일, 폴더 검색 기능
    private List<Path> getFolderList() {
        Path dirPath = Paths.get(System.getProperty("user.dir"));
        int maxDepth = 2;

        try {
            return Files.walk(dirPath, maxDepth).filter(Files::isDirectory).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<Path> getFileList(Path selectedFolder) {

        try {
            return Files.list(selectedFolder)
                    .filter(file -> Files.isRegularFile(file))
                    .filter(path -> path.toString().endsWith(".csv"))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
