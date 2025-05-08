package org.kosa.sbb.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.kosa.sbb.DataNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardRepository boardRepository;
  
  public List<Board> getList() {
    return boardRepository.findAll();
  }
  
  public Board getBoard(Integer bno) {
    Optional<Board> board = boardRepository.findById(bno);
    if (board.isPresent()) {
      return board.get();
    } else {
      throw new DataNotFoundException("board not found");
    }
  }
  
  public Board create(String subject, String content) {
    Board b = new Board();
    b.setTitle(subject);
    b.setContent(content);
    b.setReg_date(LocalDateTime.now());
    Board result = boardRepository.save(b);
    return result;
  }
  
  public Board update(String title, String content, Integer bno) {
    Optional<Board> ob = boardRepository.findById(bno);
    if (ob.isPresent()) {
      Board board = ob.get(); // Optional에서 꺼냄
      board.setTitle(title);
      board.setContent(content);
      Board result = boardRepository.save(board);
      return result;
    } else {
      throw new DataNotFoundException("question not found");
    }
  }
  
  public void delete(Integer bno) {
    Optional<Board> ob = boardRepository.findById(bno);
    if (ob.isPresent()) {
      Board board= ob.get();
      boardRepository.delete(board);
    } else {
      throw new DataNotFoundException("board not found");
    }
  }
//  public Board create(String subject, String content) {
//    Question q = new Question();
//    q.setSubject(subject);
//    q.setContent(content);
//    q.setCreateDate(LocalDateTime.now());
//    Question result = questionRepository.save(q);
//    return result;
//  }

}
