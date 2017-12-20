package Utils;

import Samples.TrainingSample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public List<TrainingSample> getSamplesFromFile(String path)
    {
        List<TrainingSample> samples = new ArrayList<>();

        File filePath = new File(path);
        BufferedReader bufferedReader = null;
        List<String> sampleStrings = new ArrayList<>();
        try
        {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String str = null;
            int line = 1;
            while ((str = bufferedReader.readLine()) != null)
            {
                line++;
                sampleStrings.add(str);
            }
            bufferedReader.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        String[] coordinates = new String[2];
        for (String str: sampleStrings) {
            str = str.replace("(", "");
            str = str.replace(")", "");
            coordinates = str.split(",");
            samples.add(new TrainingSample(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }

        return samples;
    }
}
