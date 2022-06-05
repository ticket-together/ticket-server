package com.tickettogether.domain.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum TicketSite {
    ELEVEN("11번가"),
    MELON("멜론티켓"),
    INTER_PARK("인터파크");

    private final String ticketSiteName;

    public static Optional<TicketSite> of(String ticketSiteName) {
        return Arrays.stream(TicketSite.values())
                .filter(t -> t.getTicketSiteName().equals(ticketSiteName))
                .findAny();
    }
}
