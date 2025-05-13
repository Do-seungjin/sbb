package org.kosa.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.kosa.sbb.DataNotFoundException;
import org.kosa.sbb.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
  private final QuestionRepository questionRepository;

  public Page<Question> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    return questionRepository.findAll(pageable);
  }

  public Question getQuestion(Integer id) {
    Optional<Question> question = questionRepository.findById(id);
    if (question.isPresent()) {
      return question.get();
    } else {
      throw new DataNotFoundException("question not found");
    }
  }
  
  // 등록 기능 
  public Question create(String subject, String content, SiteUser user) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());
    q.setAuthor(user);
    Question result = questionRepository.save(q);
    return result;
  }
  
  // 수정 기능 
  public Question update(String subject, String content, Integer id) {
    Optional<Question> oq = questionRepository.findById(id);
    if (oq.isPresent()) {
      Question question = oq.get(); // Optional에서 꺼냄
      question.setSubject(subject);
      question.setContent(content);
      question.setModifyDate(LocalDateTime.now());
      Question result = questionRepository.save(question);
      return result;
    } else {
      throw new DataNotFoundException("question not found");
    }
  }
  
  // 삭제 기능 
  public void delete(Integer id) {
    Optional<Question> oq = questionRepository.findById(id);
    if (oq.isPresent()) {
      Question question = oq.get();
      questionRepository.delete(question);
    } else {
      throw new DataNotFoundException("question not found");
    }
  }
  
  // 추천 기능 
  public void vote(Question question, SiteUser siteUser) {
    question.getVoter().add(siteUser);
    this.questionRepository.save(question);
  }
}
