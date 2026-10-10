package WEEK10.class_problems;

import java.util.*;

abstract class Device {
    protected String id;
    protected boolean isOn;

    public Device(String id) {
        this.id = id;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(id + " is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(id + " is OFF");
    }

    public String getId() {
        return id;
    }
}

class Light extends Device {
    private boolean supportsDim;
    private boolean supportsSchedule;

    public Light(String id, boolean supportsDim, boolean supportsSchedule) {
        super(id);
        this.supportsDim = supportsDim;
        this.supportsSchedule = supportsSchedule;
    }

    public void dim(int level) {
        if (!supportsDim) {
            System.out.println(id + " rejected: DIM unsupported");
        } else if (level < 0 || level > 100) {
            System.out.println(id + " rejected: invalid dim level");
        } else {
            System.out.println(id + " dimmed to " + level);
        }
    }

    public void schedule(String time) {
        if (!supportsSchedule) {
            System.out.println(id + " rejected: SCHEDULE unsupported");
        } else {
            System.out.println(id + " scheduled " + time);
        }
    }
}

class Fan extends Device {
    private boolean supportsSchedule;

    public Fan(String id, boolean supportsSchedule) {
        super(id);
        this.supportsSchedule = supportsSchedule;
    }

    public void schedule(String time) {
        if (!supportsSchedule) {
            System.out.println(id + " rejected: SCHEDULE unsupported");
        } else {
            System.out.println(id + " scheduled " + time);
        }
    }
}

class Plug extends Device {
    private double energy;

    public Plug(String id, double energy) {
        super(id);
        this.energy = energy;
    }

    public void showEnergy() {
        System.out.println(id + " energy " + energy + " kWh");
    }
}

class SmartHome {
    private Map<String, Device> devices = new HashMap<>();

    public void addDevice(Device device) {
        devices.put(device.getId(), device);
    }

    public void processCommand(String command) {
        String[] parts = command.trim().split("\\s+");

        if (parts.length < 2) {
            System.out.println("Rejected: invalid command");
            return;
        }

        String action = parts[0];
        String id = parts[1];

        Device device = devices.get(id);

        if (device == null) {
            System.out.println(id + " rejected: device not found");
            return;
        }

        switch (action) {
            case "ON":
                device.turnOn();
                break;

            case "OFF":
                device.turnOff();
                break;

            case "DIM":
                if (device instanceof Light) {
                    if (parts.length < 3) {
                        System.out.println(id + " rejected: missing dim level");
                    } else {
                        try {
                            ((Light) device).dim(Integer.parseInt(parts[2]));
                        } catch (NumberFormatException e) {
                            System.out.println(id + " rejected: invalid dim level");
                        }
                    }
                } else {
                    System.out.println(id + " rejected: DIM unsupported");
                }
                break;

            case "SCHEDULE":
                if (device instanceof Light) {
                    if (parts.length < 3) {
                        System.out.println(id + " rejected: missing schedule time");
                    } else {
                        ((Light) device).schedule(parts[2]);
                    }
                } else if (device instanceof Fan) {
                    if (parts.length < 3) {
                        System.out.println(id + " rejected: missing schedule time");
                    } else {
                        ((Fan) device).schedule(parts[2]);
                    }
                } else {
                    System.out.println(id + " rejected: SCHEDULE unsupported");
                }
                break;

            case "ENERGY":
                if (device instanceof Plug) {
                    ((Plug) device).showEnergy();
                } else {
                    System.out.println(id + " rejected: ENERGY unsupported");
                }
                break;

            default:
                System.out.println(id + " rejected: unknown command");
        }
    }
}

public class SmartHomePlatform {
    public static void main(String[] args) {
        SmartHome home = new SmartHome();

        home.addDevice(new Light("L1", true, true));
        home.addDevice(new Fan("F1", true));
        home.addDevice(new Plug("P1", 12));

        String[] commands = {
                "ON L1",
                "DIM L1 40",
                "DIM F1 30",
                "SCHEDULE F1 22:00",
                "ENERGY P1",
                "ENERGY L1"
        };

        for (String command : commands) {
            home.processCommand(command);
        }
    }
}
