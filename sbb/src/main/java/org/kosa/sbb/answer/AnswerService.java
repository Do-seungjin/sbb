package org.kosa.sbb.answer;

import java.time.LocalDateTime;
import org.kosa.sbb.question.Question;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
  private final AnswerRepository answerRepository;

  public Answer create(Question question, String content) {
    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    answer.setQuestion(question);
    return answerRepository.save(answer);
  }

}
