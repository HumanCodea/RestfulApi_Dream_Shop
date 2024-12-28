package com.shoppproduct.dream_shops.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppproduct.dream_shops.dto.ImageDTO;
import com.shoppproduct.dream_shops.model.Image;

public interface IImageService {
    
    Image getImageById(int imageId);
    void deleteImageById(int imageId);
    List<ImageDTO> saveImages(List<MultipartFile> file, int productId);
    void updateImage(MultipartFile file, int imageId);

}
