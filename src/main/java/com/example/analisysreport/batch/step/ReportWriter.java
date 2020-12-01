package com.example.analisysreport.batch.step;

import com.example.analisysreport.exception.GenericException;
import com.example.analisysreport.vo.ReportVO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.NonNull;

@Service
public class ReportWriter implements ItemWriter<ReportVO> {

	public static final String OUTPUT_FILE_NAME = "OUTPUT_FILE_";

	@Value("${analysis.output.path}")
	private String outputPath;

	@Value("${analysis.output.type}")
	private String outputType;

	@Override
	public void write(List<? extends ReportVO> items) throws Exception {
		items.forEach(this::generate);
	}

	private File generate(@NonNull final ReportVO reportVO) {

		final var filePath = outputPath + File.separator + OUTPUT_FILE_NAME + System.currentTimeMillis() + outputType;
		Path file = Paths.get(filePath);
		file.toFile().getParentFile().mkdirs();

		try {
			return Files.write(file, reportVO.toStringList(), StandardCharsets.UTF_8).toFile();
		} catch (IOException e) {
			throw new GenericException("E=Error generating report file");
		}
	}

}