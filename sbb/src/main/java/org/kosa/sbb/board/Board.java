package org.kosa.sbb.board;

import java.time.LocalDateTime;
import java.util.List;
import org.kosa.sbb.answer.Answer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Board {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bno;
  @Column
  private String title;// 제목
  @Column
  private String content;// 내용
  @Column
  private String writer;// 작성자
  @Column
  private String passwd;// 게시물 비번
  @Column
  private LocalDateTime reg_date;// 작성일
  @Column
  private Integer view_count;// 보기 수
}
