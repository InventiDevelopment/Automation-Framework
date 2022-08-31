package cz.inventi.qa.framework.core.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    /**
     * Reads file as text.
     * @param file given file
     * @return String with file content
     */
    public static String readFileAsText(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new FrameworkException("Could not read given file (" + file.toPath() + ") as text.");
        }
    }

    /**
     * Extract a ZIP archive to given destination.
     * @param zipArchive ZIP archive file
     * @param outputDir where the archive should be extracted
     * @return folder with extracted files as a File
     */
    public static File extractZipArchive(File zipArchive, Path outputDir) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipArchive))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                Path unzipPath = outputDir.resolve(zipEntry.getName()).normalize();
                if (!unzipPath.startsWith(outputDir.toFile().getCanonicalPath())) {
                    throw new FrameworkException("Invalid ZIP file detected (" + zipArchive.getName() + ")");
                }
                if (zipEntry.getName().endsWith(File.separator)) {
                    Files.createDirectories(unzipPath);
                } else {
                    if (unzipPath.getParent() != null && Files.notExists(unzipPath.getParent())) {
                        Files.createDirectories(unzipPath.getParent());
                    }
                    Files.copy(zipInputStream, unzipPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
            return outputDir.toFile();
        } catch (IOException e) {
            throw new FrameworkException("Could not extract zip archive (" + zipArchive.getName() + ")", e);
        }
    }

    /**
     * Finds file by name in a given destination by path.
     * @param path where the file should be searched for
     * @param fileName full name of the file
     * @return found path to relevant file or null
     */
    public static Path findFileByName(Path path, String fileName) {
        try {
            return Files.find(
                    path,
                    Integer.MAX_VALUE,
                    (filePath, attr) -> filePath.toFile().getName().equals(fileName)
            ).findFirst().orElse(null);
        } catch (IOException e) {
            throw new FrameworkException(
                    "Error occurred while trying to find file (" + fileName + ") in path (" + path + ")",
                    e
            );
        }
    }

    /**
     * Creates a ZIP archive with specified files inside.
     * @param filesToZip list of files-paths to be zipped
     * @param outputArchivePath output path with filename for the zip file
     * @return Path to created ZIP archive
     */
    public static Path zipFiles(
            List<Path> filesToZip,
            Path outputArchivePath
    ) {
        try {
            Files.deleteIfExists(outputArchivePath);
            ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(outputArchivePath));
            for (Path path : filesToZip) {
                    ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                    try {
                        zs.putNextEntry(zipEntry);
                        Files.copy(path, zs);
                        zs.closeEntry();
                    } catch (IOException e) {
                        throw new FrameworkException(e);
                    }
            }
            zs.finish();
            return outputArchivePath;
        } catch (IOException e) {
            throw new FrameworkException("Could not ZIP result files", e);
        }
    }

    /**
     * Creates a TXT file in given location with given content.
     * @param fileName Filename
     * @param content Content
     */
    public static File createTxtFile(Path path, String fileName, String content, boolean noExtension) {
        try {
            String fileExtension = ".txt";
            if (noExtension) fileExtension = "";
            Path fullPath = Path
                    .of(path.toString(), File.separator, fileName + fileExtension)
                    .toAbsolutePath();
            PrintWriter writer = new PrintWriter(fullPath.toString(), StandardCharsets.UTF_8);
            writer.println(content);
            writer.close();
            return fullPath.toFile();
        } catch (IOException e) {
            throw new FrameworkException("Cannot create TXT file", e);
        }
    }

    public static File createTxtFile(String fileName, String content) {
        return createTxtFile(Utils.getTestResourcesFolder(), fileName, content);
    }

    public static File createTxtFile(Path path, String fileName, String content) {
        return createTxtFile(path, fileName, content, false);
    }

    /**
     * Returns MIME type of the file.
     * @param filePath Path to the file
     * @return File MIME type in String
     */
    public static String retrieveFileMimeType(Path filePath) {
        try {
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new FrameworkException("Could not get file MIME type", e);
        }
    }

    /**
     * Maps a CSV file content to a List of
     * defined Java DTOs.
     * @param csvFile CSV file
     * @param csvDtoClass class to map to
     * @param withHeader if the CSV file has header
     * @return Returns List of mapped objects.
     * @param <T> class to map to
     */
    public static <T> List<T> readCsvAsList(
            File csvFile,
            Class<T> csvDtoClass,
            boolean withHeader
    ) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(csvDtoClass);
        if (withHeader) schema = schema.withHeader().withColumnReordering(true);
        try {
            MappingIterator<T> it = csvMapper.readerFor(csvDtoClass).with(schema).readValues(csvFile);
            List<T> resultsList = it.readAll();
            it.close();
            return resultsList;
        } catch (IOException e) {
            throw new FrameworkException("Could not map CSV file values", e);
        }
    }
}
