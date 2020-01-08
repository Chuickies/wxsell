package com.zcj.utils;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author zhou
 * @date 2019年5月30日 下午3:18:48
 * @detail 随机生成固定长度的字符串
 */
public class GenerateStrUtils {

        public static ArrayList<String> strList = new ArrayList<String>();
        public static Random random = new Random();
        public static final int RANDOM_LENGTH0=16;
        public static final int RANDOM_LENGTH1 = 256;
        public static final int RANDOM_LENGTH2 = 512;
        public static final int RANDOM_LENGTH3 = 1024;

        static {
            init();
        }

        public static void main(String[] args) {
            //String randomStr = generateRandomStr(RANDOM_LENGTH1);
            //System.out.println(RANDOM_LENGTH1 + "位随机数:" + randomStr);
            //randomStr = generateRandomStr(RANDOM_LENGTH2);
            //System.out.println(RANDOM_LENGTH2 + "位随机数:" + randomStr);
            //randomStr = generateRandomStr(RANDOM_LENGTH3);
            //System.out.println(RANDOM_LENGTH3 + "位随机数:" + randomStr);
            //System.out.println(generateRandomStr(RANDOM_LENGTH0));
        }

        public static String generateRandomStr(int length) {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < length; i++) {
                int size = strList.size();
                String randomStr = strList.get(random.nextInt(size));
                sb.append(randomStr);
            }
            return sb.toString();
        }

        public static void init() {
            int begin = 97;
            //生成小写字母,并加入集合
            for(int i = begin; i < begin + 26; i++) {
                strList.add((char)i + "");
            }
            //生成大写字母,并加入集合
            begin = 65;
            for(int i = begin; i < begin + 26; i++) {
                strList.add((char)i + "");
            }
            //将0-9的数字加入集合
            for(int i = 0; i < 10; i++) {
                strList.add(i + "");
            }
        }

}
