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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VikiKeyHandlerThread implements Runnable {

    private List<Character> cardPrefix1 = Collections.singletonList((char) 37);
    private List<Character> cardSuffix1 = Collections.singletonList((char) 63);
    private List<Character> cardPrefix2 = Collections.singletonList((char) 59);
    private List<Character> cardSuffix2 = Arrays.asList((char) 63, (char) 10);
    private static int SECOND_TRACK_WAITING_TIME = 50;
    private static int TIME_OUT = 3000;
    private static int SCANNER_THRESHOLD = 4;
    private VikiKeyboard keyboardModule = null;
    private TypedCharactersQueue keysQueue;

    public VikiKeyHandlerThread(VikiKeyboard keyboardModule, TypedCharactersQueue keysQueue) {
        this.keyboardModule = keyboardModule;
        this.keysQueue = keysQueue;
    }

    @Override
    public void run() {

        //чтобы не создавать объекты внутри цикла, создадим один раз и будем переиспользовать
        LinkedList<Character> characters;
        List<Character> sequence = new ArrayList<>((int) (255 * 1.2)); //переменная для поиска последовательностей

        //основной цикл обработки событий
        while (!Thread.currentThread().isInterrupted()) {
            try {
                characters = keysQueue.poll();

                if (isTrack1(characters)) {
                    if (null !=
                        getMSRSequence(characters, cardSuffix1, cardPrefix2, cardSuffix2, keysQueue, TIME_OUT, SECOND_TRACK_WAITING_TIME, sequence)) {
                        processMSR(sequence);
                    }
                    //msr карта, дорожка 2 ?
                } else if (isTrack2(characters)) {
                    char finalScanCode = cardSuffix2.get(cardSuffix2.size() - 1);
                    sequence.clear();
                    if (null != getSequence(characters, finalScanCode, keysQueue, TIME_OUT, sequence)) {
                        processMSR(sequence);
                    }
                } else {
                    keyboardModule.processScanner(characters);
                }
            } catch (Exception e) {
                keysQueue.clear();
            }
        }
    }

    /**
     * Отыщем подпоследовательность сканкодов.
     * Сперва ищем последовательность в уже пришедшей последовательности keys. Если она не найдена финальным
     * символом, то ожидаем ее окончание из очереди keysQueue в течении timeout милисекунд
     *
     * @param keys пришедшая последовательность, если ее нет, то null (очищается в случае удачной обработки)
     * @param finalScanCode последний символ в искомой последовательности
     * @param keysQueue очередь для будущих символов
     * @param timeout время ожидания, если не найдена последовательность
     * @param result массив для результатов
     * @return если найдена последовательность - вернем ее в result, если нет - null
     */
    private List<Character> getSequence(LinkedList<Character> keys, char finalScanCode, TypedCharactersQueue keysQueue, long timeout,
        List<Character> result)
        throws InterruptedException {
        //лайфхак, в 99% случаев finalCode будет последним в keys
        if (keys != null && !keys.isEmpty() && keys.getLast() == finalScanCode) {
            result.addAll(keys);
            keys.clear();
            return result;
        }
        //не прокатило, просмотрим keys и подождем из очереди
        if (keys != null && keys.contains(finalScanCode)) {
            int h = 0;
            for (Character value : keys) {
                result.add(value);
                h++;
                if (value == finalScanCode) {
                    break;
                }
            }
            //очистим keys от найденных значений
            for (int i = 0; i < h; i++) {
                keys.removeFirst();
            }
            return result;
        } else {
            List<Character> scanCodeList = keysQueue.waitSequence(finalScanCode, timeout);
            if (scanCodeList != null) {
                if (keys != null) {
                    result.addAll(keys);
                    keys.clear();
                }
                result.addAll(scanCodeList);
                return result;
            }
            return null;
        }
    }

    /**
     * Поиск последовательностей MSR карт
     * Алгоритм примерно такой-же что и getSequence, с условием что могут быть 2 дорожки и после первой
     * последовательности надо попробовать найти вторую с определенным таймаутом
     *
     * @return если последовательность найдена, возвращаем заполненный result массив, null - если не найдена ни
     * одна дорожка
     */
    private List<Character> getMSRSequence(LinkedList<Character> keys, List<Character> cardSufix, List<Character> cardPrefix2,
        List<Character> cardSufix2, TypedCharactersQueue keysQueue, long timeout, long timeoutSecondTrack, List<Character> result)
        throws InterruptedException {
        char finalScanCode = cardSufix.get(cardSufix.size() - 1);
        char firstScanCode2 = cardPrefix2.get(0);
        char finalScanCode2 = cardSufix2.get(cardSufix2.size() - 1);
        result.clear();
        if (null != getSequence(keys, finalScanCode, keysQueue, timeout, result)) {
            //нашли первую дорожку MSR, может есть вторая?
            Character nextKey = (keys.isEmpty()) ? keysQueue.peek() : keys.getFirst();
            if (nextKey != null && firstScanCode2 == nextKey) {
                getSequence(keys, finalScanCode2, keysQueue, timeoutSecondTrack, result);
            }
            return result;
        }
        return null;
    }

    public void processMSR(List<Character> scanCodeList) {
        String[] tracks = getTracks(scanCodeList);

        String track1 = tracks[0];
        String track2 = tracks[1];

        if (track1 != null || track2 != null) {
            keyboardModule.processMSR(track1, track2);
        }
    }

    /**
     * просто проверим, что префикс не пустой
     */
    private boolean validatePrefix(List<Character> prefix) {
        return prefix != null && prefix.size() > 0;
    }

    public boolean isTrack1(Queue<Character> scanCodes) {
        return isEvent(cardPrefix1, scanCodes);
    }

    public boolean isTrack2(Queue<Character> scanCodes) {
        return isEvent(cardPrefix2, scanCodes);
    }

    /**
     * Проверим последовательность на префикс, по нему начнется отслеживанеи последовательности
     */
    private boolean isEvent(List<Character> prefix, Queue<Character> scanCodes) {
        if (validatePrefix(prefix) && scanCodes.size() > prefix.size()) {
            Character[] codes = scanCodes.toArray(new Character[scanCodes.size()]);
            for (int i = 0; i < prefix.size(); i++) {
                if (!codes[i].equals(prefix.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String[] getTracks(List<Character> scanCodeList) {

        String track1 = null;
        String track2 = null;

        int delimiter = findTrackDelimiter(scanCodeList, cardSuffix1);

        if ((delimiter != -1) && (delimiter + 1 < scanCodeList.size())) {
            List<Character> scanCodeList1 = scanCodeList.subList(0, delimiter);
            List<Character> scanCodeList2 = scanCodeList.subList(delimiter, scanCodeList.size());
            scanCodeList2 = scanCodeList2.subList(0, findTrackDelimiter(scanCodeList2, cardSuffix2));
            if (validateTrack(scanCodeList1, cardPrefix1, cardSuffix1) && validateTrack(scanCodeList2, cardPrefix2, cardSuffix2)) {
                track1 = getTrack(cardPrefix1, cardSuffix1, scanCodeList1);
                track2 = getTrack(cardPrefix2, cardSuffix2, scanCodeList2);
            }
        } else {
            if (validateTrack(scanCodeList, cardPrefix1, cardSuffix1)) {
                track1 = getTrack(cardPrefix1, cardSuffix1, scanCodeList);
            } else if (validateTrack(scanCodeList, cardPrefix2, cardSuffix2)) {
                track2 = getTrack(cardPrefix2, cardSuffix2, scanCodeList);
            }
        }

        return new String[]{ track1, track2 };
    }

    private String getTrack(List<Character> cardPrefix, List<Character> cardSuffix, List<Character> scanCodeList) {
        StringBuilder sb = new StringBuilder();
        int lastIndex = scanCodeList.size() - cardSuffix.size();
        if (cardSuffix.size() > 1 && cardSuffix.get(cardSuffix.size() - 1) == 10 && scanCodeList.get(scanCodeList.size() - 1) != 10) {
            lastIndex++;
        }
        for (int i = cardPrefix.size(); i < lastIndex; i++) {
            sb.append(scanCodeList.get(i));
        }
        return sb.toString();
    }

    private boolean validateTrack(List<Character> scanCodeList, List<Character> cardPrefix, List<Character> cardSuffix) {
        return scanCodeList.subList(0, cardPrefix.size()).equals(cardPrefix) &&
            (scanCodeList.subList(scanCodeList.size() - cardSuffix.size(), scanCodeList.size()).equals(cardSuffix) || (cardSuffix.size() > 1 &&
                scanCodeList.subList(scanCodeList.size() - cardSuffix.size() + 1, scanCodeList.size())
                    .equals(cardSuffix.subList(0, cardSuffix.size() - 1))));
    }

    private int findTrackDelimiter(List<Character> scanCodeList, List<Character> cardSuffix1) {
        if (cardSuffix1.size() > 1 && cardSuffix1.get(cardSuffix1.size() - 1) == 10) {
            return Collections.indexOfSubList(scanCodeList, cardSuffix1.subList(0, cardSuffix1.size() - 1)) + cardSuffix1.size() - 1;
        }
        return Collections.indexOfSubList(scanCodeList, cardSuffix1) + cardSuffix1.size();
    }
}