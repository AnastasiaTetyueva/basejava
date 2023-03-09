package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.Resume;

public class DeadlockTest {

    public static void main(String[] args) {
        Resume r1 = ResumeTestData.createResume("uuid1", "Петров Петр");
        Resume r2 = ResumeTestData.createResume("uuid2", "Иванов Иван");

        Thread thread1 = doThread(r1, r2, "Сергей");
        Thread thread2 = doThread(r2, r1, "Георгий");

        thread1.start();
        thread2.start();
    }

    private static Thread doThread(Resume x, Resume y, String name) {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (x) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                x.setFullName(name);
                System.out.println(x.getFullName());
                synchronized (y) {
                    x.equals(y);
                }
            }
            System.out.println(Thread.currentThread().getName() + " end");
        });
    }

}
