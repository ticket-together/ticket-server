package com.tickettogether.domain.reservation.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.reservation.domain.TicketSite;
import com.tickettogether.domain.reservation.exception.SiteBusinessException;
import com.tickettogether.domain.reservation.exception.SiteEmptyException;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.domain.reservation.domain.TicketSiteInfo;
import com.tickettogether.domain.reservation.exception.SiteUpdateException;
import com.tickettogether.domain.reservation.repository.SiteInfoRepository;
import com.tickettogether.domain.reservation.dto.ReservationDto;
import com.tickettogether.domain.reservation.repository.ReservationRepository;
import com.tickettogether.global.config.security.jwt.JwtConfig;
import com.tickettogether.global.config.security.utils.AES128;
import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.AuthorityException;
import com.tickettogether.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tickettogether.global.error.ErrorCode.PASSWORD_ENCRYPTION_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final static int MIN_COUNT = 1;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final SiteInfoRepository siteInfoRepository;
    private final JwtConfig jwtConfig;

    @Override
    public List<ReservationDto.GetResponse> getReservations(Member member) {
        return reservationRepository.findByMember(member).stream()
                .map(ReservationDto.GetResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto.SiteInfoGetResponse postSiteInfo(ReservationDto.SiteInfoPostRequest siteInfo, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(UserEmptyException::new);
        if (siteInfoRepository.countTicketSiteInfoByMemberAndTicketSite(member, TicketSite.of(siteInfo.getTicketSite())
                .orElseThrow(SiteEmptyException::new)) >= MIN_COUNT) {
            throw new SiteBusinessException();
        }
        try {
            siteInfo.setPassword(new AES128(jwtConfig.getPasswordKey()).encrypt(siteInfo.getPassword()));
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        return new ReservationDto.SiteInfoGetResponse(siteInfoRepository.save(siteInfo.toEntity(member)));
    }

    public ReservationDto.SiteInfoGetResponse getSiteInfo(Long tempMemberId, TicketSite siteName){
        Member member = memberRepository.findById(tempMemberId).orElseThrow(UserEmptyException::new);
        Optional<TicketSiteInfo> ticketSiteInfo = siteInfoRepository.findByMemberAndTicketSite(member, siteName);
        return ticketSiteInfo.map(ReservationDto.SiteInfoGetResponse::new).orElse(null);
    }

    @Transactional
    public ReservationDto.SiteInfoGetResponse updateSiteInfo(ReservationDto.SiteInfoPostRequest siteInfo, Long id){
        TicketSiteInfo ticketSiteInfo = siteInfoRepository.findById(id).orElseThrow(SiteEmptyException::new);

        if (!ticketSiteInfo.getTicketSite().getTicketSiteName().equals(siteInfo.getTicketSite())){
            throw new SiteUpdateException();
        }

        try {
            siteInfo.setPassword(new AES128(jwtConfig.getPasswordKey()).encrypt(siteInfo.getPassword()));
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        return new ReservationDto.SiteInfoGetResponse(ticketSiteInfo.updateTicketSiteInfo(siteInfo));
    }

    @Override
    @Transactional
    public String deleteSiteInfo(Long siteInfoId, Long memberId) {
        TicketSiteInfo ticketSiteInfo = siteInfoRepository.findById(siteInfoId).orElseThrow(SiteEmptyException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(UserEmptyException::new);

        if (!ticketSiteInfo.getMember().getId().equals(member.getId())) {
            throw new AuthorityException(ErrorCode.INVALID_USER_JWT);
        }

        siteInfoRepository.delete(ticketSiteInfo);
        return "ok";
    }
}
