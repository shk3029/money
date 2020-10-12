package com.kakaopay.money.share.mapper;

import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Share;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShareMapper {
    ShareMapper INSTANCE = Mappers.getMapper(ShareMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Share shareDtoToEntity(ShareDto shareDto);
}
