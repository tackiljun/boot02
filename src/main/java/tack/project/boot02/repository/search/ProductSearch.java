package tack.project.boot02.repository.search;

import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.dto.ProductListDTO;


public interface ProductSearch {

    ///////////////////////////////////////////////////////////////////////////////
    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    ///////////////////////////////////////////////////////////////////////////////
    PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO);
    
}
