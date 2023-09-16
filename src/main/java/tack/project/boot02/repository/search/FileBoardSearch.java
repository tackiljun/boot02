package tack.project.boot02.repository.search;

import tack.project.boot02.dto.FileBoardListDTO;
import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;


public interface FileBoardSearch {

    ///////////////////////////////////////////////////////////////////////
    // 리스트 검색조건.
    PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO);
    // limit 한번 확인해주기.
    
}
