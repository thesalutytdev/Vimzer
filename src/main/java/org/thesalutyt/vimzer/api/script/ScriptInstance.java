package org.thesalutyt.vimzer.api.script;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.commmand.CommandParser;
import org.thesalutyt.vimzer.api.features.commmand.Feedback;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ScriptInstance {
    protected final File file;
    protected final ArrayList<String> lines = new ArrayList<>();
    protected int curLine = 0;
    protected int breakOn = -1;

    public ScriptInstance(File file) {
        if (!file.getName().endsWith(Configurations.getScriptExtension())) {
            System.out.println(file);
            throw new IllegalArgumentException("[!] File is not a script: " + file.getName());
        }

        this.file = file;

        if (Configurations.isDebug() || Configurations.isLog()) {
            System.out.println("~ Script Instance: " + file.getName());

            System.out.println();
        }

        try {
            Scanner scanner = new Scanner(file);
            boolean inComment = false;
            int commentLine = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                curLine++;

                if (line.isEmpty() || line.isBlank()) continue;


                if (line.startsWith("**")) {
                    line = line.replaceFirst("\\**", "");

                    Feedback feedback = CommandParser.parse(line);

                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Executing(and adding to the script): " + line);
                        System.out.println("> Result: " + feedback.getMessage());
                        System.out.println("> Is Success: " + feedback.isSuccess());
                        System.out.println("> (Feedback) Is Necessary: " + feedback.isNecessary());
                        System.out.println();
                    }

                    if (feedback.isNecessary() || !feedback.isSuccess()) {
                        feedback.raise();
                    }

                } else if (line.startsWith("*")) {
                    line = line.replaceFirst("\\*", "");

                    Feedback feedback = CommandParser.parse(line);

                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Executing(and not adding to the script): " + line);
                        System.out.println("> Result: " + feedback.getMessage());
                        System.out.println("> Is Success: " + feedback.isSuccess());
                        System.out.println("> (Feedback) Is Necessary: " + feedback.isNecessary());
                        System.out.println();
                    }

                    if (feedback.isNecessary() || !feedback.isSuccess()) {
                        feedback.raise();
                    }

                    continue;
                }

                if (line.startsWith("#") || line.startsWith("//")) {
                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Ignoring: " + line);
                        System.out.println();
                    }
                    continue;
                } else if (line.startsWith("/*")) {
                    inComment = true;
                    commentLine++;
                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Ignoring: " + line);
                        System.out.println("> Comment line: " + commentLine);
                        System.out.println();
                    }
                    continue;
                } else if (line.endsWith("*/") && inComment) {
                    inComment = false;
                    commentLine++;
                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Ignoring: " + line);
                        System.out.println("> Comment line: " + commentLine);
                        System.out.println();
                    }
                    commentLine = 0;
                    continue;
                } else if (inComment) {
                    commentLine++;
                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Ignoring: " + line);
                        System.out.println("> Comment line: " + commentLine);
                        System.out.println();
                    }
                    continue;
                } else if (line.startsWith("!")) {
                    line = line.replaceFirst("!", "");
                    breakOn = curLine;

                    if (Configurations.isDebug() || Configurations.isLog()) {
                        System.out.println("> Break when executing on line: " + line);
                        System.out.println();
                    }
                }

                lines.add(line);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Feedback execute() {
        for (String line : lines) {
            Feedback feedback = CommandParser.parse(line);

            if (Configurations.isDebug()) {
                System.out.println("> Executing: " + line);
                System.out.println("> Result: " + feedback.getMessage());
                System.out.println("> Is Success: " + feedback.isSuccess());
                System.out.println("> (Feedback) Is Necessary: " + feedback.isNecessary());

                System.out.println();
            }

            if (feedback.isNecessary()) {
                feedback.raise();
            }

            if (!feedback.isSuccess()) {
                return feedback;
            }

            if (breakOn != -1 && curLine >= breakOn) {
                break;
            }

            curLine++;
        }

        return Feedback.SUCCESS;
    }
}
