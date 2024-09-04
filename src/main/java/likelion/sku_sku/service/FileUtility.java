package likelion.sku_sku.service;

import java.util.Base64;
public class FileUtility {

    // 파일 인코딩
    public static String encodeFile(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }
}