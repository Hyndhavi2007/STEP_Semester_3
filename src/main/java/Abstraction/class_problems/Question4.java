
package Abstraction.class_problems;

interface Playable {
    String play();

    String play(int fromSecond);

    String pause();
}

abstract class MediaFile {
    private static int counter = 1001;
    private final String fileId;

    public MediaFile() {
        fileId = "MF-" + counter++;
    }

    public String getFileId() {
        return fileId;
    }

    public abstract String getFormatInfo();
}

class AudioFile extends MediaFile implements Playable {
    private String title;

    public AudioFile(String title) {
        super();
        this.title = title;
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;

        return "Playing audio: " + title + " from "
                + minutes + ":" + String.format("%02d", seconds);
    }

    @Override
    public String pause() {
        return "Paused audio: " + title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }
}

class Podcast implements Playable {
    private String showName;
    private int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber
                + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;

        return "Streaming episode " + episodeNumber + " of "
                + showName + " from " + minutes + ":"
                + String.format("%02d", seconds);
    }

    @Override
    public String pause() {
        return "Paused episode " + episodeNumber
                + " of " + showName;
    }
}

public class Question4 {

    static void launchAll(Playable[] items) {
        for (Playable item : items) {
            System.out.println(item.play());
        }
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");

        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        // Upcasting: AudioFile stored in a Playable variable.
        Playable ref = a;
        System.out.println(ref.play());

        launchAll(new Playable[]{ref, p});

        System.out.println(a.pause());
        System.out.println(p.pause());
    }
}
