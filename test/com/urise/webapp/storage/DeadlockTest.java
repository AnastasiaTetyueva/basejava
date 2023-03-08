package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.Resume;

public class DeadlockTest {

    public static void main(String[] args) {
        Resume r1 = ResumeTestData.createResume("uuid1", "Петров Петр");
        Resume r2 = ResumeTestData.createResume("uuid2", "Иванов Иван");

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (r1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r1.setFullName("Иванов Петр");
                System.out.println(r1.getFullName());

                synchronized (r2) {
                    System.out.println(r1.equals(r2));
                }
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "Thread1");

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (r2) {
                r2.setFullName("Петров Иван");
                System.out.println(r2.getFullName());
                synchronized (r1) {
                    r2.equals(r1);
                }
            }
            System.out.println(Thread.currentThread().getName() + " start");
        }, "Thread2");

        thread1.start();
        thread2.start();
    }

}
