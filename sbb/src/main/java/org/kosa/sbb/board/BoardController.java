package org.kosa.sbb.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {


  private final BoardService boardService;


  @GetMapping("/list")
  public String list(Model model) {
    List<Board> boardList = boardService.getList();
    model.addAttribute("boardList", boardList);
    return "board_list";
  }

  @GetMapping("/create")
  public String boardCreate(BoardForm boardForm) {
    return "board_form";
  }

  @ResponseBody
  @PostMapping("/create")
  public Map<String, Object> boardCreate(@RequestBody @Valid BoardForm boardForm,
      BindingResult bindingResult) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (bindingResult.hasErrors()) {
      map.put("res_code", "400");
      StringBuilder errorMessages = new StringBuilder();
      for (ObjectError error : bindingResult.getAllErrors()) {
        errorMessages.append(error.getDefaultMessage()).append("<br>");
      }
      map.put("res_msg", errorMessages.toString().trim());
      return map;
    }
    if (boardService.create(boardForm.getTitle(), boardForm.getContent()) == null) {
      map.put("res_code", "400");
      map.put("res_msg", "게시글 등록에 실패하였습니다.");
    } else {
      map.put("res_code", "200");
      map.put("res_msg", "게시글 등록에 성공하였습니다.");
    }
    return map;
  }

  @GetMapping(value = "/detail/{bno}")
  public String detail(Model model, @PathVariable("bno") Integer bno, BoardForm boardForm) {
    Board board = boardService.getBoard(bno);
    model.addAttribute("board", board);
    return "board_detail";
  }
  
  @GetMapping(value = "/update/{bno}")
  public String update(Model model, @PathVariable("bno") Integer bno) {
    Board board = boardService.getBoard(bno);
    model.addAttribute("board", board);
    return "board_update_form";
  }
  
  @ResponseBody
  @PostMapping("/update/{bno}")
  public Map<String, Object> questionUpdate(@RequestBody @Valid BoardForm boardForm,
      BindingResult bindingResult, @PathVariable("bno") Integer bno) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (bindingResult.hasErrors()) {
      map.put("res_code", "400");
      StringBuilder errorMessages = new StringBuilder();
      for (ObjectError error : bindingResult.getAllErrors()) {
        errorMessages.append(error.getDefaultMessage()).append("<br>");
      }
      map.put("res_msg", errorMessages.toString().trim());
      return map;
    }
    if (boardService.update(boardForm.getTitle(), boardForm.getContent(), bno) == null) {
      map.put("res_code", "400");
      map.put("res_msg", "질문 수정에 실패하였습니다.");
    } else {
      map.put("res_code", "200");
      map.put("res_msg", "질문 수정에 성공하였습니다.");
    }
    return map;

  }
  
  @ResponseBody
  @DeleteMapping("/delete/{bno}")
  public Map<String, Object> boardDelete(@PathVariable("bno") Integer bno) {
    Map<String, Object> map = new HashMap<String, Object>();
    Board board = boardService.getBoard(bno);
    if (board == null) {
      map.put("res_code", "400");
      map.put("res_msg", "질문 삭제에 실패하였습니다.");
    } else {
      boardService.delete(bno);
      map.put("res_code", "200");
      map.put("res_msg", "질문 삭제에 성공하였습니다.");
    }
    return map;
  }
  // @GetMapping("detailView")
  // public String detailView(Model model, String bno) {
  // Board boardInfo = boardService.getBoard(bno);
  // if (boardInfo == null) {
  // return "redirect:/";
  // }
  //
  // model.addAttribute("boardInfo", boardInfo);
  //
  // return "board/detailView";
  // }
  //
  // @RequestMapping("updateForm2")
  // public String updateForm(Model model, String bno) {
  // // 멤버 userid를 이용하여 멤버의 상세정보를 얻는다
  // Board boardInfo = boardService.getBoard(bno);
  // if (boardInfo == null) {
  // return "redirect:/";
  // }
  //
  // model.addAttribute("boardInfo", boardInfo);
  //
  // return "board/updateForm2";
  // }
  //
  // @PostMapping("update2")
  // @ResponseBody
  // public Map<String, Object> update(@RequestBody Board board) throws Exception {
  // Map<String, Object> result = new HashMap<String, Object>();
  // try {
  // Board boardInfo = boardService.update(board);
  // result.put("status", "ok");
  // } catch (Exception e) {
  // result.put("errorMessage", e.getMessage());
  // result.put("status", "error");
  // }
  // return result;
  // }


}
