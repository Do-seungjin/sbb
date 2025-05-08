package org.kosa.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@GetMapping("/sbb")
	@ResponseBody
	public String index() {
		System.out.println("index....");
		log.trace("trace 로그...");
        log.debug("debug 로그...");
        log.info("info 로그...");
        log.warn("warn 로그...");
        log.error("error 로그...");
        
        // @ResponseBody 이 없을 때는 static/templates/index.html 문서를 의미
        // @ResponseBody 있으면 순수한 값이 브라우저로 리턴 
		return "안녕하세요 sbb에 오신 것을 환영합니다.";
	}
	
	@GetMapping("/")
	public String root() {
	  return "redirect:/question/list";
	}
	
}
