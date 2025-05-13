package org.kosa.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;
import org.kosa.sbb.question.Question;
import org.kosa.sbb.user.SiteUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {

  // @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
  // @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq", allocationSize = 1)
  // private Integer id;

  // @Lob
  // @Column
  // private String content;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @ManyToOne
  private Question question;
  
  @ManyToOne
  private SiteUser author;
  
  private LocalDateTime modifyDate;
  
  @ManyToMany
  Set<SiteUser> voter;
}
