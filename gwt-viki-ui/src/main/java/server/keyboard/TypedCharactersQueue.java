package server.keyboard;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Очередь нажатых клавиш Смысл тут такой: при нажатии сканкод помещается в очередь, но отдается методом poll() не сразу. Если в течении таймаута
 * пришел еще один сканкод, он помещается в очередь, а ожидание пробливается на таймаут. Таким образом метод poll() вернет либо отдельные символы,
 * либо сразу строку сканирования (msr дорожку) <p> предназначен для работы в 1 потоке обработки результатов очереди, т.к. результаты отдаются наружу
 * в локальных массивах (для оптимизации)
 */
public class TypedCharactersQueue {
    private PriorityQueue<KeyCode> queue = new PriorityQueue<>(15, (Comparator<KeyCode>) (o1, o2) -> Long.compare(o1.getNumber(), o2.getNumber()));
        //основная очередь
    private LinkedList<Character> toReturn = new LinkedList<>();   //переменная для результатов poll()
    private List<Character> toReturnSequence = new LinkedList<>(); //
    private long timeout = 30L;
    private final Object writer = new Object();
    private long lastWriteTime = 0;

    TypedCharactersQueue(long timeout) {
        this.timeout = timeout;
    }

    /**
     * новый сканкод, прсто добавим в очередь и запомним время добавления
     */
    public boolean add(KeyCode keyCode) {
        synchronized (writer) {
            boolean b = queue.add(keyCode);
            lastWriteTime = System.currentTimeMillis();
            writer.notifyAll();
            return b;
        }
    }

    /**
     * Блокирующая выборка Отдаем результаты только если в течении таймаута не было ни 1 сообщения
     */
    public LinkedList<Character> poll() throws InterruptedException {
        long l = 1;
        synchronized (writer) {
            //ждем пока очередь будет непуста и пройдем более timeout с предыдущего символа
            while (queue.peek() == null || (l = (System.currentTimeMillis() - lastWriteTime)) < timeout) {
                if (l < timeout) {
                    writer.wait(timeout - l);
                } else {
                    writer.wait();
                }
            }
            //отдаем результаты в локальном массиве
            toReturn.clear();
            while (queue.size() != 0) {
                toReturn.add(queue.remove().getKeyCode());
            }

            queue.clear();
            return toReturn;
        }
    }

    /**
     * Посмотрим следующий элемент на обработку, но не тронем его
     */
    public Character peek() {
        synchronized (writer) {
            return queue.peek().getKeyCode();
        }
    }

    /**
     * Запрашиваем последовательность из очереди. Если в течении таймаута последовательнотсь не набралась, возвращаем null. Найденый результат
     * возвращаем мгновенно
     *
     * @param finalScanCode
     *     символ окончания последовательности
     * @param timeout
     *     время ожидания
     */
    public List<Character> waitSequence(char finalScanCode, long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        toReturnSequence.clear();

        synchronized (writer) {
            for (KeyCode code : queue) {
                toReturnSequence.add(code.getKeyCode());
                if (code.getKeyCode() == finalScanCode) {
                    //нашли последовательность в очереди
                    queue.removeAll(toReturnSequence);
                    return toReturnSequence;
                }
            }
            boolean found;
            //нет еще последовательности, подождем
            long time = 0;
            while (true) {
                time = startTime + timeout - System.currentTimeMillis();
                if (time > 0) {
                    writer.wait(time);
                }
                if (!queue.isEmpty()) {
                    found = queue.peek().getKeyCode() == finalScanCode;
                    toReturnSequence.add(queue.poll().getKeyCode());
                    if (found) {
                        return toReturnSequence;
                    }
                } else {
                    //вышли из ожидания по таймауту, т.к. очередь пуста
                    return null;
                }
                if (time <= 0) {
                    return null;
                }
            }
        }
    }

    public void clear() {
        synchronized (writer) {
            queue.clear();
        }
    }
}