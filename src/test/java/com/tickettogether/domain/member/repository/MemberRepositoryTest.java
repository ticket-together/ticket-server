package com.tickettogether.domain.member.repository;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void db_테스트_롤백() throws Exception{
        //given
        Member member = new Member("test@test.com","1234","minnie","url", Role.USER);

        //when
        memberRepository.save(member);
        List<Member> all = memberRepository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(1);
    }
}
