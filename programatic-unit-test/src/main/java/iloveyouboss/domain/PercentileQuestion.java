package iloveyouboss.domain;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
@DiscriminatorValue(value="percentile")
public class PercentileQuestion extends Question {
	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(name="AnswerChoice",
		joinColumns=@JoinColumn(name="question_id"))
	private List<String> answerChoices;

	public PercentileQuestion() {}
	public PercentileQuestion(String text, String[] answerChoices) {
		super(text);
		this.answerChoices = Arrays.asList(answerChoices);
	}

	public PercentileQuestion(Integer id, String text, String[] answerChoices) {
		super(id, text);
		this.answerChoices = Arrays.asList(answerChoices);
	}

	public List<String> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<String> answerChoices) {
		this.answerChoices = answerChoices;
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected <= actual;
	}
}

