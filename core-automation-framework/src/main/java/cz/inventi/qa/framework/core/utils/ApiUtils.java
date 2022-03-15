package cz.inventi.qa.framework.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.inventi.qa.framework.core.objects.api.filters.ProxyFilter;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class ApiUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Converts given object to json String.
     *
     * @param object object to be converted
     * @return String with json
     */
    public static String convertToJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * Downloads file using given URL.
     * @param url URL to the file
     * @return RestAssured Response
     */
    public static Response downloadFile(String url) {
        if (url == null) throw new FrameworkException("URL must not be null");
        return RestAssured.given()
                .filter(new ProxyFilter())
                .urlEncodingEnabled(false)
                .get(url);
    }

    public static File downloadAndRetrieveFile(String url, String downloadFileName) {
        try {
            File outputFile = new File(Utils.getTestResourcesFolder() + File.separator + downloadFileName);
            OutputStream outStream = new FileOutputStream(outputFile);
            outStream.write(downloadFile(url).asByteArray());
            outStream.close();
            return outputFile;
        } catch (IOException e) {
            throw new FrameworkException("Failed to save downloaded file from URL (" + url + ")", e);
        }
    }

    public static File downloadAndRetrieveFileExtracted(
            String url,
            String downloadFileName,
            String extractFolderName
    ) {
        return FileUtils.extractZipArchive(
                downloadAndRetrieveFile(url, downloadFileName),
                Path.of(Utils.getTestResourcesFolder().toString(),extractFolderName)
        );
    }
}