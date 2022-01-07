package elastovis.loganalysis.service;

import java.util.HashMap;

public interface FileOperationService {

    public String getFilePath(String path);
    public HashMap<Integer, String> getAllFileList(String dir);
}
