package tack.project.boot02.service;

import java.util.List;

import jakarta.transaction.Transactional;

import tack.project.boot02.dto.MemberCartDTO;
import tack.project.boot02.entity.MemberCart;


@Transactional
public interface MemberCartService {

    /////////////////////////////////////////////////////////////////////////////////////////////
    List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO);

    /////////////////////////////////////////////////////////////////////////////////////////////
    List<MemberCartDTO> getCart(String email);

    /////////////////////////////////////////////////////////////////////////////////////////////
    default MemberCart dtoToEntity(MemberCartDTO dto) {

        MemberCart entity = 
        MemberCart.builder().cno(dto.getCno()).email(dto.getEmail()).pno(dto.getPno()).build();

        return entity;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    default  MemberCartDTO entityToDTO(MemberCart entity) {

        MemberCartDTO dto = MemberCartDTO.builder()
            .cno(entity.getCno())
            .email(entity.getEmail())
            .pno(entity.getPno())
            .build();

        return dto;

    }
    
}
