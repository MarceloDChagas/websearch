import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class responsible for reading queries from files.
 * Follows Single Responsibility Principle - only handles file I/O operations.
 */
public class FileQueryReader {
    
    /**
     * Interface for processing queries as they are read from file.
     */
    public interface QueryProcessor {
        void processQuery(String query);
    }
    
    /**
     * Reads all queries from a file and returns them as a list.
     * @param sourceFile The file to read queries from
     * @return List of queries (lines from the file)
     * @throws IOException if file reading fails
     */
    public static List<String> readAllQueries(File sourceFile) throws IOException {
        List<String> queries = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                queries.add(line);
            }
        }
        
        return queries;
    }
    
    /**
     * Reads queries from file and processes them one by one using the provided processor.
     * This approach is memory-efficient for large files as it doesn't load all content into memory.
     * @param sourceFile The file to read queries from
     * @param processor The processor that will handle each query
     * @throws IOException if file reading fails
     */
    public static void readAndProcessQueries(File sourceFile, QueryProcessor processor) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                processor.processQuery(line);
            }
        }
    }
}