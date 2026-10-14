package WEEK8.assigment_problems;

import java.util.*;

interface Capability {
    String getName();
    boolean supports(String action);
    boolean apply(String action, int value, String deviceName);
}

class PowerCapability implements Capability {
    private boolean isOn = false;

    public String getName() {
        return "Power";
    }

    public boolean supports(String action) {
        return action.equalsIgnoreCase("power");
    }

    public boolean apply(String action, int value, String deviceName) {
        if (!supports(action)) {
            return false;
        }

        if (value != 0 && value != 1) {
            System.out.println("Invalid power value.");
            return false;
        }

        isOn = value == 1;
        System.out.println(deviceName + ": " + (isOn ? "ON" : "OFF") + ".");
        return true;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    public String getName() {
        return "Brightness";
    }

    public boolean supports(String action) {
        return action.equalsIgnoreCase("brightness");
    }

    public boolean apply(String action, int value, String deviceName) {
        if (!supports(action)) {
            return false;
        }

        if (value < 0 || value > 100) {
            System.out.println(
                    "Rejected: " + deviceName
                            + " brightness must be between 0% and 100%."
            );
            return false;
        }

        brightness = value;
        System.out.println(
                deviceName + ": brightness set to " + brightness + "%."
        );
        return true;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 24;

    public String getName() {
        return "Temperature";
    }

    public boolean supports(String action) {
        return action.equalsIgnoreCase("temperature");
    }

    public boolean apply(String action, int value, String deviceName) {
        if (!supports(action)) {
            return false;
        }

        if (value < 16 || value > 30) {
            System.out.println(
                    "Rejected: " + deviceName
                            + " temperature must be between 16°C and 30°C."
            );
            return false;
        }

        temperature = value;
        System.out.println(
                deviceName + ": temperature set to " + temperature + "°C."
        );
        return true;
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(
                capability.getName().toLowerCase(Locale.ROOT),
                capability
        );

        System.out.println(
                name + ": " + capability.getName() + " capability added."
        );
    }

    public boolean hasCapability(String action) {
        for (Capability capability : capabilities.values()) {
            if (capability.supports(action)) {
                return true;
            }
        }
        return false;
    }

    public boolean apply(String action, int value) {
        for (Capability capability : capabilities.values()) {
            if (capability.supports(action)) {
                return capability.apply(action, value, name);
            }
        }

        return false;
    }
}

class SceneStep {
    private String action;
    private int value;

    public SceneStep(String action, int value) {
        this.action = action;
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(String action, int value) {
        steps.add(new SceneStep(action, value));
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");

        int actionsApplied = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getAction())) {
                    if (device.apply(step.getAction(), step.getValue())) {
                        actionsApplied++;
                    }
                }
            }
        }

        System.out.println(
                "Scene '" + name + "' completed: "
                        + actionsApplied + " actions applied."
        );
    }
}

public class Question3 {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(
                ac, lights, projector
        );

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep("power", 1);
        lectureMode.addStep("brightness", 40);
        lectureMode.addStep("temperature", 24);

        lectureMode.execute(devices);

        ac.apply("temperature", 12);

        projector.addCapability(new BrightnessCapability());
        projector.apply("brightness", 70);
    }
}