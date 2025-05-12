package org.kosa.sbb.answer;


import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kosa.sbb.question.Question;
import org.kosa.sbb.question.QuestionService;
import org.kosa.sbb.user.SiteUser;
import org.kosa.sbb.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final UserService userService;
  
  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  @PostMapping("/create/{id}")
  public Map<String, Object> createAnswer(Model model, @PathVariable("id") Integer id,
      @RequestBody @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
    Map<String, Object> map = new HashMap<String, Object>();
    Question question = questionService.getQuestion(id);
    SiteUser siteUser = this.userService.getUser(principal.getName());
    // if(bindingResult.hasErrors()) {
    // model.addAttribute("question",question);
    // return "question_detail";
    // }
    // answerService.create(question,answerForm.getContent());
    // return String.format("redirect:/question/detail/%s", id);
    if (bindingResult.hasErrors()) {
      map.put("res_code", "400");
      StringBuilder errorMessages = new StringBuilder();
      for (ObjectError error : bindingResult.getAllErrors()) {
        errorMessages.append(error.getDefaultMessage()).append("<br>");
      }
      map.put("res_msg", errorMessages.toString().trim());
      return map;
    }
    if (answerService.create(question, answerForm.getContent(),siteUser) == null) {
      map.put("res_code", "400");
      map.put("res_msg", "답변 등록에 실패하였습니다.");
    } else {
      map.put("res_code", "200");
      map.put("res_msg", "답변 등록에 성공하였습니다.");
    }
    return map;
  }

  @ResponseBody
  @GetMapping("list/{id}")
  public List<Map<String, String>> answerList(@PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);
    List<Answer> answers = question.getAnswerList();
    List<Map<String, String>> result = new ArrayList<>();
    for (Answer a : answers) {
      Map<String, String> map = new HashMap<>();
      map.put("content", a.getContent());
      map.put("createDate",
          a.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
      result.add(map);
    }
    return result;
  }



}
