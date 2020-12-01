package com.example.analisysreport.batch.step;

import com.example.analisysreport.exception.GenericException;
import com.example.analisysreport.factory.LineFactory;
import com.example.analisysreport.strategy.ReadLine;
import com.example.analisysreport.vo.ReportVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import lombok.NonNull;

@Service
public class ReportProcessor implements ItemProcessor<List<Path>, ReportVO> {

	public static final String COMPLETE = "_COMPLETE";

	@Autowired
	public LineFactory lineFactory;

	@Override
	public ReportVO process(List<Path> paths) throws Exception {

		if (!paths.isEmpty()) {
			ReportVO reportVO = ReportVO.builder()
					.clientsAmount(0)
					.salesManAmount(0)
					.totalSalesBySalesMan(new HashMap<>())
					.build();

			paths.forEach(filePath -> processLine(filePath, reportVO));
			return reportVO;
		}
		return null;
	}

	private void processLine(@NonNull final Path filePath, @NonNull final ReportVO reportVO) {

		final var file = filePath.toFile();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.lines().forEach(line -> processLineByCode(reportVO, line));
		} catch (FileNotFoundException e) {
			throw new GenericException("E=error file not found");
		} catch (IOException e) {
			throw new GenericException("E=error with input or output");
		} finally {
			file.renameTo(new File(file.getAbsolutePath() + System.currentTimeMillis() + COMPLETE));
		}
	}

	private void processLineByCode(@NonNull final ReportVO reportVO, @NonNull final String line) {

		final ReadLine readLine = this.lineFactory.getDataLineProcessor(line);
		readLine.validate(line);
		readLine.readFileLine(reportVO, line);
	}

}
