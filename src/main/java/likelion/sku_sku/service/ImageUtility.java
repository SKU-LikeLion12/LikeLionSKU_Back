package likelion.sku_sku.service;

import java.util.Base64;

public class ImageUtility {

    // 이미지 인코딩
    public static String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
