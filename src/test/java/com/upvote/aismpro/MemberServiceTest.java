package com.upvote.aismpro;

import com.upvote.aismpro.entity.Member;
import com.upvote.aismpro.repository.MemberRepository;
import com.upvote.aismpro.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Member.class, MemberService.class, MemberRepository.class})
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void joinTest() throws Exception {

        // member 객체 생성
        Member member = new Member();
        member.setName("kim");

        // 회원가입(join) 시켜봄
        Long saveId = memberService.join(member);

        // 저장한 회원과 id로 조회한 회원이 동일한지 검즌
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void doubleMemberException() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        memberService.join(member2);

        fail("예외 발생!");
    };

}
