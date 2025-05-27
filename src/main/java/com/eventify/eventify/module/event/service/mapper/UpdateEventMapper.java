package com.eventify.eventify.module.event.service.mapper;

import com.eventify.eventify.module.event.model.dto.UpdateEventRequestDTO;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateEventMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(UpdateEventRequestDTO dto, @MappingTarget EventEntity entity);
}
