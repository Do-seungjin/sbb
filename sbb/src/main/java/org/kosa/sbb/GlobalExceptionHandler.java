package org.kosa.sbb;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
      Map<String, Object> map = new HashMap<>();
      map.put("res_code", "400");
      map.put("res_msg", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
      return map;
  }
}
