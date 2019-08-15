package com.example.booking.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandPrompt {
    Process p;

    public String runCommand(String command) throws IOException {
        p = Runtime.getRuntime().exec(command);
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder strAllLines = new StringBuilder();
        int i = 1;
        while ((line = r.readLine()) != null) {
            strAllLines = strAllLines.append("").append(line).append("\n");
            if (line.contains("Console LogLevel: debug") && line.contains("Complete")) {
                break;
            }
            i++;
        }
        return strAllLines.toString();
    }

    public String runCommandThruProcessBuilder(String command) throws IOException {
        BufferedReader br = getBufferedReader(command);
        String line;
        StringBuilder strAllLines = new StringBuilder();
        while ((line = br.readLine()) != null) {
            strAllLines = strAllLines.append("").append(line).append("\n");
        }
        return strAllLines.toString().split(":")[1].replace("\n", "").trim();
    }

    public String runProcessCommandToGetDeviceID(String command) throws IOException {
        BufferedReader br = getBufferedReader(command);
        String line;
        String allLine = "";
        while ((line = br.readLine()) != null) {
            allLine = allLine.trim() + "" + line.trim() + "\n";
        }
        return allLine.trim();
    }

    private BufferedReader getBufferedReader(String command) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/sh");
        commands.add("-c");
        commands.add(command);
        ProcessBuilder builder = new ProcessBuilder(commands);

        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    public String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
