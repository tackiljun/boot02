package tack.project.boot02.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import tack.project.boot02.dto.MemberCartDTO;
import tack.project.boot02.entity.MemberCart;
import tack.project.boot02.repository.MemberCartRepository;


@Service
@Log4j2
@RequiredArgsConstructor
public class MemberCartServiceImpl implements MemberCartService {

    //////////////////////////////////////////////////////////////////////////////////////////////
    private final MemberCartRepository cartRepository;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO) {

        MemberCart cart = dtoToEntity(memberCartDTO);

        cartRepository.save(cart);

        List<MemberCart> cartList = cartRepository.selectCart(memberCartDTO.getEmail());

        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<MemberCartDTO> getCart(String email) {

        List<MemberCart> cartList = cartRepository.selectCart(email);

        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());

    }
    
}
