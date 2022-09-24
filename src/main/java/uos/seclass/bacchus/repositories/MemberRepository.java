package uos.seclass.bacchus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uos.seclass.bacchus.domains.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
    public Optional<Member> findById(String id);
}
