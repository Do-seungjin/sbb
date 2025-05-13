package org.kosa.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.kosa.sbb.DataNotFoundException;
import org.kosa.sbb.answer.Answer;
import org.kosa.sbb.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
  private final QuestionRepository questionRepository;
  
  // 검색 기능 
  private Specification<Question> search(String kw) {
    return new Specification<>() {
      private static final long serialVersionUID = 3017219970922382893L;

      @Override
      public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
        query.distinct(true); // 중복을 제거
        Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
        Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
        Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
        return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
            cb.like(q.get("content"), "%" + kw + "%"), // 내용
            cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
            cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
            cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
      }
    };
  }

  public Page<Question> getList(int page, String kw) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    Specification<Question> spec = search(kw);
    return questionRepository.findAll(spec,pageable);
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
    if (question.getVoter().contains(siteUser)) {
      question.getVoter().remove(siteUser);
    } else {
      question.getVoter().add(siteUser);
    }
    this.questionRepository.save(question);
  }
}
