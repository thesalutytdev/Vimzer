package org.thesalutyt.vimzer.api.variables;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.error.ErrorType;
import org.thesalutyt.vimzer.api.features.commmand.Feedback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VariableHandler {
    private static final ArrayList<IVar> variables = new ArrayList<>();
    public static final File variablesFile = new File(Configurations.getBrowserDir() + File.separator + ".variables.vzhst");

    public static void dump() {
        if (!variablesFile.exists()) {
            try {
                variablesFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileWriter writer = new FileWriter(variablesFile);

            for (IVar var : variables) {
                writer.write(var.toString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        variables.clear();

        if (variablesFile.exists()) {
            try {
                Scanner scanner = new Scanner(variablesFile);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(": ");

                    IVar var;

                    StringBuilder var_value = new StringBuilder();
                    if (parts[0].equals("var")) {
                        for (int i = 2; i < parts.length; i++) {
                            var_value.append(parts[i]).append(": ");
                        }

                    } else {
                        for (int i = 2; i < parts.length; i++) {
                            var_value.append(parts[i]).append(": ");
                        }

                    }
                    var = new Variable(parts[1], var_value);

                    variables.add(var);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Feedback addVariable(IVar var) {
        if (getVariable(var.getName()) != null) {
            Feedback feedback = Feedback.FAIL;

            feedback.setMessage(feedback.getMessage()
                    .replace("%ERROR_MESSAGE%", ErrorType.VAR_ALREADY_EXISTS.message())
                    .replace("%ERROR_CODE%", String.valueOf(ErrorType.VAR_ALREADY_EXISTS.errorCode())));

            return feedback;
        }

        variables.add(var);

        return Feedback.SUCCESS;
    }

    public static ArrayList<IVar> getVariables() {
        return variables;
    }

    public static IVar getVariable(String name) {
        for (IVar var : variables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }

        return null;
    }

    public static Feedback removeVariable(IVar var) {
        if (variables.contains(var)) {
            variables.remove(var);
            return Feedback.SUCCESS;
        } else {
            Feedback feedback = Feedback.FAIL;

            feedback.setMessage(feedback.getMessage()
                    .replace("%ERROR_MESSAGE%", ErrorType.VAR_NOT_FOUND.message())
                    .replace("%ERROR_CODE%", String.valueOf(ErrorType.VAR_NOT_FOUND.errorCode())));

            return feedback;
        }
    }

    public static void clearVariables() {
        variables.clear();
    }

    public static boolean containsVariable(String name) {
        return getVariable(name) != null;
    }

    public static Feedback removeVariable(String var) {
        if (getVariable(var) != null) {
            variables.remove(getVariable(var));
            return Feedback.SUCCESS;
        } else {
            Feedback feedback = Feedback.FAIL;

            feedback.setMessage(feedback.getMessage()
                    .replace("%ERROR_MESSAGE%", ErrorType.VAR_NOT_FOUND.message())
                    .replace("%ERROR_CODE%", String.valueOf(ErrorType.VAR_NOT_FOUND.errorCode())));

            return feedback;
        }
    }

    public static Feedback setVariableValue(String name, Object value) {
        IVar var = getVariable(name);

        if (var != null) {
            if (var instanceof Variable) {
                ((Variable) var).setValue(value);
                return Feedback.SUCCESS;
            } else {
                Feedback feedback = Feedback.FAIL;

                feedback.setMessage(feedback.getMessage()
                        .replace("%ERROR_MESSAGE%", ErrorType.VAR_IS_CONST.message())
                        .replace("%ERROR_CODE%", String.valueOf(ErrorType.VAR_IS_CONST.errorCode())));

                return feedback;
            }
        } else {
            Feedback feedback = Feedback.FAIL;

            feedback.setMessage(feedback.getMessage()
                    .replace("%ERROR_MESSAGE%", ErrorType.VAR_NOT_FOUND.message())
                    .replace("%ERROR_CODE%", String.valueOf(ErrorType.VAR_NOT_FOUND.errorCode())));

            return feedback;
        }
    }
}
