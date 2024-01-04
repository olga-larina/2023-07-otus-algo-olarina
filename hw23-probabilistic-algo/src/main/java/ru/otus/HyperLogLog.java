package ru.otus;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import net.agkn.hll.HLL;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Определение количества уникальных элементов
 */
public class HyperLogLog {

    /**
     * Все зафиксированные нарушения ПДД в Нью-Йорке с июля 2018 по июнь 2019
     */
    private static final String DATA_URL = "https://data.cityofnewyork.us/api/views/faiq-9dfq/rows.csv?accessType=DOWNLOAD";

    /**
     * Количество уникальных номеров ТС среди всех зафиксированных нарушений ПДД
     */
    public static void main(String[] args) throws Exception {
        // HyperLogLog, 1 параметр - количество регистров, 2 параметр - число бит, используемых каждым регистром
        HLL hll = new HLL(14, 5);
        // хэш-функция
        HashFunction hashFunction = Hashing.murmur3_128();
        // реальные значения (для сравнения)
        Set<String> realSet = new HashSet<>();
        long all = 0L;

        URL url = new URL(DATA_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) {
            try (InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                 CsvReader csvReader = CsvReader.builder().build(streamReader)) {

                for (CsvRow csvRow : csvReader) {
                    if (csvRow.getOriginalLineNumber() == 1L || csvRow.getFieldCount() < 2) {
                        continue;
                    }
                    // номер ТС
                    String regNo = csvRow.getField(1);
                    // добавляем значение в HyperLogLog
                    long hashedValue = hashFunction.newHasher().putBytes(regNo.getBytes()).hash().asLong();
                    hll.addRaw(hashedValue);
                    // реальные значения
                    realSet.add(regNo);
                    all++;
                }
            }
        }
        System.out.println("All=" + all); // 11_467_506
        System.out.println("Expected=" + realSet.size()); // 3_052_679
        System.out.println("Unique=" + realSet.size() * 100D / all); // 26.62
        System.out.println("Actual (HLL)=" + hll.cardinality()); // 3_061_508
        System.out.println("Difference=" + Math.abs(realSet.size() - hll.cardinality()) * 100D / realSet.size()); // 0.289
    }
}
