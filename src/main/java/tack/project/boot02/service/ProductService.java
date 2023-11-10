package tack.project.boot02.service;

import jakarta.transaction.Transactional;

import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.dto.ProductDTO;
import tack.project.boot02.dto.ProductListDTO;


@Transactional
public interface ProductService {

    /////////////////////////////////////////////////////////////////
    PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);

    /////////////////////////////////////////////////////////////////
    Long register(ProductDTO productDTO);
    ProductDTO readOne(Long pno);
    void remove(Long pno);
    void modify(ProductDTO productDTO);
    
}
