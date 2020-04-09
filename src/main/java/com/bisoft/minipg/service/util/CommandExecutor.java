package com.bisoft.minipg.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommandExecutor {

    public List<String> executeCommand(String... args) {
        System.out.println("sync script executing:" + String.join(" ", args));

        log.trace("async command executing : " + String.join(" ", args));

        List<String> cellValues = new ArrayList<>();
        try {
            Runtime.getRuntime().exec(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellValues;
    }

    public List<String> executeCommandSync(String... args) {

        System.out.println("EXECUTING THIS:" + String.join(" ", args));
        log.trace("sync command executing : ", String.join(" ", args));
        Process p;
        List<String> cellValues = new ArrayList<>();

        try {
            p = Runtime.getRuntime().exec(args);
            System.out.println("waiting for .... " + String.join(" ", args) + " to execute......");

            log.trace("waiting for .... ", String.join(" ", args), " to execute......");

            int resultNum = p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;

            if (resultNum == 0) {
                while (true) {
                    line = in.readLine();
                    if (line == null)
                        break;
                    cellValues.add(line);
                    System.out.println(line);
                }

                // For better seeing the result
                log.trace("COMMAND OUTPUT:" + String.join("\n", cellValues));
            } else {
                // in case of error
                while (true) {
                    line = err.readLine();
                    if (line == null)
                        break;
                    cellValues.add(line);
                    System.out.println(line);
                }

                // for better seeing the errors
                log.error("COMMAND ERRORS:" + String.join("\n", cellValues));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cellValues;
    }
}