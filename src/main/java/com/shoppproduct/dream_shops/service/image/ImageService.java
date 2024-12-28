package com.shoppproduct.dream_shops.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoppproduct.dream_shops.dto.ImageDTO;
import com.shoppproduct.dream_shops.exception.ImageNotFoundException;
import com.shoppproduct.dream_shops.model.Image;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.repostitory.ImageRepository;
import com.shoppproduct.dream_shops.service.product.IProductService;

@Service
public class ImageService implements IImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private IProductService iProductService;

    @Override
    public Image getImageById(int imageId) {
        return imageRepository.findById(imageId)
            .orElseThrow(() -> new ImageNotFoundException("Not found image with id: " + imageId));        
    }

    @Override
    public void deleteImageById(int imageId) {
        imageRepository.findById(imageId)
            .ifPresentOrElse(
                imageRepository :: delete, () -> {
                    throw new ImageNotFoundException("Not found image with id: " + imageId);
                });
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, int productId) {
        Product product = iProductService.getProductById(productId);
        
        List<ImageDTO> savedImageDTO = new ArrayList<>();
        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "api/v1/images/image/download";
                String downloadUrl = buildDownloadUrl + image.getImageId();
                image.setDownloadUrl(downloadUrl);
                
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getImageId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getImageId());
                imageDTO.setFileName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDTO.add(imageDTO);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDTO;
    }

    @Override
    public void updateImage(MultipartFile file, int imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
