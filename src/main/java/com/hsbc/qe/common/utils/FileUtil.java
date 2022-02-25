package com.hsbc.qe.common.utils;


import com.hsbc.qe.api.customexceptions.ContentNotFoundException;
import com.hsbc.qe.api.customexceptions.InvalidFileFormatException;
import io.restassured.internal.util.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static final String NL = System.getProperty("line.separator");
    private static final String FS = File.separator;
    public static String readContentFromFile(String filePath) throws IOException, InvalidFileFormatException,
            ContentNotFoundException {
        checkFileExtension(filePath);
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
        if(fileContent.isEmpty()){
            throw new ContentNotFoundException("No content found in the expected file " + FilenameUtils.getName(filePath));
        }
        return fileContent;
    }

    private static void checkFileExtension(String filePath) throws InvalidFileFormatException, FileNotFoundException {

        checkFileExists(filePath);
        String fileExtensionName = FilenameUtils.getExtension(filePath);
        switch (fileExtensionName) {
            case "json":
                logger.info("file extension is json");
                break;
            case "txt":
                logger.info("file extension is txt");
                break;
            case "xml":
                logger.info("file extension is xml");
                break;
            default:
                logger.error("File extension should be either .txt or .xml but the actual format is {}", fileExtensionName);
                throw new InvalidFileFormatException("Invalid file format.");
        }
    }

    private static void checkFileExists(String filePath) throws FileNotFoundException {
        if(!Files.exists(Paths.get(filePath))) {
            throw new FileNotFoundException("File " + FilenameUtils.getName(filePath) + " not found.");
        }
    }

    public static List<String> readAllLinesFromFile(String filePath) throws IOException {
        checkFileExists(filePath);
        return Files.readAllLines(Paths.get(filePath));
    }

    public static void writeToFile(String directoryName, String inputFileFileName,
                                   String content) throws IOException {
        createDirectoryIfNotExists(directoryName);
        File actualFile = createFileIfNotExists(directoryName + FS, inputFileFileName);
        if (actualFile.exists()) {
            FileWriter fileWriter = new FileWriter(actualFile.getAbsoluteFile(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();
        }
    }


    private static void createDirectoryIfNotExists(String directoryName) throws IOException {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                throw new IOException("Directory: " + directoryName + " not created");
            }
        }
    }

    private static File createFileIfNotExists(String directoryName, String fileName) throws IOException {

        if(directoryName == null) {
            throw new IOException("Directory name should not be null.");
        }

        if(fileName == null) {
            throw new IOException("File name should not be null.");
        }

        File tempFile = new File(directoryName + FS + fileName);
        if (!tempFile.exists()) {
            try {

                tempFile.createNewFile();

            } catch (IOException e) {
                logger.error("Unable to create the file: {} in the directory: {}" + NL + e, fileName, directoryName);
                throw e;
            }
        } else if (tempFile.exists()) {
            PrintWriter writer = new PrintWriter(tempFile);
            writer.print("");
            writer.close();
        }

        return tempFile;
    }

    /**
     * Method to delete the file.
     *
     * @param fileName
     *            name of the file to delete
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("Unable to delete the file: {}", fileName);
            throw e;
        }
    }

    /**
     * Method to read the content from the file.
     *
     * @param fileName
     *            name of the file.
     * @return the content in String.
     * @throws IOException
     *             if unable to read the file.
     */
    public static String getContentFromTextFile(String fileName) throws IOException {
        Class clazz = FileUtil.class;
        InputStream inputStream = clazz.getResourceAsStream(fileName);
        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    static List<String> readLines(File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }

    private static List<String> readLines(File file, Charset encoding) throws IOException {
        FileInputStream in = null;

        List<String> var3;
        try {
            in = openInputStream(file);
            var3 = readLines(in, encoding == null ? Charset.defaultCharset() : encoding);
        } finally {
            closeQuietly(in);
        }

        return var3;
    }

    static byte[] readBytes(File file) throws IOException {
        return IOUtils.toByteArray(new FileInputStream(file));
    }

    private static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File \'" + file + "\' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File \'" + file + "\' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File \'" + file + "\' does not exist");
        }
    }

    private static boolean isSeparator(char ch) {
        return ch == 47 || ch == 92;
    }


    public static URL createCukesPropertyFileUrl(final ClassLoader classLoader) {
        String cukesProfile = System.getProperty("cukes.profile");

        String propertiesFileName = cukesProfile == null || cukesProfile.isEmpty()
                ? "cukes.properties"
                : "cukes-" + cukesProfile + ".properties";

        return classLoader.getResource(propertiesFileName);
    }

    private static void closeQuietly(InputStream closeable) {
        try {
            if(closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {}
    }

    private static List<String> readLines(InputStream inputStream, Charset encoding) throws IOException {
        InputStreamReader input = new InputStreamReader(inputStream, encoding == null?Charset.defaultCharset():encoding);
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<String>();

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }

        return list;
    }

    private static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader?(BufferedReader)reader:new BufferedReader(reader);
    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
