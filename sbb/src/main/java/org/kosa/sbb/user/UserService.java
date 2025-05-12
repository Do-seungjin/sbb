package org.kosa.sbb.user;

import java.util.Optional;
import org.kosa.sbb.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SiteUser create(String username, String email, String password) {
    // 회원 정보 객체 생성
    SiteUser user = SiteUser.of(username, passwordEncoder.encode(password), email);
    // 저장소에 회원 정보 등록
    userRepository.save(user);  
    return user;
  }
  public SiteUser getUser(String username) {
    Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
    if (siteUser.isPresent()) {
        return siteUser.get();
    } else {
        throw new DataNotFoundException("siteuser not found");
    }
}
}
