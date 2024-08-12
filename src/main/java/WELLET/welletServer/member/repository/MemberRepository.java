package WELLET.welletServer.member.repository;

import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberSaveDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
