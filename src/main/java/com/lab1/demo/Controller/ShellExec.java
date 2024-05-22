package com.lab1.demo.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShellExec {

    private String fullpath;

   public static ArrayList<String> ExecCommand(String command) throws IOException {
        String resp;
        ArrayList<String> list = new ArrayList<>();
        ProcessBuilder builder = new ProcessBuilder();
        //builder.inheritIO();

       String fullpath = HelloController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
       System.out.println(fullpath +" --> HelloController.class.getProtectionDomain().getCodeSource().getLocation().getPath(); ");
       fullpath=fullpath.replace("main/demo-1.0-SNAPSHOT-shaded.jar","");
       System.out.println(fullpath+" ---> after replace ");

        builder.command("sh", "-c", command);
        builder.directory(new File(fullpath));

        Process process = builder.start();
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));

        for(resp=output.readLine(); resp!=null;resp=output.readLine()) {
            if (resp.contains("\n")) {
                resp = resp.replace("\n", "");
            }
            list.add(resp);
        }
        output.close();
        process.destroy();
        return list;
    }

}
