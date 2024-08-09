package likelion.sku_sku.service;

import java.util.Base64;

public class FileUploadUtility {
    // 파일을 Base64로 인코딩
    public static String encodeFile(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

    // Base64로 인코딩된 파일을 디코딩
    public static byte[] decodeFile(String encodedFileData) {
        return Base64.getDecoder().decode(encodedFileData);
    }
}
