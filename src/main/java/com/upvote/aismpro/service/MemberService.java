package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.Member;
import com.upvote.aismpro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 이 어노테이션 붙은 클래스나 메소드에 트랜젝션 적용.
// 메소드 호출시에 트랜젝션 시작, 종료시 커밋
// Member 관련 비즈니스 로직
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    MemberRepository memberRepo;

    // 생성자로 주입
//    private final MemberRepository memberRepo;
//    public MemberService(MemberRepository memberRepo) {
//        this.memberRepo = memberRepo;
//    }

    // 회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepo.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepo.findByName(member.getName());
        // exception 발생을 어떻게 처리할까
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepo.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepo.findOne(memberId);
    }
}
