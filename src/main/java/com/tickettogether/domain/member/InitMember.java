package com.tickettogether.domain.member;

import com.tickettogether.domain.member.domain.Keyword;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;
    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitMemberService {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Member member = Member.builder().email("test@naver.com").name("세미").role(Role.USER).build();
            Keyword keyword1 = new Keyword("영화");
            Keyword keyword2 = new Keyword("공연");

            em.persist(member);
            em.persist(keyword1);
            em.persist(keyword2);
        }
    }
}
