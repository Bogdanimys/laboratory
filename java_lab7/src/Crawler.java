import jdk.jfr.internal.tool.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Executable;
import java.util.LinkedList;
import java.net. *;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Crawler {

    public static final String BEFORE_URL = "a href=";

    private static LinkedList<URLDepthPair> CheckedURL = new LinkedList<>();
    private static LinkedList<URLDepthPair> UncheckedURL = new LinkedList<>();
    private static final int MAXDepth = 4;
    private static final int MAXThreads = 5;




    public static void main(String[] args) throws IOException {


        //https://www.nytimes.com/
        //https://slashdot.org/
        //URLDepthPair firstURL = new URLDepthPair(new Scanner(System.in).nextLine(), 1);
        URLDepthPair firstURL = new URLDepthPair("https://slashdot.org/", 0);

        crawlThroughURL(firstURL);
        CheckedURL.add(firstURL);

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(MAXThreads);


        // Значит цикл работает так: Если потоков меньше MAXThreads -> крутим цикл (иначе main.wait() (wait снимается при окончании потока из executor)).
        // Если UncheckedURL не пуст -> добавляем UncheckedURL.get(0) к CheckedURL и удаляем из UncheckedURL, если глубина поиска не превышена, то начинаем...
        Main m = new Main();
        while (!UncheckedURL.isEmpty() || executor.getActiveCount() != 0) { // работает пока не закончатся непроверенные ссылки и все потоки
            // ВНИМАНИЕ - весь код внутри этого цикла сильно зависит от друг друга!!!

            if (executor.getActiveCount() == MAXThreads) {
                try {
                    synchronized (m) {
                        m.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!UncheckedURL.isEmpty()) {
                if (UncheckedURL.get(0).getDepth() < MAXDepth) {

                    executor.submit(() -> {
                        URLDepthPair urlDepthPair = UncheckedURL.get(0);

                        UncheckedURL.remove(urlDepthPair);
                        CheckedURL.add(urlDepthPair);

                        System.out.println(urlDepthPair.getStringFormat());
                        crawlThroughURL(urlDepthPair);

                        synchronized (m) {
                            m.notify();
                        }

                        return null;
                    });
                } else {
                    CheckedURL.add(UncheckedURL.get(0));
                    UncheckedURL.remove(UncheckedURL.get(0));
                }
            }
        }

        printAll(CheckedURL);

        System.out.println("UNCHECKED");
        printAll(UncheckedURL);
        System.out.println("CHECKED");
        printAll(CheckedURL);
    }


    private static void crawlThroughURL (URLDepthPair urlDepthPair){



        try {
            HttpURLConnection urlConnection = null;


            // устанавливаем соединение
            urlConnection =(HttpURLConnection) new URL(urlDepthPair.getURLAddress()).openConnection();
            urlConnection.setConnectTimeout(10_1000);

            // создаём Reader для чтения
            BufferedReader in = null;
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                in = new BufferedReader(inputStreamReader);
            } catch (ConnectException connectException) {
                connectException.printStackTrace();
            }


            // читаем сайт
            String s;
            if (in != null) {
                while ((s = in.readLine()) != null) {

                    //System.out.println(s);// TODO убрать, это просто для наглядности

                    if (s.contains(BEFORE_URL + "\"" + URLDepthPair.URL_PREFIX) && urlDepthPair.getDepth() < MAXDepth) {

                        String url = s.substring(s.indexOf(BEFORE_URL + "\"" + URLDepthPair.URL_PREFIX) + BEFORE_URL.length() + 1); // обрезаем url адресс от лишнего слева
                        url = url.substring(0, url.indexOf("\"")); //обрезаем url адресс от лишнего справа

                        // url = url.replace(URLDepthPair.URL_PREFIX, URLDepthPair.URL_PREFIX_S); // костыль, потому что некоторые ссылки http работают только, если они https


                        URLDepthPair foundURL = new URLDepthPair(url, urlDepthPair.getDepth() + 1);
                        if (!listContains(UncheckedURL, foundURL) && !listContains(CheckedURL, foundURL)) {
                            UncheckedURL.add(foundURL);
                        }
                    }
                }

                in.close();
                inputStreamReader.close();
                urlConnection.getInputStream().close();



            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    private static void printAll (LinkedList<URLDepthPair> linkedList){
        for (URLDepthPair urlDepthPair : linkedList) {
            System.out.println(urlDepthPair.getStringFormat());
        }
    }

    private static boolean listContains (LinkedList<URLDepthPair> linkedList,URLDepthPair urlDepthPair) {
        for (URLDepthPair ur: linkedList) {
            if (ur.getURLAddress().equals(urlDepthPair.getURLAddress())) return true;
        }
        return false;
    }


}
