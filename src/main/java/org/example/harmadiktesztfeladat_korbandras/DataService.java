package org.example.harmadiktesztfeladat_korbandras;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    public List<NameFrequency> dataFromXML(String startsWith, String sort){
        List<NameFrequency> frequencies = new ArrayList<>();

        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("java/org/example/harmadiktesztfeladat_korbandras/test-data.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            NodeList nodeList = document.getElementsByTagName("datafield");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    if ("100".equals(elem.getAttribute("tag"))) {
                        NodeList subfields = elem.getElementsByTagName("subfield");
                        for (int j = 0; j < subfields.getLength(); j++) {
                            Node subNode = subfields.item(j);
                            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element subElem = (Element) subNode;
                                if ("a".equals(subElem.getAttribute("code"))) {
                                    String name = subElem.getTextContent();
                                    updateFrequency(frequencies, name);
                                }
                            }
                        }
                    }
                }
            }

            if(startsWith != null && !startsWith.isEmpty()){
                frequencies.removeIf(nf -> !nf.getName().startsWith(startsWith));
            }

            sortFrequencies(frequencies, sort);

        }catch (Exception e){e.printStackTrace();}

        return frequencies;
    }

    private void updateFrequency(List<NameFrequency> frequencies, String name) {
        for (NameFrequency nf : frequencies) {
            if (nf.getName().equals(name)) {
                nf.incrementFrequency();
                return;
            }
        }
        frequencies.add(new NameFrequency(name, 1));
    }

    private void sortFrequencies(List<NameFrequency> frequencies, String sortOrder) {
        switch (sortOrder) {
            case "frequencyAsc": frequenciesAsc(frequencies); break;
            case "frequencyDesc": frequenciesDesc(frequencies); break;
            case "nameDesc": namesDesc(frequencies); break;
            default: namesAsc(frequencies); break;
        }
    }

    private static void namesDesc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getName().compareTo(frequencies.get(maxIndex).getName()) > 0) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(maxIndex));
                frequencies.set(maxIndex, temp);
            }
        }
    }

    private static void namesAsc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getName().compareTo(frequencies.get(minIndex).getName()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(minIndex));
                frequencies.set(minIndex, temp);
            }
        }
    }

    private static void frequenciesDesc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getFrequency() > frequencies.get(maxIndex).getFrequency()) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(maxIndex));
                frequencies.set(maxIndex, temp);
            }
        }
    }

    private static void frequenciesAsc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getFrequency() < frequencies.get(minIndex).getFrequency()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(minIndex));
                frequencies.set(minIndex, temp);
            }
        }
    }
}
