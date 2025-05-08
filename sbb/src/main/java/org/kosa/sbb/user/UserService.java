package org.kosa.sbb.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public SiteUser create(String username, String email, String password) {
    // 회원 정보 객체 생성
    SiteUser user = SiteUser.of(username, passwordEncoder.encode(password), email);
    // 저장소에 회원 정보 등록
    userRepository.save(user);

    return user;
  }
}
