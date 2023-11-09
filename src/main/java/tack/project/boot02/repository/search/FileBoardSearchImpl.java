package tack.project.boot02.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import tack.project.boot02.dto.FileBoardListDTO;
import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.entity.FileBoard;
import tack.project.boot02.entity.QFileBoard;
import tack.project.boot02.entity.QFileBoardImage;


public class FileBoardSearchImpl extends QuerydslRepositorySupport implements FileBoardSearch {

    ///////////////////////////////////////////////////////////////////////////////////////
    public FileBoardSearchImpl() {
        super(FileBoard.class);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Override
    public PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO) {

        // Q로 선언.
        QFileBoard board = QFileBoard.fileBoard;
        QFileBoardImage boardImage = QFileBoardImage.fileBoardImage;
        // query 선언 select.
        JPQLQuery<FileBoard> query = from(board);

        // left outer join.
        // 같은 조건이 없으므로 on조건을 사용할 수 없다.
        // 그래서 join을 할 수 없다.
        query.leftJoin(board.images, boardImage);
        query.where(boardImage.ord.eq(0));

        // boardImage의 ord가 0인걸로 where.
        //query.where(boardImage.ord.eq(0));

        // 페이지가 음수값이면 0으로 초기화.
        int pageNum = pageRequestDTO.getPage() - 1 < 0 ? 0 : pageRequestDTO.getPage() - 1;

        // 페이징처리.
        Pageable pageable = PageRequest.of(
        pageNum,
        pageRequestDTO.getSize(), 
        Sort.by("bno").descending());

        // 페이징.
        this.getQuerydsl().applyPagination(pageable, query);

        // 리스트 출력.
        // List<FileBoard> list = query.fetch();
        // list.forEach(fb -> {
        //     log.info(fb);
        //     log.info(fb.getImages());
        // });

        JPQLQuery<FileBoardListDTO> listQuery = query.select(
        Projections.bean(FileBoardListDTO.class,
        board.bno,
        board.title,
        boardImage.uuid,
        boardImage.fname)); // 목록

        List<FileBoardListDTO> list = listQuery.fetch();
        long totalCount = listQuery.fetchCount();

        return new PageResponseDTO(list, totalCount, pageRequestDTO);

    }
    
}
