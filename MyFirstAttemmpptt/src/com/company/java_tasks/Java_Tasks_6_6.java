package com.company.java_tasks;

import java.util.LinkedList;

public class Java_Tasks_6_6 {
    public static void showResults() {

        System.out.println("___[1]___");
        System.out.println(bell(5));

        System.out.println("___[2]___");
        System.out.println(translateWord("What"));
        System.out.println(translateSentence("I like to eat honey waffles."));

        System.out.println("___[3]___");
        System.out.println(validColor("rgb(5,2,7)"));
        System.out.println(validColor("rgba(5,2,7,0.5213)"));
        System.out.println(validColor("rgb(5,,7)"));
        System.out.println(validColor("rgb(5,266,7)"));

        System.out.println("___[4]___");
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        String[] strings4 = {"b"};
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2", strings4));


    }


    /**
     *1.	Число Белла - это количество способов, которыми массив из n элементов может быть разбит на непустые подмножества.
     * Создайте функцию, которая принимает число n и возвращает соответствующее число Белла.
     *
     *
     1
     1	2
     2	3	5
     5	7	10	15
     15	20	27	37	52

     */
    private static int bell (int i1){
        int[] array1 = {1};
        int[] array2 = new int[0];
        for (int i = 1; i < i1; i++){

            array2 = new int[i+1];
            array2[0] = array1[array1.length - 1];
            for (int j = 1; j < i+1;j++){
                array2[j] = array2[j - 1] + array1[j - 1];
            }

            array1 = array2;
        }

        return array2[array2.length - 1];
    }


    /**
     *2.	В «поросячей латыни» (свинский латинский) есть два очень простых правила:
     * – Если слово начинается с согласного, переместите первую букву (буквы) слова до гласного до конца слова и добавьте «ay» в конец.
     * have ➞ avehay
     * cram ➞ amcray
     * take ➞ aketay
     * cat ➞ atcay
     * shrimp ➞ impshray
     * trebuchet ➞ ebuchettray
     * –	Если слово начинается с гласной, добавьте "yay" в конце слова.
     * ate ➞ ateyay
     * apple ➞ appleyay
     * oaken ➞ oakenyay
     * eagle ➞ eagleyay
     * Напишите две функции, чтобы сделать переводчик с английского на свинский латинский.
     * Первая функция translateWord (word) получает слово на английском и возвращает это слово,
     * переведенное на латинский язык. Вторая функция translateSentence (предложение) берет
     * английское предложение и возвращает это предложение, переведенное на латинский язык.
     */
    private static String translateWord (String s){

        if (isVowel(s.charAt(0))){
            s += "yay";
        }
        else {
            for (int i = 1; i < s.length(); i++){
                if (isVowel(s.charAt(i))){
                    s = s.substring(i) + s.substring(0, i ) + "ay";
                    break;
                }
            }
        }
        return s;
    }
    private static String translateSentence (String s){
        String[] words = sentenceToWords(s);

        for (String word : words) {
            s = s.replaceFirst(word, translateWord(word));
        }
        return s;
    }


    private static boolean isVowel (char c){
        return c=='a' || c=='e' ||
                c=='i' || c=='o' ||
                c=='u' ||
                c=='A' || c=='E' ||
                c=='I' || c=='O' ||
                c=='U';
    }

    private static String[] sentenceToWords(String s){
        LinkedList<String> linkedList = new LinkedList<>();

        String sBuffer = "";
        for (int i = 0; i < s.length(); i++){
            String Symbol = s.charAt(i) + "";
            if (Symbol.equals(" ") || Symbol.equals(".") || Symbol.equals(",") || Symbol.equals("-")) {

                if (!sBuffer.isEmpty()) linkedList.add(sBuffer);
                sBuffer = "";
            }
            else {
                sBuffer += Symbol;
            }
        }

        String[] ans = new String[linkedList.size()];
        for (int i = 0; i < ans.length; i++){
            ans[i] = linkedList.get(i);
        }
        return ans;
    }

