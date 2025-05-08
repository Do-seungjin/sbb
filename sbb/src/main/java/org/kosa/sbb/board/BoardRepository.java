package org.kosa.sbb.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer>{

  @Query(value = "SELECT * FROM board ORDER BY reg_date DESC LIMIT :start, :count", nativeQuery = true)
  List<Board> findListByPaging(@Param("start") int start, @Param("count") int count);

}
