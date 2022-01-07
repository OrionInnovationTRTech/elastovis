package elastovis.loganalysis.loader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import elastovis.loganalysis.document.FileOperation;
import elastovis.loganalysis.document.Pm;
import elastovis.loganalysis.repository.PmRepository;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private static int pmIndex = 0;
    private int uniqueVal = 0;
    private final PmRepository pmRepository;
    private HashMap<String, Integer> history = new HashMap<>();

    @Value("${DIR_PATH_FOLDER}")
    private String DIR_PATH_FOLDER;

    public DataLoader(PmRepository pmRepository) {
        this.pmRepository = pmRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            FileOperation fileOperation = new FileOperation();
            HashMap<Integer, String> listFile = fileOperation.getAllFileList(DIR_PATH_FOLDER);
            ArrayList<String> reverseListFile = fileOperation.sortHashMap(listFile);
            for (String file : reverseListFile) {

                try {
                    System.out.println("[DEBUG] File Read: " + file);
                    String xmlFileContent = readFileContent(file);
                    JSONArray metricSample = jsonParse(xmlFileContent);
                    pmRepository.saveAll(savePm(metricSample, file.replaceAll(".*\\\\", "")));
                    uniqueVal = 0;
                    logger.debug("[DEBUG] " + file + " File Read...");
                } catch (Exception ex) {
                    logger.error("[ERROR] " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * İlk okunan Pm dosyasına ait olan Metric Attribute değerlerini Hashmap içerisine ekleyerek bir sonraki okunacak
     * olan Pm dosyasındaki değerlerle karşılaştırma yapılmaktadır. Pm log dosyalarındaki artış metricleri sıfırlanmadığı
     * için metricDiff verisi oluşturulmuş ve bu sayede okunacak olan log dosyalarındaki value artış miktarının doğru
     * şekilde gözlemlenmesi amaçlanmıştır.
     * TODO: Add Method Description
     *
     * @param pmAttribute: Pm log file Metric Attribute
     * @param pmValue:     Pm log file Metric Value
     * @return
     */
    private int calculateDiff(String pmAttribute, int pmValue) {
        int result = history.containsKey(pmAttribute) ? pmValue - history.get(pmAttribute) : 0;
        history.put(pmAttribute, pmValue);

        return result;
    }

    private Long docTimeOperation(String dates, String times) throws ParseException {
        String mergeDate = dates + " " + times;
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss aaa", Locale.ENGLISH);
        Date date = format.parse(mergeDate);

        return date.getTime();
    }

    private String readFileContent(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();

        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }

    private JSONArray jsonParse(String xml) {
        JSONObject json = XML.toJSONObject(xml);
        JSONArray metricSample = json.getJSONObject("performance-history")
                .getJSONObject("metrics")
                .getJSONArray("metric-sample");

        return metricSample;
    }

    private ArrayList<Pm> savePm(JSONArray jsonArray, String pmName) throws ParseException {

        HashMap<String, Pm> pmFile = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            Pm pm = new Pm();
            JSONObject rec = jsonArray.getJSONObject(i);
            pm.setMetricAttribute(rec.getString("id"));
            pm.setPmFileName(pmName);
            pm.setSystem(rec.getJSONObject("distinguishedName").getJSONObject("ns3:systemID").getJSONArray("ns3:element").getJSONObject(1).get("ns2:value").toString());
            pm.setEntity(rec.getJSONObject("distinguishedName").getJSONObject("ns3:subentityId").getJSONArray("ns3:element").getJSONObject(1).get("ns2:name").toString());
            pm.setName(rec.getJSONObject("distinguishedName").getJSONObject("ns3:subentityId").getJSONArray("ns3:element").getJSONObject(1).get("ns2:value").toString());
            pm.setMetricValue((Integer) rec.get("value"));
            pm.setPmFileTimestamp(docTimeOperation(rec.getString("date"), rec.getString("time")));
            String uniqueID = pm.getMetricAttribute() + pm.getName() + pm.getSystem();
            //String uniqueID = pm.getMetricAttribute() + (++uniqueVal);

            if (pmFile.containsKey(uniqueID)) {
                logger.error("Duplicate Entry Ingored: " + pm);
                System.out.println("Duplicate Entry Ingored: " + pm);
                continue;
            }
            pm.setMetricDiff(calculateDiff(uniqueID, pm.getMetricValue()));
            pm.setId((long) pmIndex++);
            pmFile.put(uniqueID, pm);

        }
        return new ArrayList<Pm>(pmFile.values());
    }
}
