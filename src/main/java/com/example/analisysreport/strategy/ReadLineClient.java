package com.example.analisysreport.strategy;

import com.example.analisysreport.vo.ReportVO;
import org.springframework.stereotype.Service;
import lombok.NonNull;

@Service
public class ReadLineClient implements ReadLine {

    @Override
    public ReportVO readFileLine(@NonNull final ReportVO file, @NonNull final String line) {
        return file.incrementClientsAmount();
    }

}
