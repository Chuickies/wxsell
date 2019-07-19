package com.zcj.distributed.test;

import com.zcj.distributed.service.SkillService;

public class RedisDistributedLockTest {
    public static void main(String[] args) {
        SkillService service = new SkillService();
        for (int i = 0; i < 10; i++) {
            ThreadJedis threadJedis = new ThreadJedis(service);
            threadJedis.start();
        }
    }


}

class ThreadJedis extends Thread {
    private SkillService service;

    public ThreadJedis(SkillService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.skill();
    }
}
