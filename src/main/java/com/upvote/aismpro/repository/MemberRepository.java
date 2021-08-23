package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
// Member Entity 저장하고 관리하는 repo
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findOne(Long id);
    List<Member> findByName(String name);
    //public List<Member> findAll();


    /*
    // 엔티티 매니저 팩토리 직접 주입
    @PersistenceUnite
    EntitiyManagerFactory emf;
    */

    /*
    // 엔티티 매니저 주입
    @PersistenceContext
    private EntityManager em;

    // member entity 저장(영속화)
    @Override
    public void save(Member member) {
        em.persist(member);
    }

    // member PK로 member 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // JPQL로 모든 멤버 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // JPQL 이름으로 member 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
    */
}
