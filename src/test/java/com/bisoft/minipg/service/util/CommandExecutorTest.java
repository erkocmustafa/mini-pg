package com.bisoft.minipg.service.util;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandExecutorTest {

    CommandExecutor sut;

    @BeforeEach
    void init() {

        this.sut = new CommandExecutor();
    }

    @Test
    public void execute() {
//        sut.executeCommand("sudo", "su" ,"-", "postgres");
        sut.executeCommand("pg_ctl", "status");
    }


//    @Test
    public void executeSync() {

        sut.executeCommandSync("sleep", "3");
    }

}