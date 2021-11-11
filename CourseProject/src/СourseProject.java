import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Math.abs;

/*
     Подробное описание приграммы в поясниьельной записке
 */

public class СourseProject {
    public static void main(String[] args) {
        byte[] acc = new byte[8]; //массив для двочного числа
        byte[] x; //мнжимое
        byte[] y; //множитель
        //переменные для логики работы
        byte temp1 = 0;
        byte temp2 = 0;
        int n = 0;

        //Ввод данных и перевод систем счисления
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Введите множимое число [-128,127]: ");
        byte first = keyboard.nextByte();
        x = transformationToBinary(first); //преобразование в двоичную систему
        //вывод данных
        System.out.print("В вдоичной форме = ");
        for (byte b : x) {
            System.out.print(b);
        }
        //Ввод данных и перевод систем счисления
        System.out.print("\nВведите множитель числа [-128,127]: ");
        byte second = keyboard.nextByte();
        y = transformationToBinary(second); //преобразование в двоичную систему
        //вывод данных
        System.out.print("В двоичной форме =  ");
        for (byte b : y) {
            System.out.print(b);
        }

        //цикл, который реализует алгоритм Бута
        //выполняется 8 раз
        while (n<8){
            //проверка двух младших битов на наличие 0 и 1
            if (y[7] == 0 & temp2 == 1) {
                acc = plus(acc, x); //сложение аккумулятора с множителем
                //побитовый сдвиг вправо
                temp1 = y[7];
                for (int i = 7; i > 0; i--) {
                    y[i] = y[i - 1];
                }
                y[0] = acc[7];
                for (int i = 7; i > 0; i--) {
                    acc[i] = acc[i - 1];
                }
                acc[0] = temp1;
                temp2 = temp1;
                n++; //количество выполненных раз
                continue; //возврат на начало цикла
            }
            //проверка двух младших битов на наличие 1 и 0
            if (y[7] == 1 & temp2 == 0) {
                acc = minus(acc, x); //вычитание множителя из аккумулятора
                //побитовый сдвиг вправо
                temp1 = y[7];
                for (int i = 7; i > 0; i--) {
                    y[i] = y[i - 1];
                }
                y[0] = acc[7];
                for (int i = 7; i > 0; i--) {
                    acc[i] = acc[i - 1];
                }
                acc[0] = temp1;
                temp2 = temp1;
                n++; //количество выполненных раз
                continue; //возврат на начало цикла
            }
            //Если условия не выполнились и младшие разряды (0 и 0) или (1 и 1)
            //побитовый сдвиг вправо
            temp1 = y[7];
            for (int i = 7; i > 0; i--) {
                y[i] = y[i - 1];
            }
            y[0] = acc[7];
            for (int i = 7; i > 0; i--) {
                acc[i] = acc[i - 1];
            }
            acc[0] = temp1;
            temp2 = temp1;
            n++; //количество выполненных раз
        }

        //вывод резльтата умножения
        System.out.println("\n\nРезультат умножения:");
        System.out.print("В двоичной форме = ");
        for (byte b : y){
            System.out.print(b);
        }
        System.out.print("\nВ десятичной форме = " + transformationToDecimal(y)); //преобразование в десятичную систему
    }

    //перевод десятичной системы счисления в двоичную
    public static byte[] transformationToBinary(byte first) {
        byte[] arrBit = new byte[8];
        byte[] justOne = {0, 0, 0, 0, 0, 0, 0, 1};
        if (first > 0) {
            char[] ch = Integer.toBinaryString(first).toCharArray();
                for (int i = 0; i < ch.length; i++) {
                    arrBit[8 - ch.length + i] = (ch[i] == '1') ? (byte) 1 : 0;
            }
        }
        //проверка отрицательных вводимых данных
        if (first < 0) {
            char[] ch = Integer.toBinaryString(abs(first)).toCharArray();
            Arrays.fill(arrBit, (byte) 1);
            int count = arrBit.length - 1;
            for (int i = ch.length-1; i >= 0; i--) {
                arrBit[count--] = (ch[i] == '1') ? (byte) 0 : 1;
            }
            arrBit = plus(arrBit, justOne);
        }
        return arrBit; //возврат двоичного числа
    }

    //перевод доичной системы счисления в десятичную
    public static int transformationToDecimal(byte[] first) {
        String str = "";
        for (int i = 0; i < 8; i++) {
            str = str.concat(Byte.toString(first[i]));
        }
        int decimal = (byte) Integer.parseInt(str, 2);
        return decimal;
    }

    //сложение двух двоичных чисел
    public static byte[] plus(byte[] one, byte[] two){
        byte[] out = new byte[8];
        byte acc = 0;
        //цикл для проверка каждого разряда
        for (int i = 7; i >= 0; i--){
            //проверка всех вариантов комбинаций 0 и 1
            if (one[i] == 0 & two[i] == 0 & acc == 0){
                out[i] = 0;
                acc = 0;
                continue;
            }
            if (one[i] == 0 & two[i] == 0 & acc == 1){
                out[i] = 1;
                acc = 0;
                continue;
            }
            if ((one[i] == 0 & two[i] == 1 | one[i] == 1 & two[i] == 0) & acc == 0){
                out[i] = 1;
                acc = 0;
                continue;
            }
            if ((one[i] == 0 & two[i] == 1 | one[i] == 1 & two[i] == 0) & acc == 1){
                out[i] = 0;
                acc = 1;
                continue;
            }
            if (one[i] == 1 & two[i] == 1 & acc == 0){
                out[i] = 0;
                acc = 1;
                continue;
            }
            if (one[i] == 1 & two[i] == 1 & acc == 1){
                out[i] = 1;
                acc = 1;
                continue;
            }
        }
        return out; //вывод результата сложения
    }

    //разность двух двоичных числе
    public static byte[] minus(byte[] one, byte[] two){
        byte[] justOne = {0,0,0,0,0,0,0,1}; //единица
        two = plus(reverse(two), justOne); //получение дополнительного кода
        byte[] out = plus(one, two); //сложение числа с доп.кодом второго
        return out; //возврат результата
    }

    //получение обратного кода двоичного числа
    public static byte[] reverse(byte[] x){
        byte[] y = new byte[8];
        //замена всех 0 на 1 и наоборот
        for (int i = 0; i < x.length; i++){
            y[i] = (x[i] == 0) ? (byte)1 : 0;
        }
        return y; //возврат результата
    }
}