    /**
     *3.	Учитывая параметры RGB (A) CSS, определите,
     * является ли формат принимаемых значений допустимым или нет.
     * Создайте функцию, которая принимает строку (например, " rgb(0, 0, 0)")
     * и возвращает true, если ее формат правильный, в противном случае возвращает false.
     */
    private static boolean validColor (String s){
        if (s.contains("rgba")){

            String sBuffer = "";
            int k = 1;
            for (int i = 5; s.charAt(i) != ')'; i++){

                if (s.charAt(i) == ','){
                    if (sBuffer.isBlank()) return false;
                    if ((Integer.parseInt(sBuffer) > 255 || Integer.parseInt(sBuffer) < 0) && k < 4) return false;
                    if (k == 4 && (Double.parseDouble(sBuffer) > 1 || Double.parseDouble(sBuffer) < 0)) return false;
                    sBuffer = "";
                    k++;
                }
                else sBuffer += s.charAt(i);
            }
            return true;
        }
        else {

            String sBuffer = "";
            for (int i = 4; s.charAt(i) != ')'; i++){

                if (s.charAt(i) == ','){
                    if (sBuffer.isBlank()) return false;
                    if (Integer.parseInt(sBuffer) > 255 || Integer.parseInt(sBuffer) < 0) return false;
                    sBuffer = "";
                }
                else sBuffer += s.charAt(i);
            }
            return true;
        }
    }


    /**
     *4.	Создайте функцию, которая принимает URL (строку),
     * удаляет дублирующиеся параметры запроса и параметры,
     * указанные во втором аргументе (который будет необязательным массивом).
     *
     * Примечание:
     * – Второй аргумент paramsToStrip является необязательным.
     * – paramsToStrip может содержать несколько параметров.
     * – Если есть повторяющиеся параметры запроса с разными значениями,
     * используйте значение последнего встречающегося параметра (см. Примеры № 1 и № 2 выше).
     */
    private static String stripUrlParams (String Url){
        LinkedList<String> linkedList = new LinkedList<>();

        for (int i = Url.indexOf("?"); i < Url.length(); i++){

            if (Url.charAt(i) == '&' || i == Url.indexOf("?") ){

               if (linkedList.contains(Url.charAt(i + 1) + "")){
                   Url = Url.replaceFirst(Url.charAt(i + 1) + "="
                                   + Url.charAt(Url.indexOf(Url.charAt(i + 1) + "=") + 2)
                           , "");
               }
               else {
                   linkedList.add(Url.charAt(i + 1) + "");
               }
            }
        }

        return Url;
    }
    private static String stripUrlParams (String Url, String[] UrlParams){
        LinkedList<String> linkedList = new LinkedList<>();

        for (int i = Url.indexOf("?"); i < Url.length(); i++){

            if (Url.charAt(i) == '&' || i == Url.indexOf("?") ){

                if (linkedList.contains(Url.charAt(i + 1) + "")){
                    Url = Url.replaceFirst(Url.charAt(i + 1) + "="
                                    + Url.charAt(Url.indexOf(Url.charAt(i + 1) + "=") + 2)
                            , "");
                }
                else {
                    linkedList.add(Url.charAt(i + 1) + "");
                }
            }
        }

        for (String param : UrlParams){
            Url = Url.replaceAll(param + "="
                            + Url.charAt(Url.indexOf(param + "=") + 2)
                    , "");
        }

        return Url;
    }


    /**
     *5.	Напишите функцию, которая извлекает три самых длинных слова из заголовка газеты и преобразует их в хэштеги.
     * Если несколько слов одинаковой длины, найдите слово, которое встречается первым.
     */
    private static String[] getHashTags (String s){
        String[] ans = new String[3];

        int k = 0;
        while (k < 2){

            for (int i = 0; i < s.length(); i++){

            }
        }

    }


    /**
     *
     */


    /**
     *
     */


    /**
     *
     */


    /**
     *
     */


    /**
     *
     */


}