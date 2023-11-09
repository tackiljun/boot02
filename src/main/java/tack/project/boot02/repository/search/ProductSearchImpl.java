package tack.project.boot02.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.dto.ProductListDTO;
import tack.project.boot02.entity.Product;
import tack.project.boot02.entity.QProduct;
import tack.project.boot02.entity.QProductImage;
import tack.project.boot02.entity.QProductReview;


public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    ///////////////////////////////////////////////////////////////////////////////////////
    public ProductSearchImpl() {
        super(Product.class);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        JPQLQuery<Product> query = from(product);

        query.leftJoin(product.images, productImage);
        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1;

        Pageable pageable = PageRequest.of(
            pageNum,
            pageRequestDTO.getSize(),
            Sort.by("pno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtoQuery = query.select(
            Projections.bean(ProductListDTO.class,
            product.pno,
            product.pname,
            product.price,
            productImage.fname)
        );

        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Override
    public PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QProductReview review = QProductReview.productReview;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);
        query.leftJoin(review).on(review.product.eq(product));

        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1;

        Pageable pageable = PageRequest.of(
            pageNum,
            pageRequestDTO.getSize(),
            Sort.by("pno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(product);

        JPQLQuery<ProductListDTO> dtoQuery = 
        query.select(Projections.bean(
            ProductListDTO.class,
            product.pno,
            product.pname,
            product.price,
            productImage.fname.min().as("fname"),
            review.score.avg().as("reviewAvg"),
            review.count().as("reviewCnt"))
        );

        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);

    }
    
}
