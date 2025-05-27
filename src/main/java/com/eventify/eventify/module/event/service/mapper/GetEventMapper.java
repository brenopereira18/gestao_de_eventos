package com.eventify.eventify.module.event.service.mapper;

import com.eventify.eventify.module.event.model.dto.*;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.SectorEntity;
import com.eventify.eventify.module.user.model.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GetEventMapper {

    public GetEventResponseDTO toResponse(EventEntity entity) {
        return GetEventResponseDTO.builder()
            .name(entity.getName())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .coverPhotoUrl(entity.getCoverPhotoUrl())
            .socialNetworkOfTheEvent(entity.getSocialNetworkOfTheEvent())
            .eventCategory(entity.getEventCategory())
            .description(entity.getDescription())
            .address(entity.getAddress())
            .city(entity.getCity())
            .state(entity.getState())
            .sectors(entity.getSectors().stream()
                .map(this::mapToSector)
                .toList())
            .build();
    }

    public GetPendingEventResponseDTO toPendingResponse(EventEntity entity) {
        return new GetPendingEventResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getCoverPhotoUrl(),
            entity.getSocialNetworkOfTheEvent(),
            entity.getEventCategory(),
            entity.getDescription(),
            entity.getAddress(),
            entity.getCity(),
            entity.getState(),
            entity.getSectors().stream()
                .map(this::mapToSector)
                .toList(),
            new UserResponseDTO(
                entity.getOrganizer().getId(),
                entity.getOrganizer().getName(),
                entity.getOrganizer().getEmail(),
                entity.getOrganizer().getCpf(),
                entity.getOrganizer().getPhoneNumber(),
                entity.getOrganizer().getGender()
            ),
            entity.getEventReviews().stream()
                .map(review -> EventReviewResponseDTO.builder()
                    .reviewer(review.getReviewer())
                    .statusReview(review.getStatusReview())
                    .description(review.getDescription())
                    .dateReview(review.getDateReview())
                    .build())
                .toList(),
            entity.getCreatedAt()
        );
    }

    private SectorResponseDTO mapToSector(SectorEntity sector) {
        return SectorResponseDTO.builder()
            .name(sector.getName())
            .lotSectorTickets(sector.getLotSectorTickets().stream()
                .map(this::mapToTicketPrice)
                .toList())
            .build();
    }

    private TicketPriceResponseDTO mapToTicketPrice(LotSectorTicketEntity lotSectorTicket) {
        return TicketPriceResponseDTO.builder()
            .lotName(lotSectorTicket.getLot().getName())
            .priceForMen(lotSectorTicket.getTicketForMen())
            .priceForWomen(lotSectorTicket.getTicketForWomen())
            .build();
    }
}
