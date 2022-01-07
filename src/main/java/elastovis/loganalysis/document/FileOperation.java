package elastovis.loganalysis.document;

import elastovis.loganalysis.service.FileOperationService;

import java.io.*;
import java.util.*;

public class FileOperation implements FileOperationService {

    public String getFilePath(String path) {
        return path;
    }

    public HashMap<Integer, String> getAllFileList(String dir) {
        List<String> fileList = new ArrayList();
        HashMap<Integer,String> newFilePathList = new HashMap<>();

        File file = new File(dir);
        FilenameFilter textFilefilter = (dir1, name) -> {
            String lowercaseName = name.toLowerCase();
            if (lowercaseName.contains("-pm.log.")) {
                return true;
            } else {
                return false;
            }
        };

        File filepathlist[] = file.listFiles(textFilefilter);

        for (File f : filepathlist) {
            fileList.add(f.getAbsolutePath().toString());
        }
        Collections.sort(fileList);
        newFilePathList.putAll(hashSortFileList(fileList));

        return newFilePathList;
    }

    private  HashMap<Integer,String>  hashSortFileList(List<String> fileList){

        HashMap<Integer,String> mapFileList=new HashMap<Integer,String>();
        for (String i: fileList) {
            mapFileList.put(Integer.valueOf(i.split("\\.")[2]), i);
        }

        return mapFileList;
    }

    public ArrayList<String> sortHashMap(HashMap<Integer, String> maps) {

        ArrayList<String> fileList = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>();

        keys.addAll(maps.keySet());
        Collections.sort(keys);

        for (short i = (short) (keys.size()-1); i>=0; i-- ) {
            fileList.add(maps.get(keys.get(i)));
        }
        return fileList;
    }

    public String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }
}
