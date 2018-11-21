package com.quan;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;


public class MD5Test {
    @Test
    public void test(){
        Md5Hash md5Hash1 = new Md5Hash("666");
        System.out.println(md5Hash1);

        //加solt
        Md5Hash md5Hash2 = new Md5Hash("666","zhangsan");
        System.out.println(md5Hash2);

        //散列次数
        Md5Hash md5Hash3 = new Md5Hash("666","zhangsan",3);
        System.out.println(md5Hash3);
    }
}
