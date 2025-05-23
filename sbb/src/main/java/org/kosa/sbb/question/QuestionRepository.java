package org.kosa.sbb.question;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findBySubject(String subject);
	List<Question> findBySubjectLike(String subject);
	Question findByContent(String content);
	Question findBySubjectAndContent(String subject, String content);
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
