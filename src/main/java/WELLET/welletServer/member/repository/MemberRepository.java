package WELLET.welletServer.member.repository;

import WELLET.welletServer.kakaologin.domain.KakaoUser;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberSaveDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(UUID username);
    Optional<Member> findByKakaoId(Long kakaoId);
//    boolean existsByKakaoId(Long kakaoId);
}
