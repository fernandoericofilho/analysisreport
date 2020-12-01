package com.example.analisysreport.batch.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportReader implements ItemReader<List<Path>> {

    @Value("${analysis.input.path}")
    public String inputPath;

    @Value("${analysis.input.type}")
    public String inputType;

    @Override
    public List<Path> read() {

        return findFiles(inputPath, inputType);

    }

    private List<Path> findFiles(@NonNull final String path, @NonNull final String fileType) {

        final var filesList = new ArrayList<Path>();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(
                Paths.get(path),
                p -> p.toString().endsWith(fileType) && p.toFile().isFile())) {
            paths.forEach(filesList::add);
        } catch (IOException e) {
            log.error("E=error finding files in directory, path={}, fileType={}", path, fileType, e);
        }

        return filesList;
    }

}