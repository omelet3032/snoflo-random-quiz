package org.snoflo.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.snoflo.domain.CsvFileRow;

public class CsvFileParser {

	public List<CsvFileRow> readCsvFile(Path selectedFile) {

		List<CsvFileRow> questionList = new ArrayList<>();


		try (BufferedReader reader = Files.newBufferedReader(selectedFile)) {

			String line = reader.readLine();

			String concept = "";
			String description = "";
			StringBuilder descriptionBuilder = new StringBuilder();

			while ((line = reader.readLine()) != null) {

				CsvFileRow question = new CsvFileRow();

				String[] values = line.split(",");

				descriptionBuilder.setLength(0);

				if (line.contains("\"")) {

					if (isQuoteMiddleIndex(line)) {

						concept = values[0];
						description = appendDescriptionColumn(descriptionBuilder, values);

					} else {

						question = questionList.getLast();
						description = appendDescriptionColumn(question, descriptionBuilder, values);
						question.setDescription(description);

						questionList.set(questionList.size() - 1, question);
						continue;

					}

				} else {

					concept = values[0];
					description = values[1];

				}

				question.setConcept(concept);
				question.setDescription(description);

				questionList.add(question);

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return questionList;
	}

	private boolean isQuoteMiddleIndex(String line) {
		int quoteIndex = line.indexOf("\"");
		int lastIndex = line.length() - 1;

		return quoteIndex != lastIndex;
	}

	private String appendDescriptionColumn(
			CsvFileRow question,
			StringBuilder descriptionBuilder,
			String[] values) {

		String beforeDescription = question.getDescription();
		descriptionBuilder = descriptionBuilder.append(beforeDescription).append(", ");

		return appendDescriptionColumn(descriptionBuilder, values);
	}

	private String appendDescriptionColumn(
			StringBuilder descriptionBuilder,
			String[] values) {

		int startIndex = (descriptionBuilder.length() == 0) ? 1 : 0;

		for (int i = startIndex; i < values.length; i++) {

			if (values[i].contains("\"")) {
				values[i] = values[i].replaceAll("\"", "");
			}

			descriptionBuilder.append(values[i]);

			if (i != values.length - 1) {
				descriptionBuilder.append(",");
			}

		}

		return descriptionBuilder.toString();

	}
}