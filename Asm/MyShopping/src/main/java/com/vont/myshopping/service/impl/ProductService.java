package com.vont.myshopping.service.impl;

import com.vont.myshopping.models.dto.ProductDto;
import com.vont.myshopping.models.entity.Category;
import com.vont.myshopping.models.entity.Product;
import com.vont.myshopping.payload.request.ProductRequest;
import com.vont.myshopping.payload.response.ProductResponse;
import com.vont.myshopping.repository.CategoryRepository;
import com.vont.myshopping.repository.OrderDetailRepository;
import com.vont.myshopping.repository.ProductRepository;
import com.vont.myshopping.service.IProductService;
import com.vont.myshopping.utils.FileManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    private final FileManagerService fileManagerService;

    private final CategoryRepository categoryRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, FileManagerService fileManagerService, CategoryRepository categoryRepository, OrderDetailRepository orderDetailRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.fileManagerService = fileManagerService;
        this.categoryRepository = categoryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto save(ProductRequest productFormInput) {
        ProductDto productDto = modelMapper.map(productFormInput, ProductDto.class);
        Product product = convertToModel(productDto);
        String filename = fileManagerService.save("images", productFormInput.getImage());
        product.setImage(filename);
        product.setAvailable(true);
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    @Override
    public ProductDto update(ProductRequest productFormInput) {
        ProductDto productDto = modelMapper.map(productFormInput, ProductDto.class);
        Product product = convertToModel(productDto);
        if (!productFormInput.getImage().isEmpty()) {
            String filename = fileManagerService.save("images", productFormInput.getImage());
            product.setImage(filename);
        }
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    @Override
    public void deleteAll(long[] ids) {
        for (long id : ids) {
            Product product = productRepository.getReferenceById(id);
            fileManagerService.delete("images", product.getImage());
            orderDetailRepository.deleteOrderDetailsByProduct_Id(product.getId());
            productRepository.deleteById(id);
        }
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).get();
        try {
            fileManagerService.delete("images", product.getImage());
        } catch (Exception e) {
        }
        orderDetailRepository.deleteOrderDetailsByProduct_Id(product.getId());
        productRepository.delete(product);
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return this.convertToDTO(product.orElseGet(Product::new));
    }

    @Override
    public List<ProductDto> findByCategory(Long id) {
        return this.getProductDTOList(productRepository.findAllByCategory_IdAndAvailable(id, true));
    }

    @Override
    public ProductResponse findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        ProductResponse productFormOutput = new ProductResponse();
        productFormOutput.setTotalItems(productPage.getTotalElements());
        productFormOutput.setTotalPages(productPage.getTotalPages());
        productFormOutput.setCurrentPage(productPage.getNumber());
        productFormOutput.setProductDtoList(getProductDTOList(productPage.getContent()));
        return productFormOutput;
    }

    @Override
    public List<ProductDto> findAll() {
        return this.getProductDTOList(productRepository.findAll());
    }

    private List<ProductDto> getProductDTOList(List<Product> productList) {
        List<ProductDto> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDTO = convertToDTO(product);

            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    private Product convertToModel(ProductDto productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        if (productDTO.getId() != null) {
            Product oldProduct = productRepository.getReferenceById(productDTO.getId());
            product.setId(oldProduct.getId());
            product.setCreateDate(oldProduct.getCreateDate());
            product.setImage(oldProduct.getImage());
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
        product.setCategory(category);
        return product;
    }

    private ProductDto convertToDTO(Product product) {
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);

        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    @Override
    public List<ProductDto> findAllDto(Pageable pageable) {
        return getProductDTOList(productRepository.findAllByAvailable(true, pageable).getContent());
    }
}
