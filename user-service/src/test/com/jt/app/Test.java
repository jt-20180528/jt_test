package com.jt.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args){
        test1();
        //System.out.print(EncryptUtil.md5Encode("root1").equals("e5d9dee0892c9f474a174d3bfffb7810"));
    }

    public static void test1(){

        Long roleCount=10L;
        List<Integer> roleIds = new ArrayList<>();
        for (int i = 0; i < (roleCount > 2 ? roleCount - 1 : roleCount); i++) {
            int randomId = new Random().nextInt(roleCount.intValue()) + 1;
            roleIds.add(randomId);
            //System.out.print("随机角色编号为：【" + randomId + "】重复的会被剔除");
        }
        HashSet<Integer> hashSet = new HashSet<Integer>(roleIds);
        roleIds.clear();
        roleIds.addAll(hashSet);

        for(Integer roleId : roleIds){
            System.out.print(roleId+"\n");
        }
    }
}
