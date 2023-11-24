/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.examchecking;

/**
 *
 * @author Денис
 */
public class ExamChecking implements Runnable {
    private static final int TOTAL_EXAM_SHEETS = 500; // Общее количество листов экзамена
    private static final int SHEETS_PER_THREAD = 50; // Количество листов, которые каждый поток должен проверить
    private static final int ITERATION_LIMIT = 6; // Предел итераций для каждого потока

    private static int checkedSheets = 0; // Количество уже проверенных листов
    private static final Object lock = new Object(); // Объект блокировки для синхронизации доступа к общему ресурсу

    private int sheetsToCheck; // Количество листов для проверки каждым потоком

    public ExamChecking(int sheetsToCheck) {
        this.sheetsToCheck = sheetsToCheck;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < ITERATION_LIMIT) {
            synchronized (lock) { // Синхронизация для безопасного доступа к общему ресурсу (checkedSheets)
                if (checkedSheets < TOTAL_EXAM_SHEETS) {
                    // Вычисляем количество листов, которые данный поток может проверить в текущей итерации
                    int sheets = Math.min(sheetsToCheck, TOTAL_EXAM_SHEETS - checkedSheets);
                    checkedSheets += sheets; // Увеличиваем общее количество проверенных листов
                    // Выводим информацию о проверке текущим потоком
                    System.out.println("Thread " + Thread.currentThread().getName() +
                            " checked " + sheets + " sheets. Total checked: " + checkedSheets);
                } else {
                    break; // Если все листы уже проверены, выхожу из цикла
                }
            }
            i++;
        }
    }

    public static void main(String[] args) {
        int numberOfThreads = TOTAL_EXAM_SHEETS / SHEETS_PER_THREAD; // Вычисляю количество потоков
        ExamChecking examChecker = new ExamChecking(SHEETS_PER_THREAD); // Создаю экземпляр класса для проверки

        for (int i = 0; i < numberOfThreads; i++) {

            Thread thread = new Thread(examChecker, "Thread-" + (i + 1));
            thread.start();
        }
    }
}
