package WELLET.welletServer.kakaologin.Repository;

import WELLET.welletServer.kakaologin.domain.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<KakaoUser, Long> {
    KakaoUser findByKakaoId(Long kakaoId);
    boolean existsByKakaoId(Long kakaoId);
}
