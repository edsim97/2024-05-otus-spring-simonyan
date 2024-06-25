package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        final ClassLoader classLoader = getClass().getClassLoader();

        try (var questionsCsvStream = classLoader.getResourceAsStream(fileNameProvider.getTestFileName())) {

            if (questionsCsvStream == null) {

                throw new QuestionReadException("CSV resource with questions not found.");
            }
            return readQuestionsFromCsv(questionsCsvStream);
        } catch (IOException e) {

            throw new QuestionReadException("Error occurred while reading questions from CSV resource.", e);
        }
    }

    private List<Question> readQuestionsFromCsv(InputStream questionsCsvStream) throws IOException {

        try (var streamReader = new InputStreamReader(questionsCsvStream)) {

            return new CsvToBeanBuilder<QuestionDto>(streamReader)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withSkipLines(1)
                .build()
                .stream()
                .map(QuestionDto::toDomainObject)
                .collect(Collectors.toList());
        }
    }
}
