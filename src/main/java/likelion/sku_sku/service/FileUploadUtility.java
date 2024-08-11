package likelion.sku_sku.service;

import java.util.Base64;
public class FileUploadUtility {
    public static String encodeFile(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

    public static byte[] decodeFile(String encodedFileData) {
        return Base64.getDecoder().decode(encodedFileData);
    }
}
