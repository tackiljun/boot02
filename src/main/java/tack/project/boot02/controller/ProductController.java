package tack.project.boot02.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.dto.ProductDTO;
import tack.project.boot02.dto.ProductListDTO;
import tack.project.boot02.service.ProductService;
import tack.project.boot02.util.FileUploader;


@CrossOrigin
@RestController
@RequestMapping("/api/products/")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private final ProductService service;
    private final FileUploader uploader;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("")
    public Map<String, Long> register(ProductDTO productDTO) {

        log.info(productDTO);

        List<String> fileNames = uploader.uploadFiles(productDTO.getFiles(), true);
                     // fileNames를 DTO에 줄꺼야.
        productDTO.setImages(fileNames);

        Long pno = service.register(productDTO);

        return Map.of("result", 123L);

    } 

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(value = "list")
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        log.info("------------------------------");
        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("{pno}")
    public ProductDTO getOne(@PathVariable("pno") Long pno) {

        log.info("----------PNO----------");

        log.info("PNO.........." + pno);
        log.info("PNO.........." + pno);

        return service.readOne(pno);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @DeleteMapping("{pno}")
    public Map<String, Long> delete(@PathVariable("pno") Long pno) {


        log.info("PNO.........." + pno);
        service.remove(pno);
        log.info("PNO.........." + pno);

        return Map.of("result", pno);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("modify")
    public Map<String, Long> modify(ProductDTO productDTO) {

        log.info("----------modify----------");
        log.info("----------modify----------");
        log.info("----------modify----------");
        log.info(productDTO);

        // 기존파일에 새파일까지 추가배열.
        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0) {

            List<String> uploadFileNames = uploader.uploadFiles(productDTO.getFiles(), true);

            List<String> oldFileNames = productDTO.getImages();

            uploadFileNames.forEach(fname -> oldFileNames.add(fname));

        }

        log.info("----------AFTER----------");
        log.info(productDTO);

        service.modify(productDTO);

        return Map.of("result", 111L);

    }
    
}
