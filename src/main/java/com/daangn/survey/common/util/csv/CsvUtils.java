package com.daangn.survey.common.util.csv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    private static Log logger = LogFactory.getLog(CsvUtils.class);

    public static List<List<String>> readToList(String path) {
        List<List<String>> list = new ArrayList<>();
        File csv = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            Charset.forName("UTF-8");
            String line = "";

            while((line=br.readLine()) != null) {
                String[] token = line.split(",");
                List<String> tempList = new ArrayList<>(Arrays.asList(token));
                list.add(tempList);
            }

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return list;
    }
}
