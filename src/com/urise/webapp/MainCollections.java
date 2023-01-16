package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID1);

    private static final String UUID2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID2);

    private static final String UUID3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID3);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        for (Resume r : collection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID1)) {
//                collection.remove(r);
            }
        }

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID1)) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());


        Map<String, Resume> map = new HashMap<>();
        map.put(UUID1, RESUME_1);
        map.put(UUID2, RESUME_2);
        map.put(UUID3, RESUME_3);

        // Bad!
        /*
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        } */

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
