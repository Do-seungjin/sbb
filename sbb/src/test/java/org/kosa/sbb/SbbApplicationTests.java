package org.kosa.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import org.junit.jupiter.api.Test;
import org.kosa.sbb.answer.AnswerRepository;
import org.kosa.sbb.question.QuestionRepository;
import org.kosa.sbb.question.QuestionService;
import org.kosa.sbb.user.SiteUser;
import org.kosa.sbb.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
class SbbApplicationTests {

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;
  
  @Autowired
  private QuestionService questionService;

  @Test
  void contextLoads() {}

  @Test
  void testExam() {
    int a = 10;
    int b = 20;
    int sum = add(a, b);

    // if (sum == 10) {
    // System.out.println("성공");
    // } else {
    // System.out.println("실패");
    // }
    assertEquals(30, sum);
  }

  private int add(int a, int b) {
    return a + b;
  }

  @Transactional
  @Test
  void testJpa() {
    // 1. 객체 생성 -> Question entity
    // 2. 생성된 객체를 디비에 추가(등록)을 한다 -> 저장소에 추가 -> QuestionRepository
    // Question q1 = new Question();
    // q1.setSubject("sbb가 무엇인가요?");
    // q1.setContent("sbb에 대해서 알고 싶습니다");
    // q1.setCreateDate(LocalDateTime.now());
    //
    // questionRepository.save(q1);
    //
    // Question q2 = new Question();
    // q2.setSubject("스프링 부트 모델 질문입니다.");
    // q2.setContent("id는 자동으로 생성되나요?");
    // q2.setCreateDate(LocalDateTime.now());
    //
    // questionRepository.save(q2);
    // List<Question> all = questionRepository.findAll();
    // assertEquals(2, all.size());
    //
    // Question q = all.get(0);
    // assertEquals("sbb가 무엇인가요?", q.getSubject());

    // Optional<Question> oq = questionRepository.findById(1);
    // if(oq.isPresent()) {
    // Question q = oq.get();
    // assertEquals("sbb가 무엇인가요?", q.getSubject());
    // }

    // Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
    // Question q = questionRepository.findByContent("sbb에 대해서 알고 싶습니다");
    // Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다");
    // Question q = questionRepository.findBySubjectLike("sbb");

    // List<Question> list = questionRepository.findBySubjectLike("%sb%");
    // if(list.size()>0) {
    // Question q = list.get(0);
    // assertEquals(38, q.getId());
    // }

    // 수정
    // Optional<Question> oq = questionRepository.findById(38);
    // if (oq.isPresent()) {
    // Question q = oq.get();
    // q.setSubject("수정된 제목");
    // questionRepository.save(q);
    // }

    // Optional<Question> oq = questionRepository.findById(38);
    // assertTrue(oq.isPresent());
    // Question q = oq.get();
    //
    // Answer a = new Answer();
    // a.setContent("네 자동으로 생성됩니다.");
    // a.setQuestion(q);
    // a.setCreateDate(LocalDateTime.now());
    // this.answerRepository.save(a);
    // Optional<Answer> oa = answerRepository.findById(1);
    // assertTrue(oa.isPresent());
    // Answer a = oa.get();
    // assertEquals(2, a.getQuestion().getId());
  }
  // @Test
  // void testQExam() {
  // for(int i=1 ; i<=300 ; i++) {
  // questionService.create(String.format("테스트 데이터 입니다:[%3d]", i), "내용 없음");
  // }
  // }
  @Autowired
  UserService userService;
  
  @Test
  void register() {
    SiteUser user = userService.create("hong", "hong@kosa.org", "1004");
    assertNotNull(user);
    assertNotEquals(0, user.getId());
    log.info("user.id = "+user.getId());
    log.info("user.id = {}",user.getId());
  }
}
