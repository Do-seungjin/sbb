package org.kosa.sbb.question;


import java.util.HashMap;
import java.util.Map;
import org.kosa.sbb.answer.AnswerForm;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

  // @Autowired (RequiredArgsController로 생략 가능)
  private final QuestionService questionService;


  @GetMapping("/list")
  // @ResponseBody
  public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<Question> paging = questionService.getList(page);
    model.addAttribute("paging", paging);
    int startPage = ((page) / 10) * 10;
    int endPage = ((page) / 10) * 10 + 9;
    if (endPage > paging.getTotalPages())
      endPage = paging.getTotalPages() - 1;
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);
    return "question_list";
  }

  @GetMapping(value = "/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  @GetMapping(value = "/update/{id}")
  public String update(Model model, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_update_form";
  }

  @ResponseBody
  @PostMapping("/update/{id}")
  public Map<String, Object> questionUpdate(@RequestBody @Valid QuestionForm questionForm,
      BindingResult bindingResult, @PathVariable("id") Integer id) {
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
    if (questionService.update(questionForm.getSubject(), questionForm.getContent(), id) == null) {
      map.put("res_code", "400");
      map.put("res_msg", "질문 수정에 실패하였습니다.");
    } else {
      map.put("res_code", "200");
      map.put("res_msg", "질문 수정에 성공하였습니다.");
    }
    return map;

  }

  @ResponseBody
  @DeleteMapping("/delete/{id}")
  public Map<String, Object> questionDelete(@PathVariable("id") Integer id) {
    Map<String, Object> map = new HashMap<String, Object>();
    Question question = questionService.getQuestion(id);
    if (question == null) {
      map.put("res_code", "400");
      map.put("res_msg", "질문 삭제에 실패하였습니다.");
    } else {
      questionService.delete(id);
      map.put("res_code", "200");
      map.put("res_msg", "질문 삭제에 성공하였습니다.");
    }
    return map;
  }


  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  @ResponseBody
  @PostMapping("/create")
  public Map<String, Object> questionCreate(@RequestBody @Valid QuestionForm questionForm,
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

    if (questionService.create(questionForm.getSubject(), questionForm.getContent()) == null) {
      map.put("res_code", "400");
      map.put("res_msg", "질문 등록에 실패하였습니다.");
    } else {
      map.put("res_code", "200");
      map.put("res_msg", "질문 등록에 성공하였습니다.");
    }

    return map;

  }

}
