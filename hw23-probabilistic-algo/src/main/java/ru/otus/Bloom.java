package ru.otus;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Set;

/**
 * Определение того, принадлежит ли элемент множеству
 */
public class Bloom {

    /**
     * Слова в словаре
     */
    private static final Set<String> IN_DICTIONARY = Set.of(
        "scalpellum","undermining","collocate","breadth","tricuspidate","mesonephridium","boardable","irride",
        "aegirinolite","trachelocyllosis","effectuality","duplicia","cissies","sharkskins","chiefage","cubically",
        "entfaoilff","discomforter","overelaborate","quartets","ovipositional","equoid","fosslify","lengthsmen",
        "becard","discomforted","accorporation","hyperlustrously","psorous","circumambulated","fluorotic","reannexing",
        "uninferably","somberness","granularity","quatrocentist","restacks","artiest","nonacquisitive","aphrasia",
        "hollowware","conflagrate","anomiacea","parhomology","lebens","trination","cholelithotripsy","autodidactically",
        "copyhold","expostulator","stiffening","abacay","micropyle","griminess","retinula","abacas","rehung","unshipped",
        "bettas","deciares","contrabasso","aedeagal","overfluency","covalently","protarsal","athetizing","pleatless","sloppage",
        "literately","irrelevancy","misleadingly","congoleum","irrelevance","spasmodism","endearedly","spasmodist","milkwagon",
        "octomerous","thoracostracan","superfortunately","ancoral","unfallibleness","unappliable","betainogen","wiliness",
        "agistment","zygoid","forrader","conflagrant","unappliably","effectualize","knolly","dradge","hesperitin",
        "micrencephalus","quarters","chicalote","explanatorily","scorifying","microcos"
    );

    /**
     * Слова, которых нет в словаре
     */
    private static final Set<String> NOT_IN_DICTIONARY = Set.of(
        "cubicallyty","effectualityjd","lengthsmenil","misleadinglynm","chicalotemk","uninferablynl","anomiaceayj",
        "zygoidgr","griminesslh","unappliableoi","forraderfb","discomfortertq","thoracostracanld","autodidacticallytb",
        "quatrocentistye","protarsalyb","congoleumab","conflagratefo","aphrasiawy","scalpellumvr","tricuspidatelq",
        "micropyleva","aedeagalbm","effectualizeai","irridevx","octomerousxk","micrencephalusou","superfortunatelyfu",
        "breadthkf","wilinessal","explanatorilyrp","trinationxc","rehungkx","overelaboratelq","artiestwd","scorifyingas",
        "fluoroticwn","ancoralwj","agistmentkn","sloppagetb","deciaresxy","pleatlessut","contrabassowy","quarterseq",
        "collocatees","nonacquisitiveeh","circumambulatedpe","restackslj","discomfortedcn","antiprimersx","unappliablycf",
        "covalentlykw","psorousuy","unfalliblenessmc","cholelithotripsyov","underminingzv","entfaoilffga","hesperitinzx",
        "mesonephridiumfr","aegirinoliteqy","accorporationwu","fosslifyzt","sharkskinsyp","parhomologyxj","equoidrc",
        "abacaspb","trachelocyllosisct","hyperlustrouslyfq","ovipositionaltj","abacaymb","boardableao","overfluencyci",
        "conflagrantkt","bettaszd","dupliciapk","microcossj","copyholdzw","literatelyho","becardbt","lebenszo","dradgebj",
        "unshippedvp","spasmodistly","chiefageph","stiffeningti","athetizingrp","reannexingia","endearedlyzt","betainogenlb",
        "spasmodismoo","knollymj","expostulatorxx","sombernessjr","hollowwareuu","milkwagonyu","quartetsti","irrelevancyke",
        "granularityqq","irrelevancecr","cissieshe"
    );

    /**
     * Содержится ли слово в словаре английских слов https://github.com/dwyl/english-words
     */
    public static void main(String[] args) throws Exception {
        // добавляем слова в фильтр Блума
        BloomFilter<String> bloomFilter = BloomFilter.create(
            Funnels.stringFunnel(Charset.defaultCharset()),
            370_110,
            0.01
        );
        Path filePath = Path.of(Bloom.class.getClassLoader().getResource("words_alpha.txt").toURI());
        int all = 0;
        try (CsvReader csvReader = CsvReader.builder().build(filePath)) {
            for (CsvRow csvRow : csvReader) {
                bloomFilter.put(csvRow.getField(0));
                all++;
            }
        }
        System.out.println("Full size=" + all); // 370_105

        // проверяем слова, которые содержатся в словаре, по фильтру Блума
        int ok = 0;
        int fail = 0;
        for (String s : IN_DICTIONARY) {
            if (bloomFilter.mightContain(s)) {
                ok++;
            } else {
                fail++;
            }
        }
        System.out.printf("Words in dictionary: ok=%d fail=%d\n", ok, fail); // ok=100, fail=0, т.е. все существующие слова найдены корректны


        ok = 0;
        fail = 0;
        for (String s : NOT_IN_DICTIONARY) {
            if (bloomFilter.mightContain(s)) {
                fail++;
            } else {
                ok++;
            }
        }
        System.out.printf("Words not in dictionary: ok=%d fail=%d pctFailed=%f\n",
            ok, fail, fail * 100D / (ok + fail)); // ok=97, fail=3, pctFailed=3%
    }
}